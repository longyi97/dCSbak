package com.ruiec.web.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员管理单位全局静态变量<br>
 * 键值为userid<br>
 * List<Map<String, Object>>值如下<br>
 * id:单位id<br>
 * unitRank:单位级别<br>
 * unitName:单位名称<br>
 * @date 2017年12月18日 上午10:34:33
 */
public class UserUnitUtil {

	/** 管理员管理单位全局静态变量 */
	private static HashMap<Integer, List<Map<String, Object>>> map = new HashMap<Integer, List<Map<String, Object>>>();

	/** 管理员管理单位全局静态变量 */
	public static HashMap<Integer, List<Map<String, Object>>> getMap() {
		return map;
	}

	/** 管理员管理单位全局静态变量 */
	public static void setMap(HashMap<Integer, List<Map<String, Object>>> map) {
		UserUnitUtil.map = map;
	}
}
