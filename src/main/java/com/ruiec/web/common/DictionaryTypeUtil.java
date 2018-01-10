package com.ruiec.web.common;

import java.util.HashMap;

import com.ruiec.web.entity.DictionaryType;

/**
 * 字典类型数据工具类
 * Date：2017年09月29日
 */
public class DictionaryTypeUtil {

	/** 字典类型全局静态变量 */
	private static HashMap<String, DictionaryType> map=new HashMap<String, DictionaryType>();
	
	/** 字典类型全局静态变量 */
	public static HashMap<String, DictionaryType> getMap() {
		return map;
	}
	
	/** 民字典类型全局静态变量 */
	public static void setMap(HashMap<String, DictionaryType> map) {
		DictionaryTypeUtil.map = map;
	}
	
}
