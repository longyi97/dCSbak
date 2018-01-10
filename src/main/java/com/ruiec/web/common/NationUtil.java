package com.ruiec.web.common;

import java.util.HashMap;

import com.ruiec.web.entity.Unit;

/**
 * 民族数据工具类
 * @date 2017年12月7日 下午2:54:28
 */
public class NationUtil {

	/** 民族全局静态变量 */
	private static HashMap<String, Unit> map=new HashMap<String, Unit>();
	
	/** 民族全局静态变量 */
	public static HashMap<String, Unit> getMap() {
		return map;
	}
	
	/** 民族全局静态变量 */
	public static void setMap(HashMap<String, Unit> map) {
		NationUtil.map = map;
	}
	
}
