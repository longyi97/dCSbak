package com.ruiec.web.entity;

import java.beans.Transient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.ruiec.framework.server.support.entity.BaseEntity;


/**
 * 重点人预警实体
 * @author Senghor<br>
 * @date 2017年12月1日 上午11:00:41
 */
@Entity
@Table(name="T_COR_CONTROL_PERSON_ALARM" , uniqueConstraints = @UniqueConstraint(columnNames={"ID_CARD", "CREATE_TIME"})
)

public class ControlPersonAlarm extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

     /** 身份证 */
     private String idCard;
     /** 预警级别 */
     private Integer warningLevel;
     /** 动态信息编码 */
     private String dynamicInfoCode;
     /** 动态信息主键 */
     private Integer dynamicInfoPrikey;
     /** 动态信息名称 */
     private String dynamicInfoName;
     /** 下发状态 */
     private Integer distributeStatus;
     /** 签收人名称 */
     private String signName;	
     /** 签收人ID */
     private Integer signPrikey;
     /** 签收状态 */
     private Integer signStatus;
     /**备注 */
     private String remark;
     /** 签收时间 */
     private String signTime;
     /** 反馈时间 */
     private String feedbackTime;
     /** 出发地 */
     private String origin;
     /** 目的地 */
     private String destination;
     /** 重点人主键 */
     private ControlPerson controlPerson;
     /** 预警地派出所 */
     private Unit alarmUnit;
     /** 管控责任单位 */
     private Unit controlUnit;
     /**稳控状态*/
     private String status;

    @Column(name="ID_CARD", nullable=false)
    /** 身份证 */
    public String getIdCard() {
        return this.idCard;
    }
    /** 身份证 */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
    
    @Column(name="WARNING_LEVEL", nullable=false)
    /** 预警级别 */
    public Integer getWarningLevel() {
        return this.warningLevel;
    }
    /** 预警级别 */
    public void setWarningLevel(Integer warningLevel) {
        this.warningLevel = warningLevel;
    }
   
    @Column(name="DYNAMIC_INFO_CODE")
    /** 动态信息编码 */
    public String getDynamicInfoCode() {
        return this.dynamicInfoCode;
    }
    /** 动态信息编码 */
    public void setDynamicInfoCode(String dynamicInfoCode) {
        this.dynamicInfoCode = dynamicInfoCode;
    }
    
    @Column(name="DYNAMIC_INFO_PRIKEY", nullable=false, precision=22, scale=0)
    /** 动态信息主键 */
    public Integer getDynamicInfoPrikey() {
        return this.dynamicInfoPrikey;
    }
    /** 动态信息主键 */
    public void setDynamicInfoPrikey(Integer dynamicInfoPrikey) {
        this.dynamicInfoPrikey = dynamicInfoPrikey;
    }
    
    @Column(name="DYNAMIC_INFO_NAME")
    /** 动态信息名称 */
    public String getDynamicInfoName() {
        return this.dynamicInfoName;
    }
    /** 动态信息名称 */
    public void setDynamicInfoName(String dynamicInfoName) {
        this.dynamicInfoName = dynamicInfoName;
    }
    
    @Column(name="DISTRIBUTE_STATUS", precision=11, scale=0)
    /** 下发状态 */
    public Integer getDistributeStatus() {
        return this.distributeStatus;
    }
    /** 下发状态 */
    public void setDistributeStatus(Integer distributeStatus) {
        this.distributeStatus = distributeStatus;
    }
    
    @Column(name="SIGN_NAME")
    /** 签收人名称 */
    public String getSignName() {
        return this.signName;
    }
    /** 签收人名称 */
    public void setSignName(String signName) {
        this.signName = signName;
    }
    
    @Column(name="SIGN_PRIKEY", precision=22, scale=0)
    /** 签收人ID */
    public Integer getSignPrikey() {
        return this.signPrikey;
    }
    /** 签收人ID */
    public void setSignPrikey(Integer signPrikey) {
        this.signPrikey = signPrikey;
    }
    
    @Column(name="SIGN_STATUS", precision=22, scale=0)
    /** 签收状态 */
    public Integer getSignStatus() {
        return this.signStatus;
    }
    /** 签收状态 */
    public void setSignStatus(Integer signStatus) {
        this.signStatus = signStatus;
    }
    
    @Column(name="REMARK")
    /**备注 */
    public String getRemark() {
        return this.remark;
    }
    /**备注 */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    @Column(name="SIGN_TIME")
    /** 签收时间 */
    public String getSignTime() {
        return this.signTime;
    }
    /** 签收时间 */
    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }
    
    @Column(name="FEEDBACK_TIME")
    /** 反馈时间 */
    public String getFeedbackTime() {
        return this.feedbackTime;
    }
    /** 反馈时间 */
    public void setFeedbackTime(String feedbackTime) {
        this.feedbackTime = feedbackTime;
    }
    
    @Column(name="ORIGIN")
    /** 出发地 */
    public String getOrigin() {
        return this.origin;
    }
    /** 出发地 */
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    
    @Column(name="DESTINATION")
    /** 目的地 */
    public String getDestination() {
        return this.destination;
    }
    /** 目的地 */
    public void setDestination(String destination) {
        this.destination = destination;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CONTROL_PERSON_PRIKEY", nullable=false)
    /** 重点人主键 */
	public ControlPerson getControlPerson() {
		return controlPerson;
	}
	/** 重点人主键 */
	public void setControlPerson(ControlPerson controlPerson) {
		this.controlPerson = controlPerson;
	}
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="LOCAL_UNIT_ID", nullable=false)
	/** 预警地派出所 */
	public Unit getAlarmUnit() {
		return alarmUnit;
	}
	/** 预警地派出所 */
	public void setAlarmUnit(Unit alarmUnit) {
		this.alarmUnit = alarmUnit;
	}
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="UNIT_ID", nullable=false)
	/** 管控责任单位 */
	public Unit getControlUnit() {
		return controlUnit;
	}
	/** 管控责任单位 */
	public void setControlUnit(Unit controlUnit) {
		this.controlUnit = controlUnit;
	}
	@Transient
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
   
	
}