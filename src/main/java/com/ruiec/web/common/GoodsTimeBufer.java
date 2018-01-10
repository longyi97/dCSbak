
package com.ruiec.web.common;

import java.util.Date;

import com.ruiec.framework.server.support.entity.SortEntity;

/**
 * 商品结算时间(缓存)
 * Version: 1.0<br>
 * Date: 2016年01月04日
 */
public class GoodsTimeBufer extends SortEntity {
	
	private static final long serialVersionUID = -1602086856149514348L;
	
	public static final String KEY = "goodsTimeBuferKey";
	/** 结算时间类型 */
	private String cycleType;
	/** 到期时间 */
	private String time;
	/** 失效时间 */
	private Date endTime;
	/** 商品代码 */
	private String goodsCode;
	//该到期时间是否被修改(如果修改了的话，因为可能在修改之前已经下单，所以先报留该到期时间，结算后在删除)
	private boolean isUpdate;

	/**
	 * 结算时间类型
	 * 
	 *         Date: 2016年3月22日
	 */
	public String getCycleType() {
		return cycleType;
	}

	/**
	 * 结算时间类型
	 * 
	 *         Date: 2016年3月22日
	 */
	public void setCycleType(String cycleType) {
		this.cycleType = cycleType;
	}

	/**
	 * 到期时间
	 */
	public String getTime() {
		return time;
	}

	/**
	 * 到期时间
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * 失效时间
	 * 
	 *         Date: 2016年3月22日
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * 失效时间
	 * 
	 *         Date: 2016年3月22日
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * 商品代码
	 */
	public String getGoodsCode() {
		return goodsCode;
	}

	/**
	 * 商品代码
	 */
	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	/**
	 * 该到期时间是否被修改(如果修改了的话，因为可能在修改之前已经下单，所以先报留该到期时间，结算后在删除)
	 */
	public boolean getIsUpdate() {
		return isUpdate;
	}

	/**
	 * 该到期时间是否被修改(如果修改了的话，因为可能在修改之前已经下单，所以先报留该到期时间，结算后在删除)
	 */
	public void setIsUpdate(boolean isUpdate) {
		this.isUpdate = isUpdate;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GoodsTimeBufer [cycleType=");
		builder.append(cycleType);
		builder.append(", time=");
		builder.append(time);
		builder.append(", endTime=");
		builder.append(endTime);
		builder.append(", goodsCode=");
		builder.append(goodsCode);
		builder.append(", isUpdate=");
		builder.append(isUpdate);
		builder.append("]");
		return builder.toString();
	}
}