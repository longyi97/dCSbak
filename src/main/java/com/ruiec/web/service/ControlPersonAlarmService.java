package com.ruiec.web.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ruiec.framework.server.support.query.Page;
import com.ruiec.framework.server.support.service.BaseService;
import com.ruiec.web.common.LoginUserUnit;
import com.ruiec.web.entity.AlarmDTO;
import com.ruiec.web.entity.ControlPerson;
import com.ruiec.web.entity.ControlPersonAlarm;
import com.ruiec.web.entity.ControlPersonAlarmDTO;
import com.ruiec.web.entity.User;

/**
 * 重点人员预警服务接口
 * @date 2017年12月1日 上午9:13:21
 */
public interface ControlPersonAlarmService extends BaseService<ControlPersonAlarm, Integer>{
	/**
	 * 根据预警级别分类获取数量
	 * @date 2017年12月6日 下午10:50:06
	 */
	public List<ControlPersonAlarmDTO> getWarnCount();
	

	/**
	 * 获取稳控列表
	 * Date 2017年12月19日 
	 * */
	 public List<Object> steadyControl();

	/**
	 * 根据条件查询预警
	 * @date 2017年12月22日 下午10:50:06
	 */
	 public Map<String,Object> searchForAlarm(Page page,ControlPerson cp,ControlPersonAlarm cpam,AlarmDTO alarmDTO,LoginUserUnit loginUserUnit,User loginUser);

	/**
	 * 获取字典数据
	 * @date 2017年12月22日 下午10:50:06
	 */
	public List<Map<String, Object>> getDictionary(String name);
	
	/**
	 * 获取时间
	 * @date 2017年12月22日 下午10:50:06
	 */
	public Date getTimeOfDay(Date date,int hour,int min,int sec,int misec);
}
