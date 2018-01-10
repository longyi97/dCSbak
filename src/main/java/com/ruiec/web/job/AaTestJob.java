package com.ruiec.web.job;

import javax.annotation.Resource;

import org.springframework.core.task.TaskExecutor;

import com.ruiec.web.service.AaTestService;

/**
 * 测试任务
 * 
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
	 * @date 2017年12月20日 下午6:04:37
	 */
	public void startMethod() {
		test();
	}
	
	/**
	 * 测试
	 * 
	 * @date 2017年12月20日 下午6:05:07
	 */
	public void test() {
		aaTestService.mainMethod();
	}
}
