package com.ruiec.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ruiec.framework.server.support.entity.BaseEntity;

/**
 * 导航实体
 * @date 2017年11月29日 上午9:44:10
 */
@Entity
@Table(name = "T_SYS_NAVIGATION")
public class Navigation extends BaseEntity {

	private static final long serialVersionUID = 3150830486212328445L;
	/** 名称*/
	private String name;
	/** 上级导航*/
	private String parentId;
	/** 层级*/
	private Integer levelSys;
	/** 排序*/
	private Integer sort;
	/** 是否显示*/
	private String isShow;
	/** 链接*/
	private String link;
	/** 图标*/
	private String image;
	/** 权限*/
	private String shiroperm;

	/** 名称*/
	@Column(name = "NAME", nullable = false)
	public String getName() {
		return this.name;
	}

	/** 名称*/
	public void setName(String name) {
		this.name = name;
	}

	/** 上级导航*/
	@Column(name = "PARENT_ID", nullable = false)
	public String getParentId() {
		return this.parentId;
	}

	/** 上级导航*/
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/** 层级*/
	@Column(name = "LEVEL_SYS", nullable = false, precision = 22, scale = 0)
	public Integer getLevelSys() {
		return this.levelSys;
	}

	/** 层级*/
	public void setLevelSys(Integer levelSys) {
		this.levelSys = levelSys;
	}

	/** 排序*/
	@Column(name = "SORT", nullable = false, precision = 22, scale = 0)
	public Integer getSort() {
		return this.sort;
	}

	/** 排序*/
	public void setSort(Integer sort) {
		this.sort = sort;
	}

	/** 是否显示*/
	@Column(name = "IS_SHOW", nullable = false, length = 1)
	public String getIsShow() {
		return this.isShow;
	}

	/** 是否显示*/
	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	/** 链接*/
	@Column(name = "LINK", nullable = false)
	public String getLink() {
		return this.link;
	}

	/** 链接*/
	public void setLink(String link) {
		this.link = link;
	}

	/** 图标*/
	@Column(name = "IMAGE", nullable = false)
	public String getImage() {
		return this.image;
	}

	/** 图标*/
	public void setImage(String image) {
		this.image = image;
	}

	/** 权限*/
	@Column(name = "SHIROPERM")
	public String getShiroperm() {
		return this.shiroperm;
	}

	/** 权限*/
	public void setShiroperm(String shiroperm) {
		this.shiroperm = shiroperm;
	}

}