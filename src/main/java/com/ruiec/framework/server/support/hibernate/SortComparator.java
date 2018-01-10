
package com.ruiec.framework.server.support.hibernate;

import java.util.Comparator;

import com.ruiec.framework.server.support.entity.BaseEntity;

/**
 * 实体比较器
 * @author 肖学良<br>
 * Version: 1.0<br>
 * Date: 2016年1月4日
 */
public class SortComparator implements Comparator<BaseEntity> {
	@Override
	public int compare(BaseEntity o1, BaseEntity o2) {
		if(o1.getCreateDate() != null && o2.getCreateDate() != null){
			return o1.getCreateDate().compareTo(o2.getCreateDate());
		}else if(o1.getId() != null && o2.getId() == null){
			return 1;
		}else if(o1.getId() == null && o2.getId() != null){
			return -1;
		}
		return 0;
	}


}
