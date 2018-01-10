
package com.ruiec.server.common.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruiec.framework.server.support.service.impl.BaseServiceImpl;
import com.ruiec.server.common.entity.SysConfig;
import com.ruiec.server.common.service.SysConfigService;
/**
 * 系统配置项服务接口实现类
 * Version: 1.0<br>
 * Date: 2015年5月20日
 */
@Service
public class SysConfigServiceImpl extends BaseServiceImpl<SysConfig, String> implements SysConfigService {

	@Override
	@Transactional
	public void update(List<SysConfig> sysConfigs) {
		for(SysConfig sysConfig : sysConfigs){
			updateIgnore(sysConfig, null, null);
		}
	}
	
}
