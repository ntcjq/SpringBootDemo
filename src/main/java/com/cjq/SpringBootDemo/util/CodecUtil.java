package com.cjq.SpringBootDemo.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 编码解码工具类
 * @author lingjq
 * @date 2017年8月14日 下午6:06:16 
 *
 */
public class CodecUtil {
	private static final Logger logger = LoggerFactory.getLogger(CodecUtil.class);
	
	/**
	 * 进行md5摘要
	 * @param content
	 * @return
	 */
	public static String encodeMd5(String content) {
		return DigestUtils.md5Hex(content);
	}
	/**
	 * 进行base64编码
	 * @param content
	 * @return
	 */
	public static String encodeBase64(String content) {
		String encodeString = null;
		try {
			encodeString = new String(Base64.encodeBase64(content.getBytes("UTF-8")), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(StringUtil.format("对数据[{0}]进行base64编码出现异常", content), e);
		}
		return encodeString;
	}
	
	/**
	 * 解码使用base64编码的数据
	 * @param content
	 * @return
	 */
	public static String decodeBase64(String content) {
		String decodeString = null;
		try {
			decodeString = new String(Base64.decodeBase64(content.getBytes("UTF-8")), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(StringUtil.format("对数据[{0}]进行base64解码出现异常", content), e);
		}
		return decodeString;
	}
	
	/**
	 * 获取UUID
	 * @return
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString();
	}
	
	/**
	 * 获取去除了中划线的UUID
	 * @return
	 */
	public static String getUUIDWithoutMinus() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	public static String encodeUrl(String url) {
		try {
			return URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(StringUtil.format("对数据[{0}]进行URL编码出现异常", url), e);
		}
		return "";
	}
	
	public static String decodeUrl(String url) {
		try {
			return URLDecoder.decode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(StringUtil.format("对数据[{0}]进行URL解码出现异常", url), e);
		}
		return "";
	}
	
	public static String toHex(byte[] byteArray) {
		int i;
		StringBuffer buf = new StringBuffer("");
		int len = byteArray.length;
		for (int offset = 0; offset < len; offset++) {
			i = byteArray[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}
		return buf.toString().toUpperCase();
	}
	
}
