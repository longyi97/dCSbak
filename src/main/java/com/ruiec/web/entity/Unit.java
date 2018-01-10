package com.ruiec.web.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ruiec.framework.server.support.entity.BaseEntity;

/**
 * 单位信息实体
 * @date 2017年11月28日 下午5:04:27
 */
@Entity
@Table(name="T_SYS_UNIT")
public class Unit extends BaseEntity implements Serializable{

	private static final long serialVersionUID = -3359149305563100301L;
	/** 单位名称*/
	private String unitName;
	/** 单位级别*/
	private String unitRank;
	/** 父ID*/
	private Integer parentId;
	/** 是否警种部门*/
    private Integer isPoliceSection;
    /** 警钟所属父ID*/
    private Integer policeTypesParentId;
    /** 省级编码*/
    private String provinceCode;
    /** 市级编码*/
    private String cityCode;
    /** 区级编码*/
    private String areaCode;
    /** 镇级编码*/
    private String townCode;
    /** 其他编码1*/
    private String other1Code;
    /** 其他编码2*/
    private String other2Code;
   
    @Column(name="UNIT_NAME", nullable=false, length=100)
    /** 单位名称*/
    public String getUnitName() {
        return this.unitName;
    }
    /** 单位名称*/
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
    @Column(name="UNIT_RANK", nullable=false, length=100)
    /** 单位级别*/
    public String getUnitRank() {
        return this.unitRank;
    }
    /** 单位级别*/
    public void setUnitRank(String unitRank) {
        this.unitRank = unitRank;
    }
    
    @Column(name="PARENT_ID", precision=22, scale=0)
    /** 父ID*/
    public Integer getParentId() {
        return this.parentId;
    }
    /** 父ID*/
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    
    @Column(name="IS_POLICE_SECTION", nullable=false, precision=22, scale=0)
    /** 是否警种部门*/
    public Integer getIsPoliceSection() {
        return this.isPoliceSection;
    }
    /** 是否警种部门*/
    public void setIsPoliceSection(Integer isPoliceSection) {
        this.isPoliceSection = isPoliceSection;
    }
    
    @Column(name="POLICE_TYPES_PARENT_ID", precision=22, scale=0)
    /** 警种所属父ID*/
    public Integer getPoliceTypesParentId() {
        return this.policeTypesParentId;
    }
    /** 警种所属父ID*/
    public void setPoliceTypesParentId(Integer policeTypesParentId) {
        this.policeTypesParentId = policeTypesParentId;
    }
    
    @Column(name="PROVINCE_CODE", precision=22, scale=0)
    /** 省级编码*/
    public String getProvinceCode() {
        return this.provinceCode;
    }
    /** 省级编码*/
    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }
    
    @Column(name="CITY_CODE", precision=22, scale=0)
    /** 市级编码*/
    public String getCityCode() {
        return this.cityCode;
    }
    /** 市级编码*/
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    
    @Column(name="AREA_CODE", precision=22, scale=0)
    /** 区级编码*/
    public String getAreaCode() {
        return this.areaCode;
    }
    /** 区级编码*/
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
    
    @Column(name="TOWN_CODE", precision=22, scale=0)
    /** 镇级编码*/
    public String getTownCode() {
        return this.townCode;
    }
    /** 镇级编码*/
    public void setTownCode(String townCode) {
        this.townCode = townCode;
    }
    
    @Column(name="OTHER1_CODE", precision=22, scale=0)
    /** 其他编码1*/
    public String getOther1Code() {
        return this.other1Code;
    }
    /** 其他编码1*/
    public void setOther1Code(String other1Code) {
        this.other1Code = other1Code;
    }
    
    @Column(name="OTHER2_CODE", precision=22, scale=0)
    /** 其他编码2*/
    public String getOther2Code() {
        return this.other2Code;
    }
    /** 其他编码2*/
    public void setOther2Code(String other2Code) {
        this.other2Code = other2Code;
    }

	@Override
	public String toString() {
		return "Unit [unitName=" + unitName + ", unitRank=" + unitRank + ", parentId=" + parentId + ", isPoliceSection=" + isPoliceSection
				+ ", policeTypesParentId=" + policeTypesParentId + ", provinceCode=" + provinceCode + ", cityCode=" + cityCode + ", areaCode=" + areaCode
				+ ", townCode=" + townCode + ", other1Code=" + other1Code + ", other2Code=" + other2Code + "]";
	}

}