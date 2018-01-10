package com.ruiec.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ruiec.framework.server.support.entity.BaseEntity;

/**
 * 轨迹数据实体
 * 
 * @date 2017年12月22日 下午3:51:12
 */
@Entity
@Table(name="T_COR_DYNAMIC_INFO")
public class DynamicInfo extends BaseEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 4702070965362618263L;
	/** 出发地 */
	private String origin;
	/** 目的地 */
	private String destination;
	/** 出发时间/触发时间 */
	private String triggerTime;
	/** 轨迹类型 */
	private Integer type;
	/** 轨迹数据 */
	private String information;
	/** 重点人主键 */
//	private Integer controlPersonPrikey;
	private ControlPerson controlPerson;
	/** 预警地单位 */
	private Unit alarmUnit;

	/** 获取 origin */
	public String getOrigin() {
		return origin;
	}

	/** 设置origin */
	public DynamicInfo setOrigin(String origin) {
		this.origin = origin;
		return this;
	}

	/** 获取 destination */
	public String getDestination() {
		return destination;
	}

	/** 设置destination */
	public DynamicInfo setDestination(String destination) {
		this.destination = destination;
		return this;
	}

	/** 获取 triggerTime */
	@Column(name="TRIGGER_TIME")
	public String getTriggerTime() {
		return triggerTime;
	}

	/** 设置triggerTime */
	public DynamicInfo setTriggerTime(String triggerTime) {
		this.triggerTime = triggerTime;
		return this;
	}

	/** 获取 type */
	public Integer getType() {
		return type;
	}

	/** 设置type */
	public DynamicInfo setType(Integer type) {
		this.type = type;
		return this;
	}

	/** 获取 information */
	public String getInformation() {
		return information;
	}

	/** 设置information */
	public DynamicInfo setInformation(String information) {
		this.information = information;
		return this;
	}

	/** 获取 controlPerson */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONTROL_PERSON_PRIKEY")
	public ControlPerson getControlPerson() {
		return controlPerson;
	}

	/** 设置controlPerson */
	public DynamicInfo setControlPerson(ControlPerson controlPerson) {
		this.controlPerson = controlPerson;
		return this;
	}

	/** 获取 alarmUnit */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ALARM_UNIT_PRIKEY", nullable = false)
	public Unit getAlarmUnit() {
		return alarmUnit;
	}

	/** 设置alarmUnit */
	public DynamicInfo setAlarmUnit(Unit alarmUnit) {
		this.alarmUnit = alarmUnit;
		return this;
	}

}
