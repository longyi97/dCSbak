package com.ruiec.web.common;

import java.util.HashMap;

import com.ruiec.web.entity.Unit;

/**
 * 单位编码数据工具类
 * @date 2017年12月7日 下午2:54:28
 */
public class UnitCodeUtil {

	/** 单位编码全局静态变量 */
	private static HashMap<String, Unit> map=new HashMap<String, Unit>();
	
	/** 单位编码全局静态变量 */
	public static HashMap<String, Unit> getMap() {
		return map;
	}
	
	/** 单位编码全局静态变量 */
	public static void setMap(HashMap<String, Unit> map) {
		UnitCodeUtil.map = map;
	}
	
	/**
	 * 根据单位编码获取单位实体
	 * 
	 * @date 2017年12月26日 上午9:39:17
	 */
	public static Unit matchUnit(String unitCode) {
		Unit unit = null;
		if (null != (unit = map.get(unitCode))) {
			return unit;
		} else if (null != (unit = map.get(unitCode.substring(0, 8).concat("0000")))) {
			return unit;
		} else if (null != (unit = map.get(unitCode.substring(0, 6).concat("000000")))) {
			return unit;
		}
		return null;
	}
	
	/**
	 * 根据单位编码获取单位实体
	 * 
	 * @date 2017年12月26日 上午9:39:17
	 */
	public static Unit matchUnit(String unitCode, String defaulUnitCode) {
		if (null != unitCode && unitCode.length() == 12) {
			Unit unit = null;
			if (null != (unit = map.get(unitCode))) {
				return unit;
			} else if (null != (unit = map.get(unitCode.substring(0, 8).concat("0000")))) {
				return unit;
			} else if (null != (unit = map.get(unitCode.substring(0, 6).concat("000000")))) {
				return unit;
			}
		}
		return map.get(defaulUnitCode);
	}
}
