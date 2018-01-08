package com.ruiec.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 读取excel文件
 * 
 * @author Jiacheng Poi解析2003时使用的是HSSFCell，而2007的则是 XSSFCell，是完全不同的两套API
 *         必须先要判断excel的类型，不过 HSSFWorkbook 和 XSSFWorkbook
 *         实现的接口都是一样的Workbook，直接在实例化接口的时候有点区别其他时候没有任何差异。
 */
public class ExcelUtil {
	public static List<List<Object>> readExcel(File file) throws IOException {
		String fileName = file.getName();
		String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName.substring(fileName.lastIndexOf(".") + 1);
		if ("xlsx".equals(extension)) {
			return read2007Excel(file);
		} else {
			throw new IOException("不支持的文件类型");
		}
	}


	/**
	 * 读取Office 2007 excel
	 */
	private static List<List<Object>> read2007Excel(File file) throws IOException {
		List<List<Object>> list = new LinkedList<List<Object>>();
		// 构造 XSSFWorkbook 对象，strPath 传入文件路径
		@SuppressWarnings("resource")
		XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));
		// 读取第一章表格内容
		XSSFSheet sheet = xwb.getSheetAt(0);
	    System.out.println("共"+sheet.getRow(0).getLastCellNum()+"列");// 查看sheet的列
	    System.out.println("共"+sheet.getLastRowNum()+"行");// 查看sheet的行
		Object value = null;
		XSSFRow row = null;
		XSSFCell cell = null;
		for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
			if (i == 0) {
				// 跳过第一行
				continue;
			}
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			List<Object> linked = new LinkedList<Object>();
			for (int j = sheet.getRow(0).getFirstCellNum();j < sheet.getRow(0).getLastCellNum(); j++) {
				cell = row.getCell(j);
				if (cell == null) {
					value = "无";// 导入不能为空
					linked.add(value);
					continue;
				}
				DecimalFormat df = new DecimalFormat("0");// 格式化 number String 字符
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 格式化日期字符串
				DecimalFormat nf = new DecimalFormat("0");// 格式化数字
				switch (cell.getCellType()) {
				case XSSFCell.CELL_TYPE_STRING:
					value = cell.getStringCellValue();
					break;
				case XSSFCell.CELL_TYPE_NUMERIC:
					if ("@".equals(cell.getCellStyle().getDataFormatString())) {
						value = df.format(cell.getNumericCellValue());
					} else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
						value = nf.format(cell.getNumericCellValue());
					} else {
						value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
					}
					break;
				case XSSFCell.CELL_TYPE_BOOLEAN:
					value = cell.getBooleanCellValue();
					break;
				case XSSFCell.CELL_TYPE_BLANK:// 空格，空白
					value = "";
					break;
				default:
					value = cell.toString();
				}
				if (value == null || "".equals(value)) {
					value = "无";// 导入不能为空
				}
				linked.add(value);
			}
			list.add(linked);
		}
		return list;
	}

	public static void main(String[] args) {
		try {
			int count = 0;
			long time1 = System.currentTimeMillis();
			List<List<Object>> list = readExcel(new File("D:\\test.xlsx"));
			System.out.println("读取花费时间：" + (System.currentTimeMillis() - time1) / 1000.0);

			for (List<Object> list2 : list) {
				count++;
				for (int i = 0; i < list2.size(); i++) {
					System.out.print(list2.get(i) + "\t\t");
				}
				System.out.println("第" + count + "组");
			}
			System.out.println("ok!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

