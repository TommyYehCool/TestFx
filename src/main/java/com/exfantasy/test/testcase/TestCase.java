package com.exfantasy.test.testcase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.exfantasy.utils.http.HttpUtil;
import com.exfantasy.utils.http.HttpUtilException;

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
	
	public static void testDateTimeConevert() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");	
		Date date = new Date(1477324800000L);
		String format = dateFormat.format(date);
		System.out.println(format);
	}
	
	public static void main(String[] args) {
		testDateTimeConevert();
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
