package com.ruiec.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ruiec.framework.server.support.entity.BaseEntity;

/**
 * 数据导入字段信息
 * @date 2017年12月18日 下午4:21:05
 */
@Entity
@Table(name = "T_SYS_DB_FIELD")
public class DbField extends BaseEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3769896798703059594L;
	/** 字段名 */
	private String fieldName;
	/** 对应我方字段名 */
	private String associationFieldName;
	/** 字段类型 (0：number；1：varchar；2：date)*/
	private int fieldType;
	/** 数据导入表信息 */
	private DbTable dbTable;

	/** 数据导入表信息 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TABLE_ID", nullable = false)
	public DbTable getDbTable() {
		return this.dbTable;
	}

	/** 数据导入表信息 */
	public void setDbTable(DbTable dbTable) {
		this.dbTable = dbTable;
	}

	/** 字段名 */
	@Column(name = "FIELD_NAME", nullable = false)
	public String getFieldName() {
		return this.fieldName;
	}

	/** 字段名 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/** 对应我方字段名 */
	@Column(name = "ASSOCIATION_FIELD_NAME", nullable = false)
	public String getAssociationFieldName() {
		return this.associationFieldName;
	}
	
	/** 对应我方字段名 */
	public void setAssociationFieldName(String associationFieldName) {
		this.associationFieldName = associationFieldName;
	}

	@Column(name = "FIELD_TYPE", nullable = false)
	/** 字段类型 */
	public int getFieldType() {
		return fieldType;
	}
	
	/** 字段类型 */
	public void setFieldType(int fieldType) {
		this.fieldType = fieldType;
	}

}