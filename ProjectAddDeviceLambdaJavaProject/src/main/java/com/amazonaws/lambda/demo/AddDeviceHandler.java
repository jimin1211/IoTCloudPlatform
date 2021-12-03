package com.amazonaws.lambda.demo;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import com.amazonaws.services.iot.AWSIot;
import com.amazonaws.services.iot.AWSIotClientBuilder;
import com.amazonaws.services.iot.model.AttributePayload;
import com.amazonaws.services.iot.model.CreateThingRequest;
import com.amazonaws.services.iot.model.CreateThingResult;
import com.amazonaws.services.iot.model.CreateThingTypeRequest;
import com.amazonaws.services.iot.model.CreateThingTypeResult;
import com.amazonaws.services.iotdata.AWSIotData;
import com.amazonaws.services.iotdata.AWSIotDataClientBuilder;
import com.amazonaws.services.iotdata.model.UpdateThingShadowRequest;
import com.amazonaws.services.iotdata.model.UpdateThingShadowResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.annotation.JsonCreator;

public class AddDeviceHandler implements RequestHandler<Event, String> {

    @Override
    public String handleRequest(Event event, Context context) {
    	context.getLogger().log("Input: " + event);

    	AWSIot iot = AWSIotClientBuilder.standard().build();

        //AttributePayload payload = getPayload(event.tags);  //device shadow에 넘겨줄 payload(문자열)

        CreateThingRequest createThingRequest  = 
                new CreateThingRequest()
                    .withThingName(event.device);  //payload를 byte로 바꾸고 다시 ByteBuffer로 바꿔서 넣어줌

        CreateThingResult createThingResult = iot.createThing(createThingRequest).withThingArn(String.format("arn:aws:iot:ap-northeast-2:478929698592:thing/%s", event.device)).withThingName(event.device);
        
        /*CreateThingResult createThingResult = iotData.createThing();
        		withThingArn(String.format("arn:aws:iot:ap-northeast-2:478929698592:thing/%s", event.device)).withThingName(event.device);
        //result 객체의 문자열을 갖기 위해 복잡한 과정을 거침*/
        return event.device;
    }
    
    /*private AttributePayload getPayload(ArrayList<Tag> tags) {
        String tagstr = "";
        for (int i=0; i < tags.size(); i++) {
            if (i !=  0) tagstr += ", ";
            tagstr += String.format("\"%s\" : \"%s\"", tags.get(i).tagName, tags.get(i).tagValue);
        }
        return AttributePayload.
        return AttributePayload(String.format("{\"attributes\": {\"distance\":\"100\", \"LED\":\"OFF\"}}", tagstr));
    }*/
    
    //"{\"attributes\": {\"wattage\":\"75\", \"model\":\"123\"}}"

}

class Event {
    public String device;
    public ArrayList<Tag> tags;

    public Event() {
         tags = new ArrayList<Tag>();
    }
}

class Tag {
    public String tagName;
    public String tagValue;

    @JsonCreator 
    public Tag() {
    }

    public Tag(String n, String v) {
        tagName = n;
        tagValue = v;
    }
}