package com.cjq.SpringBootDemo;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.conn.ConnectTimeoutException;

import com.alibaba.fastjson.JSONObject;
import com.cjq.SpringBootDemo.util.CodecUtil;
import com.cjq.SpringBootDemo.util.HttpUtil;

public class DailyTest {

	public static void main(String[] args) throws ConnectTimeoutException, SocketTimeoutException, Exception {
		applyToken();
	}
	
	public static void applyToken() throws ConnectTimeoutException, SocketTimeoutException, Exception {
		String url = "http://localhost:9080/bpm/oauth2/v1/token";
		String appId = "EHR";
		Long timestamp = System.currentTimeMillis()/1000;
		JSONObject json = new JSONObject();

		json.put("appId", appId);
		json.put("timestamp", timestamp);
		json.put("refreshToken", "6e65c9a1-b118-45c3-9851-21a3ee282540");
		//获取
//		json.put("grantType", "credentials");
//		String Authorization = "Basic" + " "
//				+ CodecUtil.encodeBase64(
//						appId +"||" + timestamp.toString()
//						+ CodecUtil.encodeMd5(appId + "50d613ec-83ed-4997-a957-dfcfef29464e" + timestamp.toString()));
		//刷新
		json.put("grantType", "refresh");
		String Authorization =  CodecUtil.encodeMd5(
				"Basic" + " "
				+ CodecUtil.encodeBase64(
						appId + "||" + timestamp.toString()
						+ CodecUtil.encodeMd5(appId + "50d613ec-83ed-4997-a957-dfcfef29464e" + timestamp.toString())));
		Map headers = new HashMap();
		headers.put("Authorization", Authorization);
		String jsonStr = json.toJSONString();
		String result = HttpUtil.postJson(url, jsonStr, headers);
		System.out.println(result);
		
	}
	public static void validToken() throws ConnectTimeoutException, SocketTimeoutException, Exception {
		String url = "http://localhost:9080/bpm/api/route";
		String appId = "WECHAT";
		Long timestamp = System.currentTimeMillis()/1000;
		JSONObject json = new JSONObject();
		String jsonStr = json.toJSONString();
		Map headers = new HashMap();
		String Authorization = CodecUtil.encodeMd5("14cc6fe9-2c64-4108-8f7a-f4f5f024978e" + timestamp);
		headers.put("Authorization", Authorization);
		headers.put("appId", appId);
		headers.put("timestamp", timestamp+"");
		String result = HttpUtil.postJson(url, jsonStr, headers);
		System.out.println(result);
		
	}
}
