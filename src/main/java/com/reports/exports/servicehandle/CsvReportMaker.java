package com.reports.exports.servicehandle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.reports.common.ExportUtil;
import com.reports.exports.xmlhandle.Columnx;
import com.reports.exports.xmlhandle.Report;
import com.reports.exports.xmlhandle.Rowx;
import com.reports.exports.xmlhandle.Sheetx;


/**
 * csv报表生成类
 */
public class CsvReportMaker implements IReportMaker {
	private static final Logger logger = LoggerFactory.getLogger(CsvReportMaker.class);
	//文件类型
	private String fileType = "";
	// 报表文件保存目录
	private String saveFileDir = "";
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
		this.isDateSuffix = isDateSuffix;
		return this.writeData(report, dataMap, paramsList);
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
		File file = null;
		BufferedWriter fileOutputStream = null;
		try {
			//创建csv文件
			file = ExportUtil.createFile(report.getFilename(), fileType, paramsList, saveFileDir, isDateSuffix);
			// 实例化文件输出流
			fileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "GBK"), 4096);
			ArrayList<Sheetx> sheets = ExportUtil.getSheets(report);
			for (Sheetx sheet : sheets) {
				ArrayList<Rowx> rows = ExportUtil.getRows(sheet);
				for (Rowx row : rows) {
					List<T> rowData = dataMap.get(row.getDatakey());
					ArrayList<Columnx> columns = ExportUtil.getColumns(row);
					// 写头
					for (int i = 0; i < columns.size(); i++) {
						// 拼接所有字段为一行数据
						if (i < columns.size() - 1) { // 不是最后一个元素
							fileOutputStream.write("\"" + columns.get(i).getName() + "\"" + ",");
						} else { // 是最后一个元素
							fileOutputStream.write("\"" + columns.get(i).getName() + "\"");
						}
					}
					// 换行
					fileOutputStream.newLine();

					// 写数据
					for (int i = 0; i < rowData.size(); i++) {
						for (int j = 0; j < columns.size(); j++) {
							Object cellVal = ExportUtil.getFieldValueByName(columns.get(j).getField(), rowData.get(i));
							// 拼接所有字段为一行数据
							if (j < columns.size() - 1) { // 不是最后一个元素
								fileOutputStream.write("\"" + cellVal.toString() + "\"" + ",");
							} else { // 是最后一个元素
								fileOutputStream.write("\"" + cellVal.toString() + "\"");
							}
						}
						// 换行
						fileOutputStream.newLine();
					}
				}
				// 刷新缓存
				fileOutputStream.flush();
			}
			return file.toString();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			try {
				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
