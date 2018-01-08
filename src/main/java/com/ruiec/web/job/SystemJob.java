package com.ruiec.web.job;

import java.util.Date;

import org.apache.log4j.Logger;

/**
 * 
 * 版权所有：深圳源中瑞科技有限公司<br>
 * 网 址：www.ruiec.com<br>
 * 电 话：0755-33581131<br><br>
 * 
 * 系统任务
 * @author 肖学良<br>
 * Version: 1.0<br>
 * Date: 2015年12月22日
 */
public class SystemJob {

	private static final Logger logger = Logger.getLogger(SystemJob.class);

	public void test() {
		System.out.println(new Date());
		logger.info("test...");
	}
	
}
