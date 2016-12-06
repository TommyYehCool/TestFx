package com.exfantasy.test.vo;

import com.exfantasy.test.cnst.ResultCode;
import com.exfantasy.test.json.deserializer.ResponseVoDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * http://stackoverflow.com/questions/32794500/serialize-javafx-model-with-gson
 * 
 * @author tommy.feng
 *
 */
@JsonDeserialize(using = ResponseVoDeserializer.class)
public class ResponseVo {
	private ResultCode resultCode;
	private Object data;
	
	public ResponseVo(ResultCode resultCode, Object data) {
		this.resultCode = resultCode;
		this.data = data;
	}
	
	public ResultCode getResultCode() {
		return resultCode;
	}
	
	public Object getData() {
		return data;
	}
}
