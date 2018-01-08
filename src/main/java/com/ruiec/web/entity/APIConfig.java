/*
 * 版权所有：深圳源中瑞科技有限公司<br>
 * 网 址：www.ruiec.com<br>
 * 电 话：0755-33581131<br><br>
 */
package com.ruiec.web.entity;

import com.ruiec.framework.server.support.entity.BaseEntity;

/**
 * 轨迹数据API接口配置
 * 
 * @author bingo<br>
 * @date 2017年12月26日 下午3:24:02
 */
public class APIConfig extends BaseEntity {

	private static final long serialVersionUID = 1187968379222005393L;
	/** API接口地址 */
	private String apiUrl;
	/** 口令 */
	private String token;
	/** 开始标记 */
	private String start;
	/** 偏移量 */
	private String offset;
	/** 备注 */
	private String remark;
	/** 表ID */
	private String tableId;
	/** 条件筛选 */
	private String rowFilter;

	/** API接口地址 */
	public String getApiUrl() {
		return apiUrl;
	}

	/** API接口地址 */
	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	/** 口令 */
	public String getToken() {
		return token;
	}

	/** 口令 */
	public void setToken(String token) {
		this.token = token;
	}

	/** 开始标记 */
	public String getStart() {
		return start;
	}

	/** 开始标记 */
	public void setStart(String start) {
		this.start = start;
	}

	/** 偏移量 */
	public String getOffset() {
		return offset;
	}

	/** 偏移量 */
	public void setOffset(String offset) {
		this.offset = offset;
	}

	/** 备注 */
	public String getRemark() {
		return remark;
	}

	/** 备注 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/** 表ID */
	public String getTableId() {
		return tableId;
	}

	/** 表ID */
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	/** 条件筛选 */
	public String getRowFilter() {
		return rowFilter;
	}

	/** 条件筛选 */
	public void setRowFilter(String rowFilter) {
		this.rowFilter = rowFilter;
	}

}
