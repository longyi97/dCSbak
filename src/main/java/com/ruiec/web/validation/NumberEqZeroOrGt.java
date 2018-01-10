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
 * 两字段比较数据大小验证注解(较大数为0，或较大数大于较小数)
 * @author 肖学良<br>
 * Version: 1.0<br>
 * Date: 2015年3月23日
 */
@Target({ElementType.ANNOTATION_TYPE })  
@Retention(RetentionPolicy.RUNTIME)  
@Constraint(validatedBy = NumberEqZeroOrGtValidator.class)  
@Documented
public @interface NumberEqZeroOrGt {
	String message() default "";  
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    /**
     * 较大数
     * @author 肖学良<br>
     * Date: 2015年3月23日
     */
    String theOne();
    /**
     * 较小数
     * @author 肖学良<br>
     * Date: 2015年3月23日
     */
    String theOther();
    
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
    	NumberEqZeroOrGt[] value();
    }
}
