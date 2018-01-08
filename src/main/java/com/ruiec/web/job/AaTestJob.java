/*
 * 版权所有：深圳源中瑞科技有限公司<br>
 * 网 址：www.ruiec.com<br>
 * 电 话：0755-33581131<br><br>
 */
package com.ruiec.web.job;

import javax.annotation.Resource;

import org.springframework.core.task.TaskExecutor;

import com.ruiec.web.service.AaTestService;

/**
 * 测试任务
 * 
 * @author bingo<br>
 * @date 2017年12月20日 下午5:48:08
 */
public class AaTestJob {
	
	@Resource
	private TaskExecutor taskExecutor;

	@Resource
	private AaTestService aaTestService;
	
	/**
	 * 开始方法
	 * 
	 * @author bingo<br>
	 * @date 2017年12月20日 下午6:04:37
	 */
	public void startMethod() {
		test();
	}
	
	/**
	 * 测试
	 * 
	 * @author bingo<br>
	 * @date 2017年12月20日 下午6:05:07
	 */
	public void test() {
		aaTestService.mainMethod();
	}
}
