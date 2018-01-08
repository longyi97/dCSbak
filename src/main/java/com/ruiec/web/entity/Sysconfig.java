package com.ruiec.web.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ruiec.framework.server.support.entity.BaseEntity;

/**
 * 系统配置实体
 * @author 陈靖原<br>
 * @date 2017年11月28日 下午5:04:55
 */
@Entity
@Table(name="T_SYS_SYSCONFIG")

public class Sysconfig extends BaseEntity {

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    /** 描述*/
    private String sysDescription;
    /** 名字*/
    private String sysName;
    /** 值*/
    private String sysValue;

    @Column(name="SYS_DESCRIPTION", nullable=false)
    /** 描述*/
    public String getSysDescription() {
        return this.sysDescription;
    }
    /** 描述*/
    public void setSysDescription(String sysDescription) {
        this.sysDescription = sysDescription;
    }
    
    @Column(name="SYS_NAME", nullable=false)
    /** 名字*/
    public String getSysName() {
        return this.sysName;
    }
    /** 名字*/
    public void setSysName(String sysName) {
        this.sysName = sysName;
    }
    
    @Column(name="SYS_VALUE", nullable=false)
    /** 值*/
    public String getSysValue() {
        return this.sysValue;
    }
    /** 值*/
    public void setSysValue(String sysValue) {
        this.sysValue = sysValue;
    }
   








}