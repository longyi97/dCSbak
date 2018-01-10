
package com.ruiec.framework.server.support.query;

import java.io.Serializable;
import java.util.TreeSet;
/**
 * 初始化组装类
 * Version: 1.0<br>
 * Date: 2016年1月9日
 */
public class InitPathBuilder implements Serializable {

	private static final long serialVersionUID = 4619451744974498450L;
	
	private java.util.Set<String> initPathSet = new TreeSet<String>();
	
	public static InitPathBuilder create(){
		return new InitPathBuilder();
	}
	
	/**
	 * 添加初始化路径
	 * Date: 2016年1月9日
	 */
	public InitPathBuilder add(String initPath){
		initPathSet.add(initPath);
		return this;
	}
	/**
	 * 返回初始化路径集
	 * Date: 2016年1月9日
	 */
	public java.util.Set<String> build(){
		return initPathSet;
	}
}
