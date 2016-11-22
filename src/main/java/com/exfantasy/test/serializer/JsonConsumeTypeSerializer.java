package com.exfantasy.test.serializer;

import java.io.IOException;

import com.exfantasy.test.enu.Type;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JsonConsumeTypeSerializer extends JsonSerializer<Type> {

	@Override
	public void serialize(Type value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		gen.writeNumber(value.getCode());
	} 
}

