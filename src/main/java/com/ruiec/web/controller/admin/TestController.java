package com.ruiec.web.controller.admin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruiec.web.entity.ControlPerson;
import com.ruiec.web.service.ControlPersonService;
import com.ruiec.web.service.impl.DataCaptureServiceImpl;

/**
 * 测试控制器
 * 
 * @date 2017年12月23日 上午11:17:41
 */
@Controller
@RequestMapping("/")
public class TestController {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);
	
	@Resource
	private DataCaptureServiceImpl dataCaptureService;
	@Resource
	private ControlPersonService controlPersonService;
	
	// 文件路径
	private String fileSysRootPath;
	
	// 文件分隔符
	private String separator = File.separator;

	/**
	 * 省厅进港数据
	 * 
	 * @throws IOException 
	 * @date 2017年12月23日 上午11:19:41
	 */
	@RequestMapping("/FTDataCapture")
	public void FTDataCaptureService(String url,HttpServletRequest request, HttpServletResponse response) throws IOException {
		LOGGER.info("FTDataCaptureService=================================");
		fileSysRootPath = request.getServletContext().getRealPath("/download");
		if (null !=  url && !url.isEmpty()) {
			dataCaptureService.FTDataCapture(url, fileSysRootPath + separator + "STJG_HBXX.csv");
		}
		response.sendRedirect("FTDataCapture.html");
	}
	
	/**
	 * 人脸识别数据
	 * 
	 * @throws IOException 
	 * @date 2017年12月23日 上午11:19:41
	 */
	@RequestMapping("/FRDataCapture")
	public void FRDataCaptureService(String url, HttpServletRequest request, HttpServletResponse response) throws IOException {
		LOGGER.info("FRDataCaptureService=================================");
		fileSysRootPath = request.getServletContext().getRealPath("/download");
		if (null !=  url && !url.isEmpty()) {
			dataCaptureService.FRDataCapture(url, fileSysRootPath + separator + "HK_RLSB.csv");
		}
		response.sendRedirect("FRDataCapture.html");
	}
	
	@Test
	@RequestMapping("/createData")
	public void createData() throws IOException {
		System.out.println("createData========================================================");
		File file = new File("F:\\dowloadTest\\STJG_HBXX.csv");
		File file2 = new File("F:\\dowloadTest\\STJG_HBXX - 副本.csv");
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
//		BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(file2, true));
//		FileWriter fw = new FileWriter(file2, true);
//		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file2, true)));
		br.readLine();
		String[] values = br.readLine().split(",");
		br.close();
		long time1 = new Date().getTime();
//		List<ControlPerson> controlPersons = controlPersonService.getAll();
		long time2 = new Date().getTime();
		System.out.println("time2 - time1 : " + ((time2 - time1)));
		for (int i = 0; i < 1; i++) {
			values[2] = "123456789012345678";
//			values[2] = controlPersons.get(i).getIdCard();
//			String line = StringUtils.join(values, ',');
			String line = "潘云,女,123456789012345678,湖北省郧县大柳乡左溪寺村８组,420321000000,,6585,420321199108054922,武汉市,北京市,2017-11-04 19:30:00,2017-11-04 20:00:50,飞机,是,天河国际机场,首都国际机场";
			System.out.println(line);
			FileUtils.write(file2, line, true);
//			os.write(line.getBytes());
//			fw.write(line);
//			fw.flush();
//			bw.newLine();
//			bw.write(line);
		}
		long time3 = new Date().getTime();
		System.out.println("time3 - time2 : " + ((time3 - time2)));
//		os.close();
//		fw.close();
//		bw.close();
	}
	
	@Test
	public void clearFiles() {
		System.out.println("clearFiles========================================================");
		try {
			File file = new File("F:\\dowloadTest - 副本");
			FileUtils.deleteDirectory(file);
//			FileUtils.forceDelete(file);
//			FileUtils.deleteQuietly(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
