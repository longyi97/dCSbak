package com.ruiec.web.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 
 * 
 * 控制器中忽略对token的验证
 * Version: 1.0<br>
 * Date: 2015年10月30日
 */
@Target({ElementType.METHOD, ElementType.TYPE })  
@Retention(RetentionPolicy.RUNTIME)  
@Documented 
public @interface TokenIgnore {
	
}
