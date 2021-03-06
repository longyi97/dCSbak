package com.ruiec.web.validation;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
/**
 * 
 * 
 * 两个字段比比较大小验证器(较大数为0，或较大数大于较小数)
 * Version: 1.0<br>
 * Date: 2015年3月23日
 */
public class NumberEqZeroOrGtValidator implements ConstraintValidator<NumberEqZeroOrGt, Object> {

	private String theOne;
	private String theOther;
	
	@Override
	public void initialize(NumberEqZeroOrGt numberEqZeroOrGtAnnotation) {
		this.theOne = numberEqZeroOrGtAnnotation.theOne();
		this.theOther = numberEqZeroOrGtAnnotation.theOther();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		String theOneValueStr = null;
		String theOtherValueStr = null;
		BigDecimal theOneValue = null;
		BigDecimal theOtherValue = null;
		try {
			theOneValueStr = BeanUtils.getProperty(value, theOne);
			theOtherValueStr = BeanUtils.getProperty(value, theOther);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		if(StringUtils.isBlank(theOneValueStr) || StringUtils.isBlank(theOtherValueStr)){
			String defaultMessage = context.getDefaultConstraintMessageTemplate();
			if("".equals(defaultMessage)){
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate(" field value must not be null").addConstraintViolation();
			}
			return false;
		}
		theOneValue = new BigDecimal(theOneValueStr);
		theOtherValue = new BigDecimal(theOtherValueStr);
		
		if(theOneValue.compareTo(new BigDecimal(0)) == 0 && theOtherValue.compareTo(new BigDecimal(0)) == 0){
			return true;
		}
		
		if(theOneValue.compareTo(new BigDecimal(0)) != 0 && theOneValue.compareTo(theOtherValue) <= 0){
			String defaultMessage = context.getDefaultConstraintMessageTemplate();
			if("".equals(defaultMessage)){
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate(" must eq zero or greater than " + theOther).addNode(theOne).addConstraintViolation();
			}
			return false;
		}
		return true;
	}

}
