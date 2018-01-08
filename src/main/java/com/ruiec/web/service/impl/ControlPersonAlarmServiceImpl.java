/*
 * 版权所有：深圳源中瑞科技有限公司<br>
 * 网 址：www.ruiec.com<br>
 * 电 话：0755-33581131<br><br>
 */

package com.ruiec.web.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruiec.framework.server.support.query.Filter;
import com.ruiec.framework.server.support.query.Page;
import com.ruiec.framework.server.support.service.impl.BaseServiceImpl;
import com.ruiec.web.common.LoginUserUnit;
import com.ruiec.web.entity.AlarmDTO;
import com.ruiec.web.entity.ControlPerson;
import com.ruiec.web.entity.ControlPersonAlarm;
import com.ruiec.web.entity.ControlPersonAlarmDTO;
import com.ruiec.web.entity.Unit;
import com.ruiec.web.entity.User;
import com.ruiec.web.service.ControlPersonAlarmService;
import com.ruiec.web.service.DictionaryService;
import com.ruiec.web.service.UnitService;

/**
 * 重点人员预警服务接口实现类
 * @author Senghor<br>
 * @date 2017年11月30日 上午9:01:37
 */
@Service("controlPersonAlarmServiceImpl")
public class ControlPersonAlarmServiceImpl extends BaseServiceImpl<ControlPersonAlarm, Integer> implements ControlPersonAlarmService{
	@Resource
	private UnitService unitService;
	@Resource
	private DictionaryService dictionaryService;
	
	/**
	 * 根据预警级别分类获取数量
	 * @author 陈靖原<br>
	 * @date 2017年11月29日 下午10:50:06
	 */
	@Override
	@Transactional(readOnly = true)
	public List<ControlPersonAlarmDTO> getWarnCount() {
		String hql = "select new com.ruiec.web.entity.ControlPersonAlarmDTO(count(*),cpa.warningLevel) from ControlPersonAlarm cpa "
				+ ",ControlPerson cp where cpa.createDate >=:startDate and cpa.createDate <=:endDate and cp.id = cpa.controlPerson.id and cp.isDelete = 0 group by cpa.warningLevel";
		Query query = getSession().createQuery(hql);
		query.setParameter("startDate", getTimeOfDay(new Date(),0,0,0,0));
		query.setParameter("endDate", getTimeOfDay(new Date(),23,59,59,999));
		List<ControlPersonAlarmDTO> list = query.list();
		return list;
	}
	
	//获取时间
	public Date getTimeOfDay(Date date,int hour,int min,int sec,int misec) {
		Calendar day = Calendar.getInstance();
		day.setTime(date);
		day.set(Calendar.HOUR_OF_DAY,hour);
		day.set(Calendar.MINUTE,min);
		day.set(Calendar.SECOND,sec);
		day.set(Calendar.MILLISECOND,misec);
		return day.getTime();
	}

	/**
	 * 根据idCard去重，在用排序获取最大的id
	 * @author yuankai 
	 * Date 2017年12月19日 
	 * */
	 @Override
	 @Transactional(readOnly = true)
     public List<Object> steadyControl(){
		String sql="select max(T.PRIKEY) as from T_COR_CONTROL_PERSON_ALARM  T group by CONTROL_PERSON_PRIKEY";
        Query query=getSession().createSQLQuery(sql);
        List<Object> list=query.list();
    	return list;
     }
	
	/**
	 * 根据条件查询预警
	 * @author 陈靖原<br>
	 * @date 2017年11月29日 下午10:50:06
	 */
	@Override
	@Transactional(readOnly = true)
	public Map<String,Object> searchForAlarm(Page page,ControlPerson cp,ControlPersonAlarm cpam,AlarmDTO alarmDTO,LoginUserUnit loginUserUnit,User loginUser) {
		Map<String,Object> map = new HashMap<String,Object>();
		//
		DetachedCriteria cpa = DetachedCriteria.forClass(ControlPersonAlarm.class);
		DetachedCriteria cpn = cpa.createCriteria("controlPerson");
		cpn.setFetchMode("controlPersonTypes", FetchMode.SELECT);
		DetachedCriteria au = cpa.createCriteria("alarmUnit");
		DetachedCriteria cu = cpa.createCriteria("controlUnit");
		//根据登录用户身份查看对应数据
		if(null!=loginUserUnit) {
			if(1==loginUser.getUnit().getId()) {
				//市局管理员
			}else {
				//管理员
				cu.add(Restrictions.in("id", loginUserUnit.getUnitSonIds().toArray()));
			}
		}else {
			//警员
			cu.add(Restrictions.eq("id", loginUser.getUnit().getId()));
		}
		//重点人身份证号
		if(!StringUtils.isBlank(cp.getIdCard())) {
			cpn.add(Restrictions.like("idCard", cp.getIdCard() , MatchMode.ANYWHERE));
		}
		//重点人姓名
		if(!StringUtils.isBlank(cp.getName())) {
			cpn.add(Restrictions.like("name", cp.getName() , MatchMode.ANYWHERE));
		}
		//是否签收 1.未签收 2.已签收
		if(null != cpam.getSignStatus()) {
			cpa.add(Restrictions.eq("signStatus", cpam.getSignStatus()));
		}
		//是否反馈(反馈时间不为空时为已反馈，否则未反馈)
		if(!StringUtils.isBlank(cpam.getFeedbackTime())) {
			if(!"no".equals(cpam.getFeedbackTime())) {
				cpa.add(Restrictions.isNotNull("feedbackTime"));
			}else {
				cpa.add(Restrictions.isNull("feedbackTime"));
			}
		}
		//尚未被逻辑删除的重点人
		cpn.add(Restrictions.eq("isDelete",0));
		//人员类别
        // 根据人员类别id查询下级所有数据
        if (null!=alarmDTO.getPersonType()) {
        	Integer[] personTypeIds = dictionaryService.findSonId(alarmDTO.getPersonType());
			if (personTypeIds!=null&&personTypeIds.length>0) {
				cpn.createAlias("controlPersonTypes", "c");
				cpn.add(Restrictions.in("c.dictionaryId", personTypeIds));
			}
        }
		//登记时间
		if(null!=alarmDTO.getStartDate()){
			cpa.add(Restrictions.ge("createDate",alarmDTO.getStartDate()));
		}
		if(null!=alarmDTO.getEndDate()) {
			cpa.add(Restrictions.le("createDate",alarmDTO.getEndDate()));
		}
		//动态信息类别
		if(null!=alarmDTO.getDynamicInfoPrikeyz()) {
			cpa.add(Restrictions.in("dynamicInfoPrikey", alarmDTO.getDynamicInfoPrikeyz()));
		}
		//预警级别（当天）
		if(!StringUtils.isBlank(alarmDTO.getTodayAlarm())) {
			cpa.add(Restrictions.eq("warningLevel", alarmDTO.getTodayAlarm()));
			//开始时间
			cpa.add(Restrictions.ge("createDate",getTimeOfDay(new Date(),0,0,0,0)));
			//结束时间
			cpa.add(Restrictions.le("createDate",getTimeOfDay(new Date(),23,59,59,999)));
		}
		//预警地区
		if(null!=alarmDTO.getUnitId()) {
			au.add(Restrictions.eq("id", alarmDTO.getUnitId()));
		}
		//预警级别
		if(StringUtils.isNotBlank(alarmDTO.getWarningLevel())) {
			cpa.add(Restrictions.eq("warningLevel", alarmDTO.getWarningLevel()));
		}
		
		//字典查询
		//轨迹类型
		List<Map<String, Object>> dynamicInfo = getDictionary("track");
		map.put("dynamicInfo",dynamicInfo);
		//人员类别
		List<Map<String, Object>> dataitem = getDictionary("personClass");
		map.put("dataitem",dataitem);
		//预警地区
		List<Unit> units = unitService.findList(null, Filter.eq("unitRank" , "area"), null);
		map.put("units",units);
		//预警级别
		List<Map<String, Object>> warningLevels = getDictionary("warningLevel");
		map.put("warningLevels",warningLevels);
		//分页
        findByPage(page,cpa);
		map.put("page", page);
		//预警总数
		long count =  page.getTotalCount();
		map.put("count", count);
		
		//查询所有预警类型
		List<Map<String, Object>> lists = getDictionary("warningLevel");
		//查询当日的预警次数
		List<ControlPersonAlarmDTO> list =  getWarnCount();
		//不同预警的预警次数
		List<Map<String,Object>> listz = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < lists.size(); i++) {
			Map<String,Object> maps = new HashMap<String,Object>();
			maps.put("id", lists.get(i).get("id"));
			maps.put("itemName", lists.get(i).get("itemName"));
			for (int j = 0; j < list.size(); j++) {
				if ((lists.get(i).get("id").toString()).equals(list.get(j).getWarningLevel())) {
					maps.put("count", list.get(j).getCount());
				}
			}
			listz.add(maps);
		}
		map.put("listz", listz);
		return map;
	}
	
	//获取字典数据
	public List<Map<String, Object>> getDictionary(String name) {
		List<Map<String, Object>> maps=dictionaryService.findByItemCode(name,0);
		if (name!=null) {
			//根据类型id查询简单类型数据
			maps=dictionaryService.findByItemCode(name,0);
		}
		 return maps;
	}
}
