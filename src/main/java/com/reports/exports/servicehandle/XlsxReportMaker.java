package com.reports.exports.servicehandle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reports.common.Constants;
import com.reports.common.ExportUtil;
import com.reports.exports.xmlhandle.Columnx;
import com.reports.exports.xmlhandle.Report;
import com.reports.exports.xmlhandle.Rowx;
import com.reports.exports.xmlhandle.Sheetx;

/**
 * excel报表生成类
 */
public class XlsxReportMaker implements IReportMaker {
	private static final Logger logger = LoggerFactory.getLogger(XlsxReportMaker.class);

	private Workbook workbook = null;
	private Sheet sheet = null;
	private Row row = null;
	private Cell cell = null;
	private CellStyle cellStyle = null;
	//文件类型
	private String fileType = "";
	// 报表文件保存目录
	private String saveFileDir = "";
	// 模板文件存放目录
	private String templateDir = "";
	// 是否需要时间戳后缀
	private boolean isDateSuffix = false;

	/**
	 * 写报表
	 */
	@Override
	public <T> String writeReport(Report report, String fileType, String saveFileDir, String templateDir, Map<String, List<T>> dataMap, List<String> paramsList,
			boolean isDateSuffix) throws Exception {
		this.fileType = fileType;
		this.saveFileDir = saveFileDir;
		this.templateDir = templateDir;
		this.isDateSuffix = isDateSuffix;
		String fileName = "";
		// 判断报表是否定义为模板报表，若是，需要读取模板文件写入数据
		String template = report.getTemplate();
		if (StringUtils.isBlank(template)) {
			fileName = this.writeData(report, dataMap, paramsList);
		} else {
			fileName = this.writeTemplateData(report, dataMap, paramsList);
		}

		return fileName;
	}

	/**
	 * 写报表数据
	 * 
	 * @param report
	 *            report.xml中定义的报表节点
	 * @param dataMap
	 *            数据map对象
	 * @param paramsList
	 *            参数列表
	 * @return
	 */
	public <T> String writeData(Report report, Map<String, List<T>> dataMap, List<String> paramsList) {
		Object cellVal;
		String sheetName = "";
		OutputStream out = null;
		try {
			String fileName = report.getFilename();
			if (Constants.REPORT_TYPE_EXCEL_XLS.equals(fileType)) {
				workbook = new HSSFWorkbook();
			} else if (Constants.REPORT_TYPE_EXCEL_XLSX.equals(fileType)) {
				workbook = new XSSFWorkbook();
			}
			// 创建格式化数据对象
			DataFormat format = workbook.createDataFormat();
			ArrayList<Sheetx> sheets = ExportUtil.getSheets(report);
			for (Sheetx sheetx : sheets) {
				sheet = workbook.createSheet();
				sheetName = StringUtils.isBlank(sheetx.getName()) ? "sheet" + sheetx.getIndex() : sheetx.getName();
				sheetName = ExportUtil.replaceParams(sheetName, paramsList);
				workbook.setSheetName(sheetx.getIndex(), sheetName);
				ArrayList<Rowx> rows = ExportUtil.getRows(sheetx);
				for (Rowx rowx : rows) {
					int startRow = rowx.getSwrite();  //从配置行开始写入
					List<T> rowData = dataMap.get(rowx.getDatakey());
					// 写表头
					this.writeRowTitle(sheet, rowx);
					ArrayList<Columnx> columns = ExportUtil.getColumns(rowx);
					// 写表数据内容
					for (int i = 0; i < rowData.size(); i++) {
						//同一sheet中有多个数据块，如果已经创建了行，就直接取行
						if (null == sheet.getRow(startRow + i)) {
							row = sheet.createRow(startRow + i);
						} else {
							row = sheet.getRow(startRow + i);
						}
						for (Columnx column : columns) {
							//同一sheet中有多个数据块，如果已经创建了列，就直接取列
							if (null == row.getCell(column.getIndex())) {
								cell = row.createCell(column.getIndex());
							} else {
								cell = row.getCell(column.getIndex());
							}
							// 通过反射获取字段数据
							cellVal = ExportUtil.getFieldValueByName(column.getField(), rowData.get(i));
							if (null == cellVal) {
								cell.setCellValue("");
							} else {
								if (cellVal instanceof BigDecimal) {
									cell.setCellValue(((BigDecimal) cellVal).doubleValue());
								} else if (cellVal instanceof Double) {
									cell.setCellValue(Double.parseDouble(cellVal.toString()));
								} else {
									cell.setCellValue(cellVal.toString());
								}
								// 数据格式化
								if (StringUtils.isNotBlank(column.getFormatter())) {
									cellStyle = workbook.createCellStyle();
									cellStyle.setDataFormat(format.getFormat(column.getFormatter()));
									cell.setCellStyle(cellStyle);
								}
							}
						}
					}
				}
			}
			// 将workbook流写入文件
			File file = ExportUtil.createFile(fileName, fileType, paramsList, saveFileDir, isDateSuffix);
			out = new FileOutputStream(file);
			workbook.write(out);
			out.flush();
			out.close();
			return file.toString();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 读取模板文件写入数据
	 * 
	 * @param report
	 *            report.xml中定义的报表节点
	 * @param dataMap
	 *            数据map对象
	 * @param paramsList
	 *            参数列表
	 * @return
	 */
	public <T> String writeTemplateData(Report report, Map<String, List<T>> dataMap, List<String> paramsList) {
		Object cellVal;
		InputStream is = null;
		OutputStream out = null;
		try {
			String fileName = report.getFilename();
			// 模板文件名
			String templateFile = templateDir + File.separator + report.getTemplate();
			is = new FileInputStream(templateFile);
			workbook = WorkbookFactory.create(is);
			ArrayList<Sheetx> sheets = ExportUtil.getSheets(report);
			for (Sheetx sheetx : sheets) {
				sheet = workbook.getSheetAt(sheetx.getIndex());
				ArrayList<Rowx> rows = ExportUtil.getRows(sheetx);
				for (Rowx rowx : rows) {
					int startRow = rowx.getSwrite();
					List<T> rowData = dataMap.get(rowx.getDatakey());
					ArrayList<Columnx> columns = ExportUtil.getColumns(rowx);
					// 写表数据内容
					for (int i = 0; i < rowData.size(); i++) {
						//同一sheet中有多个数据块，如果已经创建了行，就直接取行
						if (null == sheet.getRow(startRow + i)) {
							row = sheet.createRow(startRow + i);
						} else {
							row = sheet.getRow(startRow + i);
						}
						for (Columnx column : columns) {
							//同一sheet中有多个数据块，如果已经创建了列，就直接取列
							if (null == row.getCell(column.getIndex())) {
								cell = row.createCell(column.getIndex());
							} else {
								cell = row.getCell(column.getIndex());
							}
							// 通过反射获取字段数据
							cellVal = ExportUtil.getFieldValueByName(column.getField(), rowData.get(i));
							if (cellVal instanceof BigDecimal) {
								if (StringUtils.isNotBlank(column.getFormatter())) {
									DecimalFormat df = new DecimalFormat(column.getFormatter());
									cell.setCellValue(df.format(cellVal));
								} else {
									cell.setCellValue(((BigDecimal) cellVal).doubleValue());
								}
							} else {
								cell.setCellValue(cellVal.toString());
							}
						}
					}
					logger.info("----------写入数据块完成----------   sheet序号：" + sheetx.getIndex() + "  数据块序号：" + rowx.getIndex() + "  开始写入行：" + rowx.getSwrite() + "  数据key："
							+ rowx.getDatakey());
				}
			}
			// 将workbook流写入文件
			File file = ExportUtil.createFile(fileName, fileType, paramsList, saveFileDir, isDateSuffix);
			out = new FileOutputStream(file);
			// 激活全部单元格的计算公式
			workbook.getCreationHelper().createFormulaEvaluator().evaluateAll();
			workbook.setForceFormulaRecalculation(true);
			workbook.write(out);
			out.flush();
			out.close();
			return file.toString();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 写表头
	 * 
	 * @param hssfSheet
	 *            HSSFSheet对象
	 * @param row
	 *            report.xml中定义的row节点
	 */
	public void writeRowTitle(Sheet sheet, Rowx row) {
		Row titleRow = null;
		// 创建第一行
		if (null == sheet.getRow(row.getSwrite() - 1)) {
			titleRow = sheet.createRow(row.getSwrite() - 1);
		} else {
			titleRow = sheet.getRow(row.getSwrite() - 1);
		}
		ArrayList<Columnx> columns = row.getColumns();
		for (Columnx column : columns) {
			// 创建第一列
			titleRow.createCell(column.getIndex()).setCellValue(column.getName());
		}
	}

}
