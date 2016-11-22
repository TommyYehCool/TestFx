package com.exfantasy.test.serializer;

import java.io.IOException;

import com.exfantasy.test.vo.Consume;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class ConsumeDeserializer extends JsonDeserializer<Consume> {

	@Override
	public Consume deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		ObjectCodec oc = jp.getCodec();
	    JsonNode node = oc.readTree(jp);
		
	    long lConsumeDate = node.get("consumeDate").asLong();
	    int type = node.get("type").asInt();
	    String prodName = node.get("prodName").asText();
	    int amount = node.get("amount").asInt();
	    String lotteryNo = node.get("lotteryNo").asText();
	    boolean got = node.get("got").asBoolean();
	    int prize = node.get("prize").asInt();
	    
		return null;
	}

}
