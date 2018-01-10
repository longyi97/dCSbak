package com.ruiec.web.enumeration;
/**
 * 
 * 
 * 四舍五入枚举
 * @author 肖学良<br>
 * Version: 1.0<br>
 * Date: 2015年12月21日
 */
public enum RoundType {
	
	roundHalfUp("四舍五入"), 
	roundUp("向上取整"), 
	roundDown("向下取整");
	
	private String name;

	private RoundType(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
