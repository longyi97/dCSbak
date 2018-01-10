package com.ruiec.web.common;

import java.util.HashMap;

import com.ruiec.web.entity.User;

/**
 * 民族数据工具类
 * Date：2017年09月29日
 */
public class UserUtil {

	/** 民族全局静态变量 */
	private static HashMap<String, User> map=new HashMap<String, User>();
	
	/** 民族全局静态变量 */
	public static HashMap<String, User> getMap() {
		return map;
	}
	
	/** 民族全局静态变量 */
	public static void setMap(HashMap<String, User> map) {
		UserUtil.map = map;
	}
	
}
