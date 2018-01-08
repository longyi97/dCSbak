package com.ruiec.web.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 预警查询条件实体
 * @author 陈靖原<br>
 * @date 2017年12月22日 上午9:24:55
 */
public class AlarmDTO implements Serializable{
	
	private static final long serialVersionUID = 5875862482709758377L;
	/** 人员类别*/
	Integer personType;
	/** 预警地区*/
	Integer unitId;
	/** 开始日期*/
	Date startDate;
	/** 结束日期*/
	Date endDate;
	/** 动态信息*/
	Integer[] dynamicInfoPrikeyz;
	/** 预警级别（当天）*/
	String todayAlarm;
	/** 预警级别*/
	String warningLevel;

	/** 人员类别*/
	public Integer getPersonType() {
		return personType;
	}
	/** 人员类别*/
	public void setPersonType(Integer personType) {
		this.personType = personType;
	}
	/** 预警地区*/
	public Integer getUnitId() {
		return unitId;
	}
	/** 预警地区*/
	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}
	/** 开始日期*/
	public Date getStartDate() {
		return startDate;
	}
	/** 开始日期*/
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/** 结束日期*/
	public Date getEndDate() {
		return endDate;
	}
	/** 结束日期*/
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/** 动态信息*/
	public Integer[] getDynamicInfoPrikeyz() {
		return dynamicInfoPrikeyz;
	}
	/** 动态信息*/
	public void setDynamicInfoPrikeyz(Integer[] dynamicInfoPrikeyz) {
		this.dynamicInfoPrikeyz = dynamicInfoPrikeyz;
	}
	/** 预警级别（当天）*/
	public String getTodayAlarm() {
		return todayAlarm;
	}
	/** 预警级别（当天）*/
	public void setTodayAlarm(String todayAlarm) {
		this.todayAlarm = todayAlarm;
	}
	/** 预警级别*/
	public String getWarningLevel() {
		return warningLevel;
	}
	/** 预警级别*/
	public void setWarningLevel(String warningLevel) {
		this.warningLevel = warningLevel;
	}

}
