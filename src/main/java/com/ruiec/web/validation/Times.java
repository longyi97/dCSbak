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
 * 倍率验证器注解
 * Version: 1.0<br>
 * Date: 2015年3月21日
 */
@Target({ElementType.FIELD, ElementType.METHOD})  
@Retention(RetentionPolicy.RUNTIME)  
@Constraint(validatedBy = TimesValidator.class)  
@Documented 
public @interface Times {
	String message() default "";  
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    /**
     * 倍数基数
     * Date: 2015年3月23日
     */
    int basic();
}
