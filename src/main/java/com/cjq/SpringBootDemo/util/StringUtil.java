package com.cjq.SpringBootDemo.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	/**
	 * 判断字符串是否为null或空字符串
	 * @param content
	 * @return
	 */
	public static boolean isNullOrBlank(String content) {
		return (content == null || content.trim().isEmpty());
	}
	
	/**
	 * 使用字段字符向左填充值指定长度
	 * @param value
	 * @param length
	 * @param padding
	 * @return
	 */
	public static String formatNumber(int value, int length, String padding) {
		String pattern = "%" + padding + length + "d";
		return String.format(pattern, value);
	}
	
	/**
	 * 使用正则表达式获取字符串子串
	 * @param regex
	 * @param source
	 * @return
	 */
	public static List<String> getMatcher(String regex, String source) {  
		if(StringUtil.isNullOrBlank(source)) {
			return new ArrayList<String>();
		}
        List<String> result = new ArrayList<String>();  
        Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(source);  
        while (matcher.find()) {  
        	//System.out.println(matcher.group(0));
        	result.add(matcher.group());
        }  
        return result;  
   }  
   
	/**
	 * 字符集合去重
	 * @param inList
	 * @return
	 */
	public static List<String> removeRepeat(List<String> inList){
		List<String> outList = new ArrayList<String>();
		Set<String> set = new HashSet<String>();
		for (String obj : inList) {
			if(set.add(obj)) {
				outList.add(obj);
			}
		}
		return outList;
	}
	
	/**
	 * 判断一个字符串是否为非数字类型，true-非数字，false-数字
	 * @param source
	 * @return
	 */
	public static boolean isCharacter(String source) {
		if(StringUtil.isNullOrBlank(source)) {
			return false;
		}
		char firstChar = source.trim().charAt(0);
		// 不是已1~9数字开头均为字符串
		if(firstChar <= '0' || firstChar > '9') {
			return true;
		} else {
			boolean hasChar = false;
			for(char tempChar : source.trim().toCharArray()) {
				if(tempChar < '0' || tempChar > '9') {
					hasChar = true;
					break;
				}
			}
			if(hasChar) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	
	/**
	 * 字符串占位符格式化
	 * 例：StringUtil.format("未找到初始化环节的定义信息，流程定义ID：{0}，环节名：{1}", "4001", "提交");
	 * @param pattern
	 * @param args
	 * @return
	 */
	public static String format(String pattern, Object... args) {
		Object[] tempArr = null;
		if(args != null) {
			tempArr = new Object[args.length];
			int index = 0;
			for(Object arg : args) {
				String value = null;
				if(arg == null) {
					value = "";
				} else {
					value = arg.toString();
				}
				tempArr[index] = value;
				index++;
			}
		}
		return MessageFormat.format(pattern, tempArr);
	}
	
	/**
	 * 对尖括号进行转义
	 * @param source
	 * @return
	 */
	public static String parseAngleBracket(String source) {
		if(source != null) {
			source = source.replaceAll(">", "&gt;").replaceAll("<", "&lt;");
			
		}
		return source;
	}
	
}
