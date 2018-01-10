package com.ruiec.web.common;

import java.util.HashMap;

import com.ruiec.web.entity.Dictionary;

/**
 * 字典数据工具类
 * Date：2017年09月29日
 */
public class DictionaryUtil {

	/** 字典全局静态变量 */
	private static HashMap<String, Dictionary> map=new HashMap<String, Dictionary>();
	
	/** 字典全局静态变量 */
	public static HashMap<String, Dictionary> getMap() {
		return map;
	}
	
	/** 字典全局静态变量 */
	public static void setMap(HashMap<String, Dictionary> map) {
		DictionaryUtil.map = map;
	}
	
}
