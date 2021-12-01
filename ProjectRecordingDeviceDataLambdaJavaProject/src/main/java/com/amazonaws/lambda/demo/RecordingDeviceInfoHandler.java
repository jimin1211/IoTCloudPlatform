package com.amazonaws.lambda.demo;

import	java.text.SimpleDateFormat;
import	java.util.TimeZone;

import	com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import	com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import	com.amazonaws.services.dynamodbv2.document.DynamoDB;
import	com.amazonaws.services.dynamodbv2.document.Item;
import	com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import	com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import	com.amazonaws.services.lambda.runtime.Context;
import	com.amazonaws.services.lambda.runtime.RequestHandler;

public class RecordingDeviceInfoHandler implements RequestHandler<Document, String> { //파라미터로 들어오는 타입: Document 타입
	private	DynamoDB	dynamoDb;
	private	String	DYNAMODB_TABLE_NAME	= "Project";
	@Override
	public	String	handleRequest(Document	input,	Context	context) {
		this.initDynamoDbClient();
		context.getLogger().log("Input:	" +	input);
		//return	null;
		return persistData(input); //document 객체의 내용을 저장하는 코드(이전상태와 동일한 경우 저장 X)
	}
	private	String	persistData(Document	document) throws	ConditionalCheckFailedException	{
		//	Epoch	Conversion	Code:	https://www.epochconverter.com/
		SimpleDateFormat	sdf	= new SimpleDateFormat ( "yyyy-MM-dd	HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
		String	timeString	=	sdf.format(new java.util.Date (document.timestamp*1000));
		//timestamp값 포멧		
		
		// 중복되는 레코드 저장 X
		if (document.current.state.reported.distance.equals(document.previous.state.reported.distance) && //현재 온도, 이전 온도 비교
			document.current.state.reported.LED.equals(document.previous.state.reported.LED)) { //현재 LED, 이전 LED 비교
			return	null;  //temperature, LED 둘 다 이전상태와 동일할 경우 저장하지 않고 종료
		}
		
		return this.dynamoDb.getTable(DYNAMODB_TABLE_NAME)
			.putItem(new PutItemSpec().withItem(new Item().withPrimaryKey("deviceId",	document.device)
				.withLong("time",	document.timestamp)
				.withString("distance",	document.current.state.reported.distance)
				.withString("LED",	document.current.state.reported.LED)
				.withString("timestamp",timeString)))
			.toString();
		}
	
		private void initDynamoDbClient() {
			AmazonDynamoDB	client	=	AmazonDynamoDBClientBuilder.standard().withRegion("ap-northeast-2").build();
			this.dynamoDb	= new DynamoDB(client);
		}
}
/**
*	AWS	IoT은(는)	섀도우 업데이트가 성공적으로 완료될 때마다	/update/documents	주제에 다음 상태문서를 게시합니다
*	JSON	형식의 상태문서는	2개의 기본 노드를 포함합니다.	previous	및	current.	
*	previous	노드에는 업데이트가 수행되기 전의 전체 섀도우 문서의 내용이 포함되고,	
*	current에는 업데이트가 성공적으로 적용된 후의 전체 섀도우 문서가 포함됩니다.	
*	섀도우가 처음 업데이트(생성)되면	previous	노드에는	null이 포함됩니다.
*	
*	timestamp는 상태문서가 생성된 시간 정보이고,	
*	device는 상태문서에 포함된 값은 아니고,	Iot규칙을 통해서	Lambda함수로 전달된 값이다.	
*	이 값을 해당 규칙과 관련된 사물이름을 나타낸다.	
*/
// /update/document 토픽의 속성임
class Document {  //4개의 속성
	public	Thing	previous; 
	public	Thing	current;
	public long	timestamp;
	public	String	device;	 //AWS IoT에 등록된 사물 이름
}
// /update/document 토픽 previous, current 속성은 thing 객체로 변환됨
class Thing {
	public	State	state	= new State();  //previous의 state 속성/current의 state 속성
	public long	timestamp;
	public	String	clientToken;
	public class State {
		public	Tag	reported	= new Tag();
		public	Tag	desired	= new Tag();
		public class Tag {
			public	String	distance;
			public	String	LED;
		}
	}
}