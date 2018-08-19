package com.reports.exports.servicehandle;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.reports.exports.xmlhandle.Report;

/**
 * 报表Service服务类
 */
public class ReportMakeService {
	private static final Logger logger = LoggerFactory.getLogger(ReportMakeService.class);
	private Report report = null;
	private IReportMaker reportMaker = null;
	private String fileType = "";

	/**
	 * 实例化报表服务
	 * 
	 * @param report
	 *            报表节点
	 * @param fileType
	 *            报表类型
	 */
	public ReportMakeService(Report report, String fileType) {
		this.report = report;
		this.fileType = fileType;
		reportMaker = ReportMakerFactory.createInstance(fileType);
	}

	/**
	 * 写报表
	 * 
	 * @param saveFileDir
	 *            报表文件保存目录
	 * @param templateDir
	 *            报表模板存放目录
	 * @param dataMap
	 *            数据map对象
	 * @param paramsList
	 *            参数列表
	 * @param isDateSuffix
	 *            文件名是否加时间戳后缀标识
	 * @return
	 */
	public <T> String writeReport(String saveFileDir, String templateDir, Map<String, List<T>> dataMap, List<String> paramsList, boolean isDateSuffix) {
		try {
			String reportFileName = reportMaker.writeReport(report, fileType, saveFileDir, templateDir, dataMap, paramsList, isDateSuffix);
			return reportFileName;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return null;
	}

}
