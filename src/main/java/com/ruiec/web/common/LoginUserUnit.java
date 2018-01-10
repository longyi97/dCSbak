package com.ruiec.web.common;

import java.io.Serializable;
import java.util.List;

import com.ruiec.web.enumeration.UnitRank;

/**
 * 登录用户管理单位实体
 * @date 2017年12月18日 下午3:29:40
 */
public class LoginUserUnit implements Serializable{
	/**  */
	private static final long serialVersionUID = -681375021419391824L;
	/** 单位级别 */
	private UnitRank unitRank;
	/** 直辖单位id */
	private List<Integer> unitIds;
	/** 直辖单位下级所有id */
	private List<Integer> unitSonIds;
	
	public UnitRank getUnitRank() {
		return unitRank;
	}
	public void setUnitRank(UnitRank unitRank) {
		this.unitRank = unitRank;
	}
	public List<Integer> getUnitIds() {
		return unitIds;
	}
	public void setUnitIds(List<Integer> unitIds) {
		this.unitIds = unitIds;
	}
	public List<Integer> getUnitSonIds() {
		return unitSonIds;
	}
	public void setUnitSonIds(List<Integer> unitSonIds) {
		this.unitSonIds = unitSonIds;
	}
}
