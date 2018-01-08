package com.ruiec.web.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ruiec.framework.server.support.entity.BaseEntity;

/**
 * 数据导入表信息实体
 * 
 * @author qinzhitian<br>
 * @date 2017年12月18日 下午4:24:16
 */
@Entity
@Table(name = "T_SYS_DB_TABLE")
public class DbTable extends BaseEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6358817660842816041L;
	/** 表名 */
	private String tableName;
	/** 对应我方表名 */
	private String associationTableName;
	/** 是否启用 */
	private Integer isUse;
	/** 数据库 */
	private Db db;
	/** 字段 */
	private List<DbField> dbFields;

	/** 表名 */
	@Column(name = "TABLE_NAME", nullable = false)
	public String getTableName() {
		return this.tableName;
	}

	/** 表名 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/** 对应我方表名 */
	@Column(name = "ASSOCIATION_TABLE_NAME", nullable = false)
	public String getAssociationTableName() {
		return this.associationTableName;
	}

	/** 对应我方表名 */
	public void setAssociationTableName(String associationTableName) {
		this.associationTableName = associationTableName;
	}

	/** 是否启用 */
	@Column(name = "ISUSE", nullable = false, precision = 1, scale = 0)
	public Integer getIsUse() {
		return this.isUse;
	}

	/** 是否启用 */
	public void setIsUse(Integer isUse) {
		this.isUse = isUse;
	}

	/** 数据库 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DB_ID", nullable = false)
	public Db getDb() {
		return db;
	}

	/** 数据库 */
	public void setDb(Db db) {
		this.db = db;
	}

	/** 字段 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "dbTable")
	public List<DbField> getDbFields() {
		return dbFields;
	}

	/** 字段 */
	public void setDbFields(List<DbField> dbFields) {
		this.dbFields = dbFields;
	}

}