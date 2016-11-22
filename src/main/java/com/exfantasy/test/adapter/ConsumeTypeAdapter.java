package com.exfantasy.test.adapter;

import java.io.IOException;

import com.exfantasy.test.enu.Type;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * http://www.javacreed.com/gson-typeadapter-example/
 * 
 * @author tommy.feng
 *
 */
public class ConsumeTypeAdapter extends TypeAdapter<Type> {

	@Override
	public Type read(JsonReader in) throws IOException {
		int code = in.nextInt();
		return Type.convert(code);
	}
	
	@Override
	public void write(JsonWriter out, final Type value) throws IOException {
		out.value(value.getCode());
	}
}
