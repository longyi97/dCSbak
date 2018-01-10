package com.ruiec.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ruiec.framework.server.support.entity.BaseEntity;

/**
 * 警员信息实体
 * 
 * @date 2017年11月28日 下午5:03:52
 */
@Entity
@Table(name = "T_SYS_USER")
public class User extends BaseEntity {

	private static final long serialVersionUID = -3576741164920192840L;
	
	/** 警员姓名 */
	private String policeName;
	/** 性别 */
	private String sex;
	/** 级别 */
	private String grade;
	/** 电话 */
	private String phone;
	/** 身份证号 */
	private String idCard;
	/** 警号 */
	private String policeNumber;
	/** 保留关键字1 */
	private String reservedKeywordsOne;
	/** 保留关键字2 */
	private String reservedKeywordsTwo;
	/** 密码 */
	private String password;
	/** 阅读过的公告*/
	private String readNotice;
	/** 关联单位(多对一) */
	private Unit unit;
	/** 重点人表集合(关联重点人表) */

	@Column(name = "POLICE_NAME", nullable = false)
	/** 警员姓名 */
	public String getPoliceName() {
		return this.policeName;
	}

	/** 警员姓名 */
	public void setPoliceName(String policeName) {
		this.policeName = policeName;
	}

	@Column(name = "SEX", nullable = false, length = 20)
	/** 性别 */
	public String getSex() {
		return this.sex;
	}

	/** 性别 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "GRADE", length = 100)
	/** 级别 */
	public String getGrade() {
		return this.grade;
	}

	/** 级别 */
	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Column(name = "PHONE")
	/** 电话 */
	public String getPhone() {
		return this.phone;
	}

	/** 电话 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "ID_CARD", nullable = false)
	/** 身份证号 */
	public String getIdCard() {
		return this.idCard;
	}

	/** 身份证号 */
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	@Column(name = "POLICE_NUMBER")
	/** 警号 */
	public String getPoliceNumber() {
		return this.policeNumber;
	}

	/** 警号 */
	public void setPoliceNumber(String policeNumber) {
		this.policeNumber = policeNumber;
	}

	@Column(name = "RESERVED_KEYWORDS_ONE")
	/** 保留关键字1 */
	public String getReservedKeywordsOne() {
		return this.reservedKeywordsOne;
	}

	/** 保留关键字1 */
	public void setReservedKeywordsOne(String reservedKeywordsOne) {
		this.reservedKeywordsOne = reservedKeywordsOne;
	}

	@Column(name = "RESERVED_KEYWORDS_TWO")
	/** 保留关键字2 */
	public String getReservedKeywordsTwo() {
		return this.reservedKeywordsTwo;
	}

	/** 保留关键字2 */
	public void setReservedKeywordsTwo(String reservedKeywordsTwo) {
		this.reservedKeywordsTwo = reservedKeywordsTwo;
	}

	@Column(name = "PASSWORD", nullable = false)
	/** 密码 */
	public String getPassword() {
		return this.password;
	}

	/** 密码 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "READ_NOTICE")
	public String getReadNotice() {
		return readNotice;
	}

	public void setReadNotice(String readNotice) {
		this.readNotice = readNotice;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UNIT_ID")
	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	// @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy =
	// "user")
	// /** 重点人表集合(关联重点人表) */
	// public Set<ControlPerson> getControlPersons() {
	// return this.controlPersons;
	// }
	//
	// /** 重点人表集合(关联重点人表) */
	// public void setControlPersons(Set<ControlPerson> controlPersons) {
	// this.controlPersons = controlPersons;
	// }

}