package com.exfantasy.test.serializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.TimeZone;

import com.exfantasy.test.enu.Type;
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
	    // FIXME 處理 LocalDate 轉換錯誤問題
	    LocalDate consumeDate = getDateFromTimestamp(lConsumeDate);
	    
	    int type = node.get("type").asInt();
	    String prodName = node.get("prodName").asText();
	    int amount = node.get("amount").asInt();
	    String lotteryNo = node.get("lotteryNo").asText();
	    boolean got = node.get("got").asBoolean();
	    int prize = node.get("prize").asInt();
	    
	    Consume consume = new Consume();
	    consume.setConsumeDate(consumeDate);
	    consume.setType(Type.convert(type));
	    consume.setProdName(prodName);
	    consume.setAmount(amount);
	    consume.setLotteryNo(lotteryNo);
	    consume.setGot(got);
	    consume.setPrize(prize);
	    
		return consume;
	}

	public LocalDateTime getDateTimeFromTimestamp(long timestamp) {
		if (timestamp == 0)
			return null;
		return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), TimeZone.getDefault().toZoneId());
	}

	public LocalDate getDateFromTimestamp(long timestamp) {
		LocalDateTime date = getDateTimeFromTimestamp(timestamp);
		return date == null ? null : date.toLocalDate();
	}
}
