package com.ruiec.web.entity;

import java.sql.Clob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ruiec.framework.server.support.entity.BaseEntity;


/**
 * 操作日志实体
 * @author 陈靖原<br>
 * @date 2017年11月28日 下午5:05:34
 */
@Entity
@Table(name="T_SYS_OPERATION_LOG")
public class OperationLog extends BaseEntity 
 {



	private static final long serialVersionUID = 1L;
	
	/** 操作用户*/
    private String userid;
    /** 操作单位名称*/
    private String unitName;
    /** 请求类型*/
    private Integer type;
    /** 请求URL*/
    private String url;
    /** IP地址*/
    private String ip;
    /** 备注*/
    private String remark;
    /** 请求数据*/
    private Clob content;

    @Column(name="USERID", nullable=false)
    /** 操作用户*/
    public String getUserid() {
        return this.userid;
    }
    /** 操作用户*/
    public void setUserid(String userid) {
        this.userid = userid;
    }
    
    @Column(name="UNIT_NAME", nullable=false)
    /** 操作单位名称*/
    public String getUnitName() {
        return this.unitName;
    }
    /** 操作单位名称*/
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
    
    @Column(name="TYPE", nullable=false, precision=22, scale=0)
    /** 请求类型*/
    public Integer getType() {
        return this.type;
    }
    /** 请求类型*/
    public void setType(Integer type) {
        this.type = type;
    }
    
    @Column(name="URL", nullable=false)
    /** 请求URL*/
    public String getUrl() {
        return this.url;
    }
    /** 请求URL*/
    public void setUrl(String url) {
        this.url = url;
    }
    
    @Column(name="IP")
    /** IP地址*/
    public String getIp() {
        return this.ip;
    }
    /** IP地址*/
    public void setIp(String ip) {
        this.ip = ip;
    }
    
    @Column(name="REMARK")
    /** 备注*/
    public String getRemark() {
        return this.remark;
    }
    /** 备注*/
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    @Column(name="CONTENT", nullable=false)
    /** 请求数据*/
    public Clob getContent() {
        return this.content;
    }
    /** 请求数据*/
    public void setContent(Clob content) {
        this.content = content;
    }
   








}