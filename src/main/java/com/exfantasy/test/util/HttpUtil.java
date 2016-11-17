package com.exfantasy.test.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.exfantasy.test.exception.HttpUtilException;

public class HttpUtil {
	public static String sendPostRequest(String url, String jsonData) throws HttpUtilException {
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(url);
			
			StringEntity input = new StringEntity(jsonData);
			input.setContentType("application/json");
			post.setEntity(input);
			
			HttpResponse response = client.execute(post);
			
			int httpStatusCode = response.getStatusLine().getStatusCode();
			if (httpStatusCode != HttpStatus.SC_OK) {
				throw new HttpUtilException("Failed - HTTP error code: " + httpStatusCode);
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String responseData = null;
			String output;
			while ((output = br.readLine()) != null) {
				responseData = output;
			}
			System.out.println("HttpStatusCode: " + httpStatusCode + ", response: " + responseData);
			return responseData;
		} catch (IOException e) {
			throw new HttpUtilException("IOException raised while send HTTP post to url: " + url, e);
		}
	}
}
