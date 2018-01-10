package com.ruiec.web.common;

import com.ruiec.web.util.SettingUtils;

/**
 * 
 * 
 * WEB全局变量,用于页面获取公共数据
 * Version: 1.0<br>
 * Date: 2015年12月24日
 */

public class Global {
	
	private String authorityCenterUrl;
	
	/**
	 * 获取系统配置
	 * Date: 2015年12月24日
	 */
	public Setting getSetting(){
		return SettingUtils.get();
	}
	
	/**
	 * 获取认证中心域名
	 * Date: 2016年1月17日
	 */
	public String getAuthorityCenterUrl() {
		return authorityCenterUrl;
	}

	/**
	 * 设置认证中心域名
	 * Date: 2016年1月17日
	 */
	public void setAuthorityCenterUrl(String authorityCenterUrl) {
		this.authorityCenterUrl = authorityCenterUrl;
	}
	
}
