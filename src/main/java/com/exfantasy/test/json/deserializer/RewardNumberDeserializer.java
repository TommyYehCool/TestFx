package com.exfantasy.test.json.deserializer;

import java.io.IOException;

import com.exfantasy.test.vo.RewardNumber;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class RewardNumberDeserializer extends JsonDeserializer<RewardNumber> {

	@Override
	public RewardNumber deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		ObjectCodec oc = jp.getCodec();
		JsonNode node = oc.readTree(jp);
		
		String section = node.get("section").asText();
		int rewardType = node.get("rewardType").asInt();
		String number = node.get("no").asText();
		
		RewardNumber rewardNumber = new RewardNumber();
		rewardNumber.setSection(section);
		rewardNumber.setRewardType(rewardType);
		rewardNumber.setNo(number);
		
		return rewardNumber;
	}

}
