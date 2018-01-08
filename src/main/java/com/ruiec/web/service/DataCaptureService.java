/*
 * 版权所有：深圳源中瑞科技有限公司<br>
 * 网 址：www.ruiec.com<br>
 * 电 话：0755-33581131<br><br>
 */
package com.ruiec.web.service;

/**
 * 轨迹数据抓取服务接口
 * 
 * @author bingo<br>
 * @date 2017年12月23日 上午10:27:13
 */
public interface DataCaptureService {

	/**
	 * 数据抓取主方法
	 * 
	 * @author bingo<br>
	 * @date 2017年12月19日 上午9:49:06
	 */
	public void dataCapture(String url, String targetFile);
	
	/**
	 * 人脸识别数据抓取
	 * 
	 * @author bingo<br>
	 * @date 2017年12月29日 上午10:36:07
	 */
	public void FRDataCapture(String url, String targetFile);
}
