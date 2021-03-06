
package com.ruiec.web.common;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品价格行情
 * Version: 1.0<br>
 * Date: 2016年01月04日
 */

public class GoodsPriceBuffer{

	/** 商品代码 */
	private String goodsCode;
	/** 玩法类型*/
	private String optionType;
	/** 商品价格 */
	private BigDecimal price;
	/** 行情时间 */
	private Date time;
	/** 时间戳 */
	private Long timestamp;
	/** 行情来源标识(该行情数据来自哪个平台,其值为GoodsPriceAPI的主键) */
	private String priceSource;
	
	public GoodsPriceBuffer() {
		super();
	}

	public GoodsPriceBuffer(BigDecimal price, Long timestamp) {
		super();
		this.price = price;
		this.timestamp = timestamp;
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
	 * 玩法类型
	 * Date: 2017年3月28日
	 */
	public String getOptionType() {
		return optionType;
	}

	/**
	 * 玩法类型
	 * Date: 2017年3月28日
	 */
	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}

	/**
	 * 商品价格
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * 商品价格
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * 行情时间
	 * Date: 2016年3月22日
	 */
	public Date getTime() {
		return time;
	}

	/**
	 * 行情时间
	 * Date: 2016年3月22日
	 */
	public void setTime(Date time) {
		this.time = time;
	}

	/**
	 * 时间戳
	 * Date: 2016年3月23日
	 */
	public Long getTimestamp() {
		return timestamp;
	}

	/**
	 * 时间戳
	 * Date: 2016年3月23日
	 */
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * 行情来源标识(该行情数据来自哪个平台,其值为GoodsPriceAPI的主键)
	 */
	public String getPriceSource() {
		return priceSource;
	}

	/**
	 * 行情来源标识(该行情数据来自哪个平台,其值为GoodsPriceAPI的主键)
	 */
	public void setPriceSource(String priceSource) {
		this.priceSource = priceSource;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GoodsPriceBuffer [goodsCode=");
		builder.append(goodsCode);
		builder.append(", optionType=");
		builder.append(optionType);
		builder.append(", price=");
		builder.append(price);
		builder.append(", time=");
		builder.append(time);
		builder.append(", timestamp=");
		builder.append(timestamp);
		builder.append(", priceSource=");
		builder.append(priceSource);
		builder.append("]");
		return builder.toString();
	}
}