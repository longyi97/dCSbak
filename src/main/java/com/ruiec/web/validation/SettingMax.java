package com.ruiec.web.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
/**
 * 
 * 
 * 系统参数最小值比较器
 * Version: 1.0<br>
 * Date: 2015年3月25日
 */
@Target({ElementType.FIELD, ElementType.METHOD})  
@Retention(RetentionPolicy.RUNTIME)  
@Constraint(validatedBy = SettingMaxValidator.class)  
@Documented 
public @interface SettingMax {
	String message() default "";  
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
    /**
     * 字段名称
     * Date: 2015年3月25日
     */
    String fieldName();
}
