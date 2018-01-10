package com.ruiec.web.entity;

/**
 * 预警级别实体
 * @date 2017年12月22日 上午9:24:36
 */
public class ControlPersonAlarmDTO {
	//统计预警数量
	private long count;
	//预警类型
	private String warningLevel;
	
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public String getWarningLevel() {
		return warningLevel;
	}
	public void setWarningLevel(String warningLevel) {
		this.warningLevel = warningLevel;
	}
	public ControlPersonAlarmDTO(long count, String warningLevel) {
		super();
		this.count = count;
		this.warningLevel = warningLevel;
	}
}
