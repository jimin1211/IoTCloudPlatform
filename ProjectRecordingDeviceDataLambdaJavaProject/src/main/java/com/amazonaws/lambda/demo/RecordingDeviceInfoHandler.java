package com.amazonaws.lambda.demo;

import	java.text.SimpleDateFormat;
import	java.util.TimeZone;
import	com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import	com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import	com.amazonaws.services.dynamodbv2.document.DynamoDB;
import	com.amazonaws.services.dynamodbv2.document.Item;
import	com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import	com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import	com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import	com.amazonaws.services.lambda.runtime.Context;
import	com.amazonaws.services.lambda.runtime.RequestHandler;
public class RecordingDeviceInfoHandler implements RequestHandler<Thing, String> {
	private	DynamoDB	dynamoDb;
	private	String	DYNAMODB_TABLE_NAME	= "Project";
	@Override
	public	String	handleRequest(Thing	input,	Context	context) {
			this.initDynamoDbClient();
			persistData(input);
			return "Success	in	storing	to	DB!";
	}
	private	PutItemOutcome	persistData(Thing	thing) throws	ConditionalCheckFailedException	{
			//	Epoch	Conversion	Code:	https://www.epochconverter.com/
			SimpleDateFormat	sdf	= new SimpleDateFormat ( "yyyy-MM-dd	HH:mm:ss");  //사람이 이해할 수 있는 데이터 포멧으로 변경
			sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul")); //이 데이터 포멧의 timezone은 아시아/서울
			String	timeString	=	sdf.format(new java.util.Date (thing.timestamp*1000)); //데이터포멧에 input의 timestamp값에 1000을 곱한 것으로 data객체를 넣어줌
			
			return this.dynamoDb.getTable(DYNAMODB_TABLE_NAME)
					.putItem(new PutItemSpec().withItem(new Item().withPrimaryKey("time",	thing.timestamp)
							.withString("distance",	thing.state.reported.distance)
							.withString("LED",	thing.state.reported.LED)
							.withString("timestamp",timeString))); //DynamoDB에 저장
	}
	private void initDynamoDbClient() { 
			//DynamoDB Client객체를 얻어와서 초기화
			AmazonDynamoDB	client	=	AmazonDynamoDBClientBuilder.standard().withRegion("ap-northeast-2").build();
			this.dynamoDb	= new DynamoDB(client); //dynamoDb객체 생성
	}
}
class Thing {
	public	State	state	= new State();
	public long	timestamp;
	
	public class State {
			public	Tag	reported	= new Tag();
			public	Tag	desired	= new Tag();
			public class Tag {
					public	String	distance;
					public	String	LED;
			}
	}
}