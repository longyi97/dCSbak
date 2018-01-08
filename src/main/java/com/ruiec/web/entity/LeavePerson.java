package com.ruiec.web.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruiec.framework.server.support.entity.BaseEntity;


/**
 * 离堰人员实体
 * @author yk<br>
 * @date 2017年12月1日 上午11:00:41
 */

@Entity
@Table(name="T_COR_LEAVE_PERSON", uniqueConstraints = @UniqueConstraint(columnNames={"ID_CARD", "CREATE_TIME"}))
public class LeavePerson  extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
     /** 姓名 */
     private String name;
     /** 性别 */
     private String sex;
     /** 身份证号 */
     private String idCard;
     /** 轨迹类型编码 */
     private Integer dynamicInfoId;
     /** 出行时间 */
     private String departureTime;
     /** 目的地 */
     private String endPlace;
     /** 户籍地址*/
     private String nativePlace;
     /** 户籍地址派出所编码(原来用于存储预警地派出所编码) */
     private Integer nativePlacePoliceId;
     /** 户籍地址公安机关 */
     private String nativePlacePoliceStationCo;
     /**人员类别  */
     private String personType;
     /** 触发时间 */
     private String triggerTime;
     /** 唯一索引 */
     private String theIndex;
     /** 是否签收:0=未签收,1=已签收*/
     private String isSign;
     /** 签收人姓名  */
     private Integer signPersonId;
     /** 签收单位编码 */
     private Integer signUnitId;
     /** 出发地*/
     private String startPlace;
     /** 户籍地责任派出所编码 */
     private Integer nativePlaceResponsibilityId;
     /** 字典数据 */
     private Dictionary dictionary;
     /** 签收人 */
     private User user;
     /** 签收单位 */
     private Unit unit;
     /** 重点人关系 */
     private List<ControlPersonRelation> controlPersonRelations;


    @Column(name="NAME", nullable=false)
    /** 姓名 */
    public String getName() {
        return this.name;
    }
    /** 姓名 */
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="SEX", nullable=false)
    /** 性别 */
    public String getSex() {
        return this.sex;
    }
    /** 性别 */
    public void setSex(String sex) {
        this.sex = sex;
    }
    
    @Column(name="ID_CARD", nullable=false)
    /** 身份证号 */
    public String getIdCard() {
        return this.idCard;
    }
    /** 身份证号 */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
    
    @Column(name="DYNAMIC_INFO_ID")
    /** 轨迹类型编码 */
    public Integer getDynamicInfoId() {
        return this.dynamicInfoId;
    }
    /** 轨迹类型编码 */
    public void setDynamicInfoId(Integer dynamicInfoId) {
        this.dynamicInfoId = dynamicInfoId;
    }
    
    @Column(name="DEPARTURE_TIME")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", locale="zh", timezone="GMT+8")
    /** 出行时间 */
    public String getDepartureTime() {
        return this.departureTime;
    }
    /** 出行时间 */
    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }
    
    @Column(name="END_PLACE")
    /** 目的地 */
    public String getEndPlace() {
        return this.endPlace;
    }
    /** 目的地 */ 
    public void setEndPlace(String endPlace) {
        this.endPlace = endPlace;
    }
    
    @Column(name="NATIVE_PLACE")
    /** 户籍地址*/
    public String getNativePlace() {
        return this.nativePlace;
    }
    /** 户籍地址*/
    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }
    
    @Column(name="NATIVE_PLACE_POLICE_ID")
    /** 户籍地址派出所编码(原来用于存储预警地派出所编码) */
    public Integer getNativePlacePoliceId() {
        return this.nativePlacePoliceId;
    }
    /** 户籍地址派出所编码(原来用于存储预警地派出所编码) */
    public void setNativePlacePoliceId(Integer nativePlacePoliceId) {
        this.nativePlacePoliceId = nativePlacePoliceId;
    }
    
    @Column(name="NATIVE_PLACE_POLICE_STATION_CO")
    /** 户籍地址公安机关 */
    public String getNativePlacePoliceStationCo() {
        return this.nativePlacePoliceStationCo;
    }
    /** 户籍地址公安机关 */
    public void setNativePlacePoliceStationCo(String nativePlacePoliceStationCo) {
        this.nativePlacePoliceStationCo = nativePlacePoliceStationCo;
    }
    
    @Column(name="PERSON_TYPE")
    /**人员类别  */
    public String getPersonType() {
        return this.personType;
    }
    /**人员类别  */
    public void setPersonType(String personType) {
        this.personType = personType;
    }
    
    @Column(name="TRIGGER_TIME")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", locale="zh", timezone="GMT+8")
    /** 触发时间 */
    public String getTriggerTime() {
        return this.triggerTime;
    }
    /** 触发时间 */
    public void setTriggerTime(String triggerTime) {
        this.triggerTime = triggerTime;
    }
    
    @Column(name="THE_INDEX")
    /** 唯一索引 */
    public String getTheIndex() {
        return this.theIndex;
    }
    /** 唯一索引 */
    public void setTheIndex(String theIndex) {
        this.theIndex = theIndex;
    }
    
    @Column(name="IS_SIGN", nullable=false, length=20)
    /** 是否签收:0=未签收,1=已签收*/
    public String getIsSign() {
        return this.isSign;
    }
    /** 是否签收:0=未签收,1=已签收*/
    public void setIsSign(String isSign) {
        this.isSign = isSign;
    }
    
    @Column(name="SIGN_PERSON_ID")
    /** 签收人姓名  */
    public Integer getSignPersonId() {
        return this.signPersonId;
    }
    /** 签收人姓名  */
    public void setSignPersonId(Integer signPersonId) {
        this.signPersonId = signPersonId;
    }
    
    @Column(name="SIGN_UNIT_ID")
    /** 签收单位编码 */
    public Integer getSignUnitId() {
        return this.signUnitId;
    }
    /** 签收单位编码 */
    public void setSignUnitId(Integer signUnitId) {
        this.signUnitId = signUnitId;
    }
    
    @Column(name="START_PLACE")
    /** 出发地*/
    public String getStartPlace() {
        return this.startPlace;
    }
    /** 出发地*/
    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace;
    }
    
    @Column(name="NATIVE_PLACE_RESPONSIBILITY_ID")
    /** 户籍地责任派出所编码 */
    public Integer getNativePlaceResponsibilityId() {
        return this.nativePlaceResponsibilityId;
    }
    /** 户籍地责任派出所编码 */
    public void setNativePlaceResponsibilityId(Integer nativePlaceResponsibilityId) {
        this.nativePlaceResponsibilityId = nativePlaceResponsibilityId;
    }

    @ManyToOne
    @JoinColumn(name = "DYNAMIC_INFO_ID", unique = true, nullable = false, insertable = false, updatable = false)
    /** 字典数据 */
	public Dictionary getDictionary() {
		return dictionary;
	}

    /** 字典数据 */
	public void setDictionary(Dictionary dictionary) {
		this.dictionary = dictionary;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SIGN_PERSON_ID", unique = true, nullable = false, insertable = false, updatable = false)
    /** 签收人 */
	public User getUser() {
		return user;
	}
    /** 签收人 */
	public void setUser(User user) {
		this.user = user;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SIGN_UNIT_ID", unique = true, nullable = false, insertable = false, updatable = false)
    /** 签收单位 */
	public Unit getUnit() {
		return unit;
	}
    /** 签收单位 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	@Transient
    /** 重点人关系 */
	public List<ControlPersonRelation> getControlPersonRelations() {
		return controlPersonRelations;
	}
    /** 重点人关系 */
	public void setControlPersonRelations(List<ControlPersonRelation> controlPersonRelations) {
		this.controlPersonRelations = controlPersonRelations;
	}

	/** 重点人关系 */
	public void setControlPersonRelation(ControlPersonRelation controlPersonRelation) {
		if (this.controlPersonRelations == null) {
			this.controlPersonRelations = new ArrayList<ControlPersonRelation>();
		}
		this.controlPersonRelations.add(controlPersonRelation);
	}








}