package com.reports.common;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.reports.exports.xmlhandle.Columnx;
import com.reports.exports.xmlhandle.Report;
import com.reports.exports.xmlhandle.Rowx;
import com.reports.exports.xmlhandle.Sheetx;


/**
 * 报表导出工具类
 */
public class ExportUtil {
	private static final Logger logger = LoggerFactory.getLogger(ExportUtil.class);

	/**
	 * 创建文件
	 * 
	 * @param fileName
	 *            文件名称
	 * @param fileType
	 *            文件类型
	 * @param paramsList
	 *            参数列表
	 * @param saveFileDir
	 *            保存目录
	 * @param isDateSuffix
	 *            是否加时间戳后缀
	 * @return 文件
	 * @throws IOException
	 */
	public static File createFile(String fileName, String fileType, List<String> paramsList, String saveFileDir, boolean isDateSuffix) throws IOException {
		String tempFileName = "";
		tempFileName = ExportUtil.replaceParams(fileName, paramsList);
		if (isDateSuffix) {
			tempFileName = tempFileName + "_" + DateUtil.formatDate(new Date(), "yyyyMMddHHmmssSSS");
		}
		fileName = saveFileDir + File.separator + tempFileName + fileType;
		File file = new File(fileName);
		if (file.exists()) {
			file.delete();
		}
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		file.createNewFile();
		return file;
	}

	/**
	 * 获取报表的sheet列表
	 * 
	 * @param report
	 *            report.xml中定义的报表节点
	 * @return sheet列表
	 */
	public static ArrayList<Sheetx> getSheets(Report report) {
		return report.getSheets();
	}

	/**
	 * 获取报表的Row列表
	 * 
	 * @param sheet
	 *            report.xml报表中的sheet节点
	 * @return Row列表
	 */
	public static ArrayList<Rowx> getRows(Sheetx sheet) {
		return sheet.getRows();
	}

	/**
	 * 获取报表的Column列表
	 * 
	 * @param row
	 *            report.xml报表中的row节点
	 * @return Column列表
	 */
	public static ArrayList<Columnx> getColumns(Rowx row) {
		return row.getColumns();
	}

	/**
	 * 替换参数
	 * 
	 * @param resouce
	 *            源字符串
	 * @param paramsList
	 *            参数列表
	 * @return 替换后的字符串
	 */
	public static String replaceParams(String resouce, List<String> paramsList) {
		for (int i = 0; i < paramsList.size(); i++) {
			resouce = resouce.replace("p{" + (i + 1) + "}", paramsList.get(i));
		}
		return resouce;
	}

	/**
	 * 反射获取指定字段的值
	 * 
	 * @param fieldName
	 *            字段名
	 * @param o
	 *            实体对象
	 * @return 字段值
	 */
	public static Object getFieldValueByName(String fieldName, Object o) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = o.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(o, new Object[] {});
			return value;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

}
