package com.partner4java.p4jtools.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Excel读取
 * 
 * 此工具类使用apache.poi框架为基础 编写,只支持.xls(2003)和.xlsx(2007)格式的Excel
 *
 */
public class ExcelUtil {

	/**
	 * 构建到处文件至指定目录
	 * 
	 * @param filePath
	 *            文件生成路径
	 * @param sheetName
	 *            sheet1名称
	 * @param names
	 *            第一行标题名称（可以传入null）
	 * @param values
	 *            数据列表
	 * @return
	 */
	public static boolean buildExcel(String filePath, String sheetName, List<List<Object>> values) {
		FileOutputStream outputStream = null;
		HSSFWorkbook wb = null;
		try {
			outputStream = new FileOutputStream(filePath);

			// 创建HSSFWorkbook对象(excel的文档对象)
			wb = new HSSFWorkbook();
			// 建立新的sheet对象（excel的表单）
			HSSFSheet sheet = wb.createSheet(sheetName);

			if (values != null && values.size() > 0) {
				for (int i = 0; i < values.size(); i++) {
					HSSFRow valueRow = sheet.createRow(i);
					List<Object> fValues = values.get(i);
					for (int j = 0; j < fValues.size(); j++) {
						valueRow.createCell(j).setCellValue(fValues.get(j).toString());
					}
				}
			}

			wb.write(outputStream);
			outputStream.flush();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (wb != null) {
						// wb.close();
						wb = null;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return false;

	}

	/**
	 * 构建Workbook解析对象
	 * 
	 * @param filePath
	 *            文件路径
	 * @return Workbook
	 */
	public static Workbook buildWorkbook(String filePath) {
		FileInputStream fis = null;
		try {
			if (StringUtils.isEmpty(filePath)) {
				throw new IllegalArgumentException("参数错误!");
			}

			fis = new FileInputStream(filePath);

			if (filePath.trim().toLowerCase().endsWith(".xls")) {
				return new HSSFWorkbook(fis);
			} else if (filePath.trim().toLowerCase().endsWith(".xlsx")) {
				return new XSSFWorkbook(fis);
			} else {
				throw new IllegalArgumentException("其他格式的电子表格不支持!!!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 根据sheet名称获取Excel的sheet对象
	 * 
	 * @param wb
	 * @param sheetName
	 * @return
	 */
	public static Sheet getSheet(Workbook wb, String sheetName) {
		if (wb != null && !StringUtils.isEmpty(sheetName)) {
			return wb.getSheet(sheetName);
		}
		return null;
	}

	/**
	 * 根据sheet的位置下标获取Excel的sheet对象
	 * 
	 * @param wb
	 * @param index
	 * @return
	 */
	public static Sheet getSheet(Workbook wb, int index) {
		if (wb != null && index >= 0) {
			return wb.getSheetAt(index);
		}
		return null;
	}

	/**
	 * 根据sheet和index创建Row对象
	 * 
	 * @param sheet
	 * @param index
	 * @return
	 */
	public static Row createRow(Sheet sheet, int index) {
		if (sheet != null && index >= 0) {
			return sheet.createRow(index);
		}
		return null;
	}

	/**
	 * 根据Row对象和index创建Cell对象
	 * 
	 * @param row
	 * @param index
	 * @return
	 */
	public static Cell createCell(Row row, int index) {
		if (row != null && index >= 0) {
			return row.createCell(index);
		}
		return null;
	}

	public static int getIntCellValue(Cell cell) {
		if (HSSFCell.CELL_TYPE_NUMERIC == cell.getCellType()) {
			return (int) cell.getNumericCellValue();
		}

		if (StringUtils.isEmpty(cell.getStringCellValue())) {
			return 0;
		}

		return Integer.valueOf(cell.getStringCellValue());
	}

	public static long getLongCellValue(Cell cell) {
		if (HSSFCell.CELL_TYPE_NUMERIC == cell.getCellType()) {
			return (long) cell.getNumericCellValue();
		}

		if (StringUtils.isEmpty(cell.getStringCellValue())) {
			return 0;
		}

		return Long.valueOf(cell.getStringCellValue());
	}

	/**
	 * 获取Excel单元格值,如果是数值类型则转换成字符串
	 * 
	 * @param cell
	 * @return
	 */
	public static String getCellValue(Cell cell) {
		String value = null;
		if (HSSFCell.CELL_TYPE_NUMERIC == cell.getCellType()) {
			value = String.valueOf(cell.getNumericCellValue());
		} else {
			value = cell.getStringCellValue();
		}
		return value;
	}
}
