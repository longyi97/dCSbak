package com.ruiec.web.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;
import com.ruiec.web.common.UnitCodeUtil;
import com.ruiec.web.entity.ControlPerson;
import com.ruiec.web.entity.DynamicInfo;
import com.ruiec.web.entity.LeavePerson;
import com.ruiec.web.entity.Unit;
import com.ruiec.web.service.ControlPersonService;
import com.ruiec.web.service.DynamicInfoService;
import com.ruiec.web.service.LeavePersonService;
import com.ruiec.web.util.HttpClientExcelUtil;

import net.sf.json.JSONObject;

/**
 * 轨迹数据抓取服务实现类
 * 
 * @date 2017年12月23日 上午10:28:11
 */
@Service
public class DataCaptureServiceImpl {

	protected static final Logger LOGGER = LoggerFactory.getLogger(DataCaptureServiceImpl.class);

	@Resource
	private DynamicInfoService dynamicInfoService;
	@Resource
	private ControlPersonService controlPersonService;
	@Resource
	private LeavePersonService leavePersonService;

	/**
	 * 省厅进港数据抓取
	 * 
	 * @date 2017年12月19日 上午9:49:06
	 */
	public void FTDataCapture(String url, String targetFile) {
		LOGGER.info("FTDataCapture()========================================================");
		try {
			// 1. 下载数据包
			downloadFile(url, targetFile);

			// 2. 解压数据包

			// 3. 解析数据
			LOGGER.info("解析省厅进港数据========================================================");
			List<DynamicInfo> list = new ArrayList<DynamicInfo>();
			List<LeavePerson> leavingList = new ArrayList<>();
			BufferedReader br = null;
			try {
				File cvsFile = new File(targetFile);
				br = new BufferedReader(new InputStreamReader(new FileInputStream(cvsFile), "gbk"));
				// 表头
				String line = br.readLine().replaceAll("\"|\'", "");
				if (null != line) {
					String[] ths = line.split(",");
					// title
					JSONObject title = new JSONObject();
					for (int i = 0; i < ths.length; i++) {
						if (i != 8 && i != 9 && i != 10 && i != 4) {
							title.put(ths[i], ths[i]);
						}
					}
					while ((line = br.readLine()) != null) {
						String[] values = line.replaceAll("\"|\'", "").split(",");
						// 出发时间/触发时间
						String triggerTime = values[10];
						// 出发地
						String origin = values[8];
						// 目的地
						String destination = values[9];
						// 匹配预警地单位
						String alarmUnitStr = values[4];
						// 身份证
						String idCard = values[2];
						// 姓名
						String name = values[0];
						// 性别
						String sex = values[1];
						// 户籍地址名称
						String nativePlace = values[3];
						
						// 轨迹数据封装（JSON）
						JSONObject jsonObject = new JSONObject();
						// value数组
						List<JSONObject> valueList = new ArrayList<JSONObject>();
						for (int i = 0; i < values.length; i++) {
							if (i != 8 && i != 9 && i != 10 && i != 4) {
								// values
								JSONObject val = new JSONObject();
								val.put("key", ths[i]);
								val.put("value", values[i]);
								val.put("type", "string");
								valueList.add(val);
							}
						}
						jsonObject.put("title", title);
						jsonObject.put("value", valueList);
						
						// 比对重点人员
						// 是重点人员生成轨迹记录、预警记录
						ControlPerson controlPerson = controlPersonService.getByIdCard(idCard);
						if (null != controlPerson) {
							// 轨迹数据实体
							DynamicInfo dynamicInfo = new DynamicInfo()
									.setInformation(jsonObject.toString())
									.setType(1)
									.setTriggerTime(triggerTime)
									.setOrigin(origin).setDestination(destination);
							dynamicInfo.setControlPerson(controlPerson);
							// 匹配预警地单位
							if (null != alarmUnitStr && alarmUnitStr.length() == 12) {
								Unit unit = UnitCodeUtil.matchUnit(alarmUnitStr);
								dynamicInfo.setAlarmUnit(unit != null ? unit : controlPerson.getUnit());
							}
							list.add(dynamicInfo);
						} else if (true) {
							// 铁公机处理
							// 离市人员实体
							LeavePerson leavePerson = new LeavePerson();
							leavePerson.setName(name);
							leavePerson.setSex(sex);
							leavePerson.setIdCard(idCard);
							leavePerson.setStartPlace(origin);
							leavePerson.setEndPlace(destination);
							leavePerson.setDepartureTime(triggerTime);
							leavePerson.setNativePlace(nativePlace);
							leavePerson.setDynamicInfoId(1);
							leavePerson.setIsSign("0");
							Unit unit = UnitCodeUtil.matchUnit(alarmUnitStr, "421000000000");
							if (null != unit) {
								leavePerson.setNativePlacePoliceId(unit.getId());
								leavePerson.setNativePlaceResponsibilityId(unit.getId());
							}
							leavingList.add(leavePerson);
						}
					}
				}
				LOGGER.info("轨迹数据解析成功");
			} catch (Exception e) {
				LOGGER.error("轨迹数据解析失败", e);
				throw new RuntimeException(e);
			} finally {
				if (null != br) {
					br.close();
				}
			}

			// 4. 数据插入数据库
			insertAll(list);
			insertLeaveAll(leavingList);
			LOGGER.info("数据抓取成功");
		} catch (IOException e) {
			LOGGER.error("数据抓取失败", e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 人脸识别数据抓取
	 * 
	 * @date 2017年12月29日 上午10:12:15
	 */
	public void FRDataCapture(String url, String targetFile) {
		LOGGER.info("FRDataCapture()========================================================");
		try {
			// 1. 下载数据包
			downloadFile(url, targetFile);
			
			// 2. 解压数据包
			
			// 3. 解析数据
			LOGGER.info("解析人脸识别数据========================================================");
			List<DynamicInfo> list = new ArrayList<DynamicInfo>();
			BufferedReader br = null;
			try {
				File cvsFile = new File(targetFile);
				br = new BufferedReader(new InputStreamReader(new FileInputStream(cvsFile), "gbk"));
				// 表头
				String line = br.readLine().replaceAll("\"|\'", "");
				if (null != line) {
					String[] ths = line.split(",");
					// title
					JSONObject title = new JSONObject();
					for (int i = 0; i < ths.length; i++) {
						if (i != 4 && i != 12) {
							title.put(ths[i], ths[i]);
						}
					}
					while ((line = br.readLine()) != null) {
						String[] values = line.replaceAll("\"|\'", "").split(",");
						// 出发时间/触发时间
						String triggerTime = values[4];
						// 匹配预警地单位
						String alarmUnitStr = values[12];
						// 身份证
						String idCard = values[10];
						
						// 轨迹数据封装（JSON）
						JSONObject jsonObject = new JSONObject();
						// value数组
						List<JSONObject> valueList = new ArrayList<JSONObject>();
						for (int i = 0; i < values.length; i++) {
							if (i != 4 && i != 12) {
								// values
								JSONObject val = new JSONObject();
								val.put("key", ths[i]);
								val.put("value", values[i]);
								if (i == 2 || i == 3 || i == 11) {
									val.put("type", "image");
								} else {
									val.put("type", "string");
								}
								valueList.add(val);
							}
						}
						jsonObject.put("title", title);
						jsonObject.put("value", valueList);
						
						// 轨迹数据实体
						DynamicInfo dynamicInfo = new DynamicInfo().setInformation(jsonObject.toString()).setType(2).setTriggerTime(triggerTime);
						dynamicInfo.setCreateDate(new Date());
						// 比对重点人员
						// 是重点人员生成轨迹记录、预警记录
						ControlPerson controlPerson = controlPersonService.getByIdCard(idCard);
						if (null != controlPerson) {
							dynamicInfo.setControlPerson(controlPerson);
							// 匹配预警地单位
							if (null != alarmUnitStr && alarmUnitStr.length() == 12) {
								Unit unit = UnitCodeUtil.matchUnit(alarmUnitStr);
								dynamicInfo.setAlarmUnit(unit != null ? unit : controlPerson.getUnit());
							}
							list.add(dynamicInfo);
						}
					}
				}
				LOGGER.info("轨迹数据解析成功");
			} catch (Exception e) {
				LOGGER.error("轨迹数据解析失败", e);
				throw new RuntimeException(e);
			} finally {
				if (null != br) {
					br.close();
				}
			}
			
			// 4. 数据插入数据库
			insertAll(list);
			LOGGER.info("数据抓取成功");
		} catch (IOException e) {
			LOGGER.error("数据抓取失败", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 下载文件
	 * 
	 * @date 2017年12月26日 下午2:06:05
	 */
	public void downloadFile(String url, String targetFile) {
		File file = new File(targetFile);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		HttpClientExcelUtil.httpDownloadFile(url, targetFile, null, null);
	}

	/**
	 * GZIP文件解压
	 * 
	 * @throws IOException
	 * @date 2017年12月19日 上午9:49:47
	 */
	public void uncompressArchiveGzip(String filePath) throws IOException {
		LOGGER.info("uncompressArchiveGzip()========================================================");
		File archiveGzipFile = new File(filePath);
		// 文件输入流
		GzipCompressorInputStream gcis = null;
		// 文件输出流
		BufferedOutputStream bos = null;
		try {
			gcis = new GzipCompressorInputStream(new FileInputStream(archiveGzipFile));
			// 父目录
			String parentPath = archiveGzipFile.getParent();
			String fileName = archiveGzipFile.getName();
			fileName = fileName.substring(0, fileName.lastIndexOf("."));
			// 输出文件
			String outputFileName = parentPath + File.separator + fileName;
			// 文件输出流
			bos = new BufferedOutputStream(new FileOutputStream(outputFileName));
			byte[] buffer = new byte[1024];
			int i = -1;
			while ((i = gcis.read(buffer)) != -1) {
				bos.write(buffer, 0, i);
			}
			LOGGER.info("GZIP文件解压成功");
		} catch (Exception e) {
			LOGGER.error("GZIP文件解压失败", e);
			throw new RuntimeException(e);
		} finally {
			if (null != gcis) {
				gcis.close();
			}
			if (null != bos) {
				bos.close();
			}
		}
	}

	/**
	 * TAR文件解压
	 * 
	 * @throws IOException 
	 * @date 2017年12月19日 下午8:36:28
	 */
	public void uncompressTar(String filePath) throws IOException {
		LOGGER.info("uncompressTar()========================================================");
		File tarFile = new File(filePath);
		String parent = tarFile.getParent();
		// 文件输入流
		TarArchiveInputStream tais = null;
		try {
			tais = new TarArchiveInputStream(new FileInputStream(tarFile));
			TarArchiveEntry entry = null;
			while ((entry = tais.getNextTarEntry()) != null) {
				LOGGER.info("entry.getName(): " + entry.getName());
				String targetFileName = entry.getName();
				File file = new File(parent, targetFileName);
				// 判断父目录
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				// 如果是文件夹，递归创建文件夹；如果是文件则写入文件
				if (!entry.isDirectory()) {
					// 文件输出流
					BufferedOutputStream bos = null;
					try {
						bos = new BufferedOutputStream(new FileOutputStream(file));
						byte[] buffer = new byte[1024];
						int len = -1;
						while ((len = tais.read(buffer)) != -1) {
							bos.write(buffer, 0, len);
						}
					} catch (Exception e) {
						throw new IOException(e);
					} finally {
						if (null != bos) {
							bos.close();
						}
					}
				}
			}
			LOGGER.info("TAR文件解压成功");
		} catch (Exception e) {
			LOGGER.error("TAR文件解压失败", e);
			throw new RuntimeException(e);
		} finally {
			if (null != tais) {
				tais.close();
			}
		}
	}

	/**
	 * ZIP文件解压
	 * 
	 * @throws IOException 
	 * @date 2017年12月19日 下午8:35:58
	 */
	public void uncompressZip(String filePath) throws IOException {
		LOGGER.info("uncompressZip()========================================================");
		File file = new File(filePath);
		String parentPath = file.getParent();
		ZipFile zipFile = null;
		try {
			// ZIP文件
			zipFile = new ZipFile(file);
			Enumeration<ZipArchiveEntry> entries = zipFile.getEntries();
			while (entries.hasMoreElements()) {
				ZipArchiveEntry zipArchiveEntry = (ZipArchiveEntry) entries.nextElement();
				String fileName = zipArchiveEntry.getName();
				LOGGER.info("zipArchiveEntry.getName(): " + fileName);
				File targetFile = new File(parentPath, fileName);
				// 创建父目录
				if (!targetFile.getParentFile().exists()) {
					targetFile.getParentFile().mkdirs();
				}
				// 如果是文件夹，递归创建文件夹；如果是文件则写入文件
				if (!zipArchiveEntry.isDirectory()) {
					// 文件输入流
					BufferedInputStream bis = null;
					// 文件输出流
					BufferedOutputStream bos = null;
					try {
						bis = new BufferedInputStream(zipFile.getInputStream(zipArchiveEntry));
						bos = new BufferedOutputStream(new FileOutputStream(targetFile));
						byte[] buffer = new byte[1024];
						int len = -1;
						while ((len = bis.read(buffer)) != -1) {
							bos.write(buffer, 0, len);
						}
					} catch (Exception e) {
						throw new IOException(e);
					} finally {
						if (null != bos) {
							bos.close();
						}
						if (null != bis) {
							bis.close();
						}
					}
				}
			}
			LOGGER.info("ZIP文件解压成功");
		} catch (Exception e) {
			LOGGER.error("ZIP文件解压失败", e);
			throw new RuntimeException(e);
		} finally {
			if (null != zipFile) {
				zipFile.close();
			}
		}
	}

	/**
	 * ZIP文件解压
	 * 
	 * @throws IOException
	 * @date 2017年12月19日 下午8:35:58
	 */
	public void uncompressZip2(String filePath) throws IOException {
		LOGGER.info("uncompressZip2()========================================================");
		File file = new File(filePath);
		String parentPath = file.getParent();
		// 文件输入流
		ZipArchiveInputStream zais = null;
		// 文件输出流
		BufferedOutputStream bos = null;
		try {
			zais = new ZipArchiveInputStream(new FileInputStream(file));
			ZipArchiveEntry entry = null;
			while ((entry = zais.getNextZipEntry()) != null) {
				String fileName = entry.getName();
				LOGGER.info("entry.getName(): " + fileName);
				File targetFile = new File(parentPath, fileName);
				// 创建父目录
				if (!targetFile.getParentFile().exists()) {
					targetFile.getParentFile().mkdirs();
				}
				// 如果是文件夹，递归创建文件夹；如果是文件则写入文件
				if (!entry.isDirectory()) {
					// 文件输出流
					bos = new BufferedOutputStream(new FileOutputStream(targetFile));
					byte[] buffer = new byte[1024];
					int len = -1;
					while ((len = zais.read(buffer)) != -1) {
						bos.write(buffer, 0, len);
					}
				}
			}
			LOGGER.info("ZIP文件解压成功");
		} catch (Exception e) {
			LOGGER.error("ZIP文件解压失败", e);
			throw new RuntimeException(e);
		} finally {
			if (null != zais) {
				zais.close();
			}
			if (null != bos) {
				bos.close();
			}
		}
	}

	/**
	 * RAR文件解压
	 * 
	 * @throws IOException
	 * @date 2017年12月19日 下午9:58:58
	 */
	public void uncompressRar(String filePath) throws IOException {
		LOGGER.info("uncompressRar()========================================================");
		File file = new File(filePath);
		String parentPath = file.getParent();
		Archive archive = null;
		try {
			archive = new Archive(file);
			FileHeader fileHeader = null;
			while ((fileHeader = archive.nextFileHeader()) != null) {
				String fileName = fileHeader.getFileNameW().isEmpty() ? fileHeader.getFileNameString() : fileHeader.getFileNameW();
				File targetFile = new File(parentPath, fileName);
				// 创建父目录
				if (!targetFile.getParentFile().exists()) {
					targetFile.getParentFile().mkdirs();
				}
				// 如果是文件夹，递归创建文件夹；如果是文件则写入文件
				if (!fileHeader.isDirectory()) {
					// 文件输出流
					BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(targetFile));
					// 解压文件
					archive.extractFile(fileHeader, bos);
				}
				archive.close();
			}
			LOGGER.info("RAR文件解压成功");
		} catch (Exception e) {
			LOGGER.error("RAR文件解压失败", e);
			throw new RuntimeException(e);
		} finally {
			if (null != archive) {
				archive.close();
			}
		}
	}

	/**
	 * CSV数据解析
	 * 
	 * @throws IOException
	 * @date 2017年12月19日 上午9:54:21
	 */
	public List<DynamicInfo> parseCvs(String filePath) throws IOException {
		LOGGER.info("phraseCvs()========================================================");
		List<DynamicInfo> list = new ArrayList<DynamicInfo>();
		BufferedReader br = null;
		try {
			File cvsFile = new File(filePath);
			br = new BufferedReader(new InputStreamReader(new FileInputStream(cvsFile), "utf-8"));
			// 表头
			String line = br.readLine().replaceAll("\"|\'", "");
			if (null != line) {
				String[] ths = line.split(",");
				// title
				JSONObject title = new JSONObject();
				for (int i = 0; i < ths.length; i++) {
					if (i != 8 && i != 9 && i != 10 && i != 4) {
						title.put(ths[i], ths[i]);
					}
				}
				while ((line = br.readLine()) != null) {
					String[] values = line.replaceAll("\"|\'", "").split(",");
					// 出发时间/触发时间
					String triggerTime = values[10];
					// 出发地
					String origin = values[8];
					// 目的地
					String destination = values[9];
					// 匹配预警地单位
					String alarmUnitStr = values[4];
					// 身份证
					String idCard = values[2];
					
					// 轨迹数据封装（JSON）
					JSONObject jsonObject = new JSONObject();
					// value数组
					List<JSONObject> valueList = new ArrayList<JSONObject>();
					for (int i = 0; i < values.length; i++) {
						if (i != 8 && i != 9 && i != 10 && i != 4) {
							// values
							JSONObject val = new JSONObject();
							val.put("key", ths[i]);
							val.put("value", values[i]);
							val.put("type", "string");
							valueList.add(val);
						}
					}
					jsonObject.put("title", title);
					jsonObject.put("value", valueList);
					
					// 轨迹数据实体
					DynamicInfo dynamicInfo = new DynamicInfo();
					dynamicInfo.setInformation(jsonObject.toString());
					dynamicInfo.setType(1);
					dynamicInfo.setTriggerTime(triggerTime);
					dynamicInfo.setOrigin(origin);
					dynamicInfo.setDestination(destination);
					// 匹配预警地单位
					if (null != alarmUnitStr && alarmUnitStr.length() == 12) {
						dynamicInfo.setAlarmUnit(UnitCodeUtil.matchUnit(alarmUnitStr));
					}
					// 比对重点人员
					// 是重点人员生成轨迹记录、预警记录
					ControlPerson controlPerson = controlPersonService.getByIdCard(idCard);
					if (null != controlPerson) {
						dynamicInfo.setControlPerson(controlPerson);
						list.add(dynamicInfo);
					} else if (true) {
						// 铁公机处理
					}
				}
			}
			LOGGER.info("轨迹数据解析成功");
		} catch (Exception e) {
			LOGGER.error("轨迹数据解析失败", e);
			throw new RuntimeException(e);
		} finally {
			if (null != br) {
				br.close();
			}
		}
		return list;
	}

	/**
	 * Excel数据解析
	 * 
	 * @throws IOException
	 * @date 2017年12月20日 下午4:30:42
	 */
	public void parseExcel(String filePath) throws IOException {
		LOGGER.info("phraseExcel()========================================================");
		File cvsFile = new File(filePath);
		Workbook wb = null;
		try {
			// 导入Excel文件
			wb = WorkbookFactory.create(cvsFile);
			// 获取工件簿
			Sheet sheet = wb.getSheetAt(0);
			DataFormatter dataFormatter = new DataFormatter();
			// 遍历行
			for (Row row : sheet) {
				System.out.print("\n" + row.getRowNum() + "\t");
				// 遍历列
				for (Cell cell : row) {
					System.out.print(dataFormatter.formatCellValue(cell) + "\t");
				}
			}
			LOGGER.info("解析Excel成功");
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			LOGGER.error("解析Excel失败", e);
			throw new RuntimeException(e);
		} finally {
			if (null != wb) {
				wb.close();
			}
		}
	}

	/**
	 * 数据插入数据库
	 * 
	 * @date 2017年12月19日 上午9:50:57
	 */
	public void insertAll(List<DynamicInfo> list) {
		LOGGER.info("insertAll()========================================================");
		int count = 0;
		for (DynamicInfo dynamicInfo : list) {
			try {
				// 插入记录
				dynamicInfoService.save(dynamicInfo);
				count++;
			} catch (Exception e) {
				LOGGER.error("插入轨迹数据失败", e);
			}
		}
		LOGGER.info("插入全部轨迹数据成功("+ count + "条)");
	}
	
	/**
	 * 离市人员数据插入数据库
	 * 
	 * @date 2017年12月30日 上午9:24:12
	 */
	public void insertLeaveAll(List<LeavePerson> list) {
		LOGGER.info("insertLeaveAll()========================================================");
		int count = 0;
		for (LeavePerson leavePerson : list) {
			try {
				// 插入记录
				leavePersonService.save(leavePerson);
				count++;
			} catch (Exception e) {
				LOGGER.error(leavePerson.getId() + " ->插入离市人员数据失败", e);
			}
		}
		LOGGER.info("插入离市人员数据成功("+ count + "条)");
	}
}
