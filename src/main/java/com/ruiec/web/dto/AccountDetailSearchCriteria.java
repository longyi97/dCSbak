/*版权所有：深圳源中瑞科技有限公司<br>
 * 网 址：www.ruiec.com<br>
 * 电 话：0755-33581131<br><br>
 */

package com.ruiec.web.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 账户明细搜索条件封装类
 * 
 * @author 熊华松<br>
 * Version: 1.0<br>
 * Date: 2016年03月30日
 */
public class AccountDetailSearchCriteria implements Serializable {

	private static final long serialVersionUID = -2484927395773939040L;

	/** 摘要 */
	private String summary;
	/** 交易流水号 */
	private String sn;
	/** 开始时间 */
	private Date startDate;
	/** 结束时间 */
	private Date endDate;
	/** 充值状态 */
	private String chargeStatus;
	/** 充值方式 */
	private String chargeType;
	/** 提现状态 */
	private String withdrawStatus;
	/** 转账类型 */
	private String transferType;
	/** 资金互转类型 */
	private String mutualTransferType;

	/**
	 * 摘要
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * 摘要
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * 交易流水号
	 */
	public String getSn() {
		return sn;
	}

	/**
	 * 交易流水号
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}

	/**
	 * 开始时间
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * 开始时间
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * 结束时间
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * 结束时间
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 充值状态
	 */
	public String getChargeStatus() {
		return chargeStatus;
	}

	/**
	 * 充值状态
	 */
	public void setChargeStatus(String chargeStatus) {
		this.chargeStatus = chargeStatus;
	}

	/**
	 * 充值方式
	 */
	public String getChargeType() {
		return chargeType;
	}

	/**
	 * 充值方式
	 */
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	/**
	 * 提现状态
	 */
	public String getWithdrawStatus() {
		return withdrawStatus;
	}

	/**
	 * 提现状态
	 */
	public void setWithdrawStatus(String withdrawStatus) {
		this.withdrawStatus = withdrawStatus;
	}
		
	/**
	 * 转账类型
	 */
	public String getTransferType() {
		return transferType;
	}

	/**
	 * 转账类型
	 */
	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}

	/**
	 * 资金互转类型
	 */
	public String getMutualTransferType() {
		return mutualTransferType;
	}

	/**
	 * 资金互转类型
	 */
	public void setMutualTransferType(String mutualTransferType) {
		this.mutualTransferType = mutualTransferType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccountDetailSearchCriteria [summary=");
		builder.append(summary);
		builder.append(", sn=");
		builder.append(sn);
		builder.append(", startDate=");
		builder.append(startDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append(", chargeStatus=");
		builder.append(chargeStatus);
		builder.append(", chargeType=");
		builder.append(chargeType);
		builder.append(", withdrawStatus=");
		builder.append(withdrawStatus);
		builder.append(", transferType=");
		builder.append(transferType);
		builder.append(", mutualTransferType=");
		builder.append(mutualTransferType);
		builder.append("]");
		return builder.toString();
	}
	
}
