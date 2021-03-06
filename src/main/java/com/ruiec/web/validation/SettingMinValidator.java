package com.ruiec.web.validation;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;

import com.ruiec.web.common.Setting;
import com.ruiec.web.util.SettingUtils;

/**
 * 
 * 
 * 系统参数最小值比较器
 * Version: 1.0<br>
 * Date: 2015年3月25日
 */
public class SettingMinValidator implements ConstraintValidator<SettingMin, Object> {

	private String fieldName;
	
	@Override
	public void initialize(SettingMin settingMinAnnotation) {
		fieldName = settingMinAnnotation.fieldName();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		
		if(value == null){
			return true;
		}
		
		String settingValueStr = null;
		Setting setting = SettingUtils.get();
		try {
			settingValueStr = BeanUtils.getProperty(setting, fieldName);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		
		BigDecimal settingValue = new BigDecimal(settingValueStr);
		BigDecimal param = new BigDecimal(value.toString());
		if(param.compareTo(settingValue) >= 0){
			return true;
		}
		
		String defaultMessage = context.getDefaultConstraintMessageTemplate();
		if("".equals(defaultMessage)){
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(" min value is " + settingValue.toString()).addConstraintViolation();
		}
		return false;
	}

}
