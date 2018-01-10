package com.ruiec.web.service;

/**
 * 轨迹数据抓取服务接口
 * 
 * @date 2017年12月23日 上午10:27:13
 */
public interface DataCaptureService {

	/**
	 * 数据抓取主方法
	 * 
	 * @date 2017年12月19日 上午9:49:06
	 */
	public void dataCapture(String url, String targetFile);
	
	/**
	 * 人脸识别数据抓取
	 * 
	 * @date 2017年12月29日 上午10:36:07
	 */
	public void FRDataCapture(String url, String targetFile);
}
