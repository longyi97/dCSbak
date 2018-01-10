package com.ruiec.web.service.impl;

import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruiec.framework.server.support.service.impl.BaseServiceImpl;
import com.ruiec.web.entity.ControlPerson;
import com.ruiec.web.entity.ControlPersonAlarm;
import com.ruiec.web.entity.DynamicInfo;
import com.ruiec.web.service.ControlPersonAlarmService;
import com.ruiec.web.service.DynamicInfoService;

/**
 * 轨迹数据服务实现类
 * 
 * @date 2017年12月23日 上午9:25:37
 */
@Service
public class DynamicInfoServiceImpl extends BaseServiceImpl<DynamicInfo, String> implements DynamicInfoService {

	@Resource
	private ControlPersonAlarmService controlPersonAlarmService;
	
	/**
	 * 保存轨迹记录，同时生成预警记录
	 * 
	 * @date 2017年12月25日 下午2:12:08
	 */
	@Override
	@Transactional
	public DynamicInfo save(DynamicInfo entity) {
		// 保存轨迹记录
		super.save(entity);
		ControlPersonAlarm controlPersonAlarm = new ControlPersonAlarm();
		ControlPerson controlPerson = entity.getControlPerson();
		controlPersonAlarm.setControlPerson(controlPerson);
		controlPersonAlarm.setIdCard(controlPerson.getIdCard());
		controlPersonAlarm.setWarningLevel(new Random().nextInt(3) + 1);
		controlPersonAlarm.setControlUnit(controlPerson.getUnit());
		controlPersonAlarm.setOrigin(entity.getOrigin());
		controlPersonAlarm.setDestination(entity.getDestination());
		controlPersonAlarm.setDynamicInfoPrikey(entity.getId());
		controlPersonAlarm.setDistributeStatus(1);
		controlPersonAlarm.setSignStatus(1);
		// 匹配预警地
		controlPersonAlarm.setAlarmUnit(entity.getAlarmUnit());
		// 生成预警记录
		controlPersonAlarmService.save(controlPersonAlarm);
		return entity;
	}

	
}
