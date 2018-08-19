package com.reports.common;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.reports.imports.xmlhandle.Columnx;
import com.reports.imports.xmlhandle.Import;
import com.reports.imports.xmlhandle.Rowx;
import com.reports.imports.xmlhandle.Sheetx;

/**
 * 报表导入工具类
 */
public class ImportUtil {

	private static final Logger logger = LoggerFactory.getLogger(ImportUtil.class);

	/**
	 * 获取报表的sheet列表
	 * 
	 * @param report
	 *            report.xml中定义的报表节点
	 * @return sheet列表
	 */
	public static ArrayList<Sheetx> getSheets(Import importx) {
		return importx.getSheets();
	}

	/**
	 * 获取报表的Row列表
	 * 
	 * @param sheet
	 *            report.xml报表中的sheet节点
	 * @return Row列表
	 */
	public static ArrayList<Rowx> getRows(Sheetx sheetx) {
		return sheetx.getRows();
	}

	/**
	 * 获取报表的Column列表
	 * 
	 * @param row
	 *            report.xml报表中的row节点
	 * @return Column列表
	 */
	public static ArrayList<Columnx> getColumns(Rowx rowx) {
		return rowx.getColumns();
	}

	/**
	 * 取单元格的值
	 * 
	 * @param cell
	 *            单元格对象
	 * @return
	 */
	public static String getCellValue(String type, Cell cell) {
		String value = "";
		if (cell == null) {
			return value;
		}
		switch (cell.getCellTypeEnum()) {
		case _NONE:
			value = "";
			break;
		case BOOLEAN:
			value = Boolean.toString(cell.getBooleanCellValue());
			break;
		case NUMERIC:
			short format = cell.getCellStyle().getDataFormat();
			if ((DateUtil.isCellDateFormatted(cell)) || format == 14 || format == 31 || format == 57 || format == 58) {
				// 日期
				Date date = cell.getDateCellValue();
				if ("sdate".equalsIgnoreCase(type)) {
					value = DateFormatUtils.format(date, "yyyyMMdd");
				} else if ("ldate".equalsIgnoreCase(type)) {
					value = DateFormatUtils.format(date, "yyyyMMddHHmmss");
				} else if ("time".equalsIgnoreCase(type)) {
					value = DateFormatUtils.format(date, "HHmmss");
				}
			} else {
				if ("int".equalsIgnoreCase(type)) {
					DecimalFormat df = new DecimalFormat("##0");
					value = df.format(cell.getNumericCellValue());
				} else if ("number".equalsIgnoreCase(type)) {
					DecimalFormat df = new DecimalFormat("##0.0000");
					value = df.format(cell.getNumericCellValue());
				} else if ("sdate".equalsIgnoreCase(type)) {
					// 日期
					Date date = cell.getDateCellValue();
					value = DateFormatUtils.format(date, "yyyyMMdd");
				} else if ("ldate".equalsIgnoreCase(type)) {
					// 日期
					Date date = cell.getDateCellValue();
					value = DateFormatUtils.format(date, "yyyyMMddHHmmss");
				} else if ("time".equalsIgnoreCase(type)) {
					// 日期
					Date date = cell.getDateCellValue();
					value = DateFormatUtils.format(date, "HHmmss");
				} else {
					try {
						DecimalFormat df = new DecimalFormat("##0");
						value = df.format(cell.getNumericCellValue());
					} catch (Exception e) {
						value = cell.getStringCellValue();
					}
				}
			}
			break;
		case STRING:
			if (cell.getRichStringCellValue() == null) {
				value = "";
			} else {
				if ("sdate".equalsIgnoreCase(type) || "ldate".equalsIgnoreCase(type) || "time".equalsIgnoreCase(type)) {
					if (null != cell.getStringCellValue() && cell.getStringCellValue().trim().length() != 0) {
						if ("sdate".equalsIgnoreCase(type)) {  //短日期类型
							try {
								Date date = cell.getDateCellValue();
								value = DateFormatUtils.format(date, "yyyyMMdd");
							} catch(Exception e) {
								SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
								SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMdd");
								try {
									Date date = format1.parse(cell.getStringCellValue());
									value = format2.format(date);
								} catch (ParseException e1) {
									e1.printStackTrace();
								}
							}
						} else if ("ldate".equalsIgnoreCase(type)) {    //长日期类型
							try {
								Date date = cell.getDateCellValue();
								value = DateFormatUtils.format(date, "yyyyMMddHHmmss");
							} catch(Exception e) {
								SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMddHHmmss");
								try {
									Date date = format1.parse(cell.getStringCellValue());
									value = format2.format(date);
								} catch (ParseException e1) {
									e1.printStackTrace();
								}
							}
						} else if ("time".equalsIgnoreCase(type)) {     //短时间类型
							try {
								Date date = cell.getDateCellValue();
								value = DateFormatUtils.format(date, "HHmmss");
							} catch(Exception e) {
								SimpleDateFormat format1 = new SimpleDateFormat("HH:mm:ss");
								SimpleDateFormat format2 = new SimpleDateFormat("HHmmss");
								try {
									Date date = format1.parse(cell.getStringCellValue());
									value = format2.format(date);
								} catch (ParseException e1) {
									e1.printStackTrace();
								}
							}
						}
					}
				} else {
					value = cell.getRichStringCellValue().toString();
				}
			}
			break;
		case ERROR:
			value = "";
			break;
		case BLANK:
			value = "";
			break;
		case FORMULA:
			try {
				value = String.valueOf(cell.getStringCellValue());
			} catch (IllegalStateException e) {
				value = String.valueOf(cell.getNumericCellValue());
			}
			break;
		default:
			value = "";
			break;
		}
		return value;
	}

	/**
	 * 获取合并单元格的值
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	public static String getMergedRegionValue(Sheet sheet, String type, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();
			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					Row Row = sheet.getRow(firstRow);
					Cell fCell = Row.getCell(firstColumn);
					return getCellValue(type, fCell);
				}
			}
		}
		return null;
	}

	/**
	 * 判断指定的单元格是否是合并单元格
	 * 
	 * @param sheet
	 * @param row
	 *            行下标
	 * @param column
	 *            列下标
	 * @return
	 */
	public static boolean isMergedRegion(Sheet Sheet, int row, int column) {
		int sheetMergeCount = Sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = Sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断sheet页中是否含有合并单元格
	 * 
	 * @param sheet
	 * @return
	 */
	public static boolean hasMerged(Sheet Sheet) {
		return Sheet.getNumMergedRegions() > 0 ? true : false;
	}

	/**
	 * 合并单元格
	 * 
	 * @param sheet
	 * @param firstRow
	 *            开始行
	 * @param lastRow
	 *            结束行
	 * @param firstCol
	 *            开始列
	 * @param lastCol
	 *            结束列
	 */
	public void mergeRegion(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
		sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
	}

	/**
	 * 检查单元格的值是否符合要求
	 * 
	 * @param sheetname
	 * @param row
	 * @param column
	 * @param value
	 * @return
	 */
	public static String checkCellValue(String sheetname, int row, Columnx column, String cellvalue) {
		String error = "";
		// excel默认从0开始，所以这里须 + 1
		int cell = column.getIndex() + 1;
		// 不允许为空
		if ("false".equalsIgnoreCase(column.getEmpty()) && StringUtils.isBlank(cellvalue)) {
			error = formatString(sheetname, row, cell, Constants.IMPORT_ERRORS_CHECK_EMPTY);
		}
		// 值不为空才校验类型
		if (StringUtils.isNotBlank(cellvalue)) {
			if ("int".equalsIgnoreCase(column.getType())) { // 检查int类型
				try {
					Integer.parseInt(cellvalue);
				} catch (Exception e) {
					e.printStackTrace();
					error = formatString(sheetname, row, cell, Constants.IMPORT_ERRORS_CHECK_TYPE_INT);
					logger.error("转换int类型错误" + error);
					return error;
				}
			} else if ("number".equalsIgnoreCase(column.getType())) { // 检查number类型
				try {
					new BigDecimal(cellvalue);
				} catch (Exception e) {
					e.printStackTrace();
					error = formatString(sheetname, row, cell, Constants.IMPORT_ERRORS_CHECK_TYPE_NUMBER);
					logger.error("转换number类型错误" + error);
					return error;
				}
			} else if ("sdate".equalsIgnoreCase(column.getType())) { // 检查sdate类型
				//短日期类型，必须是8位
				if (cellvalue.trim().length() != 8) {
					error = formatString(sheetname, row, cell, Constants.IMPORT_ERRORS_CHECK_TYPE_SDATE);
					logger.error("转换short date类型错误" + error);
					return error;
				} else {
					try {
						SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
						format.parse(cellvalue);
					} catch (Exception e) {
						e.printStackTrace();
						error = formatString(sheetname, row, cell, Constants.IMPORT_ERRORS_CHECK_TYPE_SDATE);
						logger.error("转换short date类型错误" + error);
						return error;
					}
				}
			} else if ("ldate".equalsIgnoreCase(column.getType())) { // 检查ldate类型
				//长日期类型，必须是14位
				if (cellvalue.trim().length() != 14) {
					error = formatString(sheetname, row, cell, Constants.IMPORT_ERRORS_CHECK_TYPE_LDATE);
					logger.error("转换Long date类型错误" + error);
					return error;
				} else {
					try {
						SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
						format.parse(cellvalue);
					} catch (Exception e) {
						e.printStackTrace();
						error = formatString(sheetname, row, cell, Constants.IMPORT_ERRORS_CHECK_TYPE_LDATE);
						logger.error("转换Long date类型错误" + error);
						return error;
					}
				}
			} else if ("time".equalsIgnoreCase(column.getType())) { // 检查time类型
				//时间类型，必须是6位
				if (cellvalue.trim().length() != 6) {
					error = formatString(sheetname, row, cell, Constants.IMPORT_ERRORS_CHECK_TYPE_TIME);
					logger.error("转换short time类型错误" + error);
					return error;
				} else {
					try {
						SimpleDateFormat format = new SimpleDateFormat("HHmmss");
						format.parse(cellvalue);
					} catch (Exception e) {
						e.printStackTrace();
						error = formatString(sheetname, row, cell, Constants.IMPORT_ERRORS_CHECK_TYPE_TIME);
						logger.error("转换short time类型错误" + error);
						return error;
					}
				}
			}
		}
		return error;
	}

	/**
	 * 错误信息格式化
	 * 
	 * @param sheetname
	 * @param row
	 * @param cell
	 * @param errorinfo
	 * @return
	 */
	public static String formatString(String sheetname, int row, int cell, String errorinfo) {
		return String.format("sheet: %s 行：%s 列：%s %s %s", sheetname, row, cell, errorinfo, Constants.IMPORT_ERRORS_CHECK_SEPARATOR);
	}

}
