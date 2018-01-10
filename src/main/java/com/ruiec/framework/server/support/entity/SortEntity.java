
package com.ruiec.framework.server.support.entity;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Min;

import org.apache.commons.lang3.builder.CompareToBuilder;

/**
 * 基础实体类<br>
 * 提供排序支持
 * Version: 1.0<br>
 * Date: 2015年12月17日
 */
@MappedSuperclass
public class SortEntity extends BaseEntity implements Comparable<SortEntity> {

	private static final long serialVersionUID = -2228834050613437554L;
	
	private Integer sort;//排序
	
	/**
	 * 排序
	 */
	@Min(0L)
	public Integer getSort() {
		return sort;
	}
	/**
	 * 排序
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Override
	public int compareTo(SortEntity o) {
		return new CompareToBuilder().append(sort, o.getSort()).append(getId(), o.getId()).toComparison();

	}

}
