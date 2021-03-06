
package com.ruiec.web.util;

/**
 * 错误消息工具类
 * Version: 1.0<br>
 * Date: 2016年06月25日
 */
public class ErrorMessageUtil {
	
	public static ThreadLocal<String> errorMessage = new ThreadLocal<String>();

	public static String get() {
		String message = errorMessage.get();
		errorMessage.remove();
		return message;
	}

	public static void set(String errorMessage) {
		ErrorMessageUtil.errorMessage.set(errorMessage);
	}
	
}
