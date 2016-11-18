package com.exfantasy.test.testcase;

import java.util.HashMap;

import com.exfantasy.utils.http.HttpUtil;
import com.exfantasy.utils.http.HttpUtilException;
import com.google.gson.Gson;

public class TestCase {
	public static void testSendJsonHttpPost() {
		String url = "http://localhost:8080/night-web/user/login";
		HashMap<String, Object> data = new HashMap<>();
		data.put("username", "bensonQQQQ");
		data.put("password", "abc123");
		data.put("validCode", "LKWR");
		String jsonData = new Gson().toJson(data);
		try {
			HttpUtil.sendPostRequest(url, jsonData);
		} catch (HttpUtilException e) {
			e.printStackTrace();
		}
	}

	public static void testSendHttpGet() {
		String url = "http://localhost:8080/night-web/user/team/member";
		try {
			HttpUtil.sendGetRequest(url);
		} catch (HttpUtilException e) {
			e.printStackTrace();
		}
	}
}
