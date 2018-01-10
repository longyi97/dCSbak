
package com.ruiec.server.common.service;

import java.util.List;

import com.ruiec.framework.server.support.service.BaseService;
import com.ruiec.server.common.entity.SysConfig;

/**
 * 系统配置项服务接口
 * Version: 1.0<br>
 * Date: 2015年12月22日
 */
public interface SysConfigService extends BaseService<SysConfig, String>{
	
	/**
	 * 批量更新系统配置
	 * Date: 2016年1月6日
	 */
	public void update(List<SysConfig> sysConfigs);
}
