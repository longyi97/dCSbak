package com.ruiec.web.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.ruiec.web.entity.Unit;

/**
 * 重点人员查询条件
 * @author Senghor<br>
 * @date 2017年12月13日 下午7:39:39
 */
public class ControlPersonDTO implements Serializable{

	private static final long serialVersionUID = 2881126805933601170L;
	
	/** 单位id */
	private Integer unitId;
	/** 单位id2 */
	private Integer unitId2;
	/** 下发状态 */
	private Integer outType;
	/** 人员类别id */
	private Integer personTypeId;
	/** 警员id */
	private Integer userId;
	/** 人员类别的拼接字符串 */
	private String Categories;
	/** 查询开始时间 */
	private Date startDate;
	/** 查询结束时间 */
	private Date endDate;
	/** 单位集合 */
	private List<Integer> unitIds;
	
	public List<Integer> getUnitIds() {
		return unitIds;
	}
	public void setUnitIds(List<Integer> unitIds) {
		this.unitIds = unitIds;
	}
	public Integer getUnitId() {
		return unitId;
	}
	public Integer getUnitId2() {
		return unitId2;
	}
	public void setUnitId2(Integer unitId2) {
		this.unitId2 = unitId2;
	}
	public Integer getOutType() {
		return outType;
	}
	public void setOutType(Integer outType) {
		this.outType = outType;
	}
	public Integer getPersonTypeId() {
		return personTypeId;
	}
	public void setPersonTypeId(Integer personTypeId) {
		this.personTypeId = personTypeId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getCategories() {
		return Categories;
	}
	public void setCategories(String categories) {
		Categories = categories;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}
	
	
}
