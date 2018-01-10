package com.ruiec.web.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Enumeration;

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
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;
import com.ruiec.web.service.AaTestService;

/**
 * 测试服务实现类
 * 
 * @author bingo<br>
 * @date 2017年12月19日 上午9:41:49
 */
@Service
public class AaTestServiceImpl implements AaTestService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(AaTestServiceImpl.class);

	/**
	 * 数据抓取主方法
	 * 
	 * @author bingo<br>
	 * @date 2017年12月19日 上午9:49:06
	 */
	public int mainMethod() {
		// 1. 下载数据包

		// 2. 解压数据包

		// 3. 解析数据

		// 4. 数据插入数据库
		
		System.out.println(new Date());
		
		return 0;
	}

	/**
	 * 解压
	 * 
	 * @author bingo<br>
	 * @throws IOException
	 * @date 2017年12月19日 上午9:49:47
	 */
	@Test
	public void uncompressArchiveGzip() throws IOException {
		System.out.println("uncompress()========================================================");
		File archiveGzipFile = new File("F:\\targz\\JDK_API_1_6_zh_CN.tar.gz");
		// 文件输入流
		GzipCompressorInputStream gcis = new GzipCompressorInputStream(new FileInputStream(archiveGzipFile));
		// 父目录
		String parentPath = archiveGzipFile.getParent();
		String fileName = archiveGzipFile.getName();
		fileName = "a.jpg";
		// fileName = fileName.substring(0, fileName.lastIndexOf("."));
		// 输出文件
		String outputFileName = parentPath + File.separator + fileName;

		// 文件输出流
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputFileName));

		byte[] buffer = new byte[1024];
		int i = -1;
		while ((i = gcis.read(buffer)) != -1) {
			bos.write(buffer, 0, i);
		}
		bos.close();
		gcis.close();
	}

	/**
	 * TAR文件解压
	 * 
	 * @author bingo<br>
	 * @date 2017年12月19日 下午8:36:28
	 */
	@Test
	public void uncompressTar() throws IOException {
		System.out.println("uncompressTar()========================================================");
		File tarFile = new File("F:\\targz\\模压在.tar");
		String parent = tarFile.getParent();

		// 文件输入流
		TarArchiveInputStream tais = new TarArchiveInputStream(new FileInputStream(tarFile));
		TarArchiveEntry entry = null;
		while ((entry = tais.getNextTarEntry()) != null) {
			System.out.println("entry.getName(): " + entry.getName());
			String targetFileName = entry.getName();
			File file = new File(parent, targetFileName);
			// 判断父目录
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			// 如果是文件夹，递归创建文件夹；如果是文件则写入文件
			if (!entry.isDirectory()) {
				// 文件输出流
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));

				byte[] buffer = new byte[1024];
				int len = -1;
				while ((len = tais.read(buffer)) != -1) {
					bos.write(buffer, 0, len);
				}
				bos.close();
			}
		}
		tais.close();
	}

	/**
	 * ZIP文件解压
	 * 
	 * @author bingo<br>
	 * @date 2017年12月19日 下午8:35:58
	 */
	@Test
	public void uncompressZip() throws IOException {
		System.out.println("uncompressZip()========================================================");
		File file = new File("F:\\targz\\模压在.zip");
		String parentPath = file.getParent();
		ZipFile zipFile = new ZipFile(file);

		Enumeration<ZipArchiveEntry> entries = zipFile.getEntries();
		while (entries.hasMoreElements()) {
			ZipArchiveEntry zipArchiveEntry = (ZipArchiveEntry) entries.nextElement();
			String fileName = zipArchiveEntry.getName();
			System.out.println("zipArchiveEntry.getName(): " + fileName);
			File targetFile = new File(parentPath, fileName);
			// 创建父目录
			if (!targetFile.getParentFile().exists()) {
				targetFile.getParentFile().mkdirs();
			}
			// 如果是文件夹，递归创建文件夹；如果是文件则写入文件
			if (!zipArchiveEntry.isDirectory()) {
				// 文件输入流
				BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(zipArchiveEntry));
				// 文件输出流
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(targetFile));
				byte[] buffer = new byte[1024];
				int len = -1;
				while ((len = bis.read(buffer)) != -1) {
					bos.write(buffer, 0, len);
				}
				bos.close();
				bis.close();
			}
		}
		zipFile.close();
	}

	/**
	 * ZIP文件解压
	 * 
	 * @author bingo<br>
	 * @date 2017年12月19日 下午8:35:58
	 */
	@Test
	public void uncompressZip2() throws IOException {
		System.out.println("uncompressZip()========================================================");
		File file = new File("F:\\targz\\模压在.zip");
		String parentPath = file.getParent();

		// zip文件输入流
		ZipArchiveInputStream zais = new ZipArchiveInputStream(new FileInputStream(file));

		ZipArchiveEntry entry = null;
		while ((entry = zais.getNextZipEntry()) != null) {
			String fileName = entry.getName();
			System.out.println("entry.getName(): " + fileName);
			File targetFile = new File(parentPath, fileName);
			// 创建父目录
			if (!targetFile.getParentFile().exists()) {
				targetFile.getParentFile().mkdirs();
			}
			// 如果是文件夹，递归创建文件夹；如果是文件则写入文件
			if (!entry.isDirectory()) {
				// 文件输出流
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(targetFile));
				byte[] buffer = new byte[1024];
				int len = -1;
				while ((len = zais.read(buffer)) != -1) {
					bos.write(buffer, 0, len);
				}
				bos.close();
			}
		}
		zais.close();
	}

	/**
	 * RAR文件解压
	 * 
	 * @author bingo<br>
	 * @date 2017年12月19日 下午9:58:58
	 */
	@Test
	public void uncompressRar() throws RarException, IOException {
		System.out.println("uncompressRar()========================================================");
		File file = new File("F:\\targz\\模压在.rar");
		String parentPath = file.getParent();

		Archive archive = new Archive(file);
		FileHeader fileHeader = null;
		while ((fileHeader = archive.nextFileHeader()) != null) {
			String fileName = fileHeader.getFileNameW().isEmpty() ? fileHeader.getFileNameString() : fileHeader.getFileNameW();
			File targetFile = new File(parentPath, fileName);
			System.out.println("fileHeader.getFileNameW(): " + fileHeader.getFileNameW());
			System.out.println("fileHeader.getFileNameString(): " + fileHeader.getFileNameString());
			
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
		}
		archive.close();
	}

	/**
	 * CSV数据解析
	 * 
	 * @author bingo<br>
	 * @date 2017年12月19日 上午9:54:21
	 */
	@Test
	public void parseCvs() throws IOException {
		System.out.println("phraseCvs()========================================================");
		File cvsFile = new File("F:\\targz\\cvs测试.csv");
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(cvsFile), "GBK"));
		String line = null;
		while ((line = br.readLine()) != null) {
			String[] values = line.split(",");
			System.out.println(values[0]+"\t"+values[1]+"\t"+values[2]);
		}
		br.close();
	}
	
	/**
	 * Excel数据解析
	 * 
	 * @author bingo<br>
	 * @date 2017年12月20日 下午4:30:42
	 */
	@Test
	public void parseExcel() {
		System.out.println("phraseExcel()========================================================");
		File cvsFile = new File("F:\\targz\\cvs测试.xls");
		try {
			// 导入Excel文件
			Workbook wb = WorkbookFactory.create(cvsFile);
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
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			LOGGER.error("解析Excel失败", e);
			e.printStackTrace();
		}
	}

	/**
	 * 数据插入数据库
	 * 
	 * @author bingo<br>
	 * @date 2017年12月19日 上午9:50:57
	 */
	public void insertAll() {

	}
}
