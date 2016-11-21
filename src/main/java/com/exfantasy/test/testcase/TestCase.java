package com.exfantasy.test.testcase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.exfantasy.utils.http.HttpUtil;
import com.exfantasy.utils.http.HttpUtilException;
import com.google.gson.Gson;

public class TestCase {
	public static void testDateConvert() {
		SimpleDateFormat df = new SimpleDateFormat("MMM d, yyyy HH:mm:ss aa", Locale.ENGLISH);
		String strDate = "Nov 17, 2016 12:00:00 AM";
		try {
			Date date = df.parse(strDate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			System.out.println(">>>> " + df.format(cal.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		testDateConvert();
	}
	
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
