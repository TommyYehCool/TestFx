package com.exfantasy.test.json.deserializer;

import java.io.IOException;

import com.exfantasy.test.cnst.ResultCode;
import com.exfantasy.test.vo.ResponseVo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class ResponseVoDeserializer extends JsonDeserializer<ResponseVo> {

	@Override
	public ResponseVo deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		ObjectCodec oc = jp.getCodec();
		JsonNode node = oc.readTree(jp);
		
		int code = node.get("resultCode").asInt();
		ResultCode resultCode = ResultCode.convert(code);
		String data = node.get("data").asText();
		
		ResponseVo responseVo = new ResponseVo(resultCode, data);
		
		return responseVo;
	}

}
