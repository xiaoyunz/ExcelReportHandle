package com.reports.exports.servicehandle;

import java.util.List;
import java.util.Map;
import com.reports.exports.xmlhandle.Report;

/**
 * 报表生成接口类
 */
public interface IReportMaker {

	/**
	 * 写报表
	 * 
	 * @param report
	 *            report.xml中定义的报表节点
	 * @param saveFileDir
	 *            保存目录
	 * @param templateDir
	 *            模板文件存放目录
	 * @param dataMap
	 *            数据map对象
	 * @param paramsList
	 *            参数列表
	 * @param isDateSuffix
	 *            是否需要时间戳标识
	 * @return 文件名称
	 * @throws Exception
	 */
	public <T> String writeReport(Report report, String fileType, String saveFileDir, String templateDir, Map<String, List<T>> dataMap, List<String> paramsList,
			boolean isDateSuffix) throws Exception;

}
