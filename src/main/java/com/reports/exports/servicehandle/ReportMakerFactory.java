package com.reports.exports.servicehandle;

import com.reports.common.Constants;

/**
 * 报表工厂类
 */
public class ReportMakerFactory {

	/**
	 * 实例化报表类型
	 * 
	 * @param type
	 *            报表类型
	 * @return
	 */
	public static IReportMaker createInstance(String type) {
		if (Constants.REPORT_TYPE_EXCEL_XLS.equals(type) || Constants.REPORT_TYPE_EXCEL_XLSX.equals(type)) {
			return new XlsxReportMaker();
		} else if (Constants.REPORT_TYPE_CSV.equals(type)) {
			return new CsvReportMaker();
		} else {
			throw new RuntimeException("type[" + type + "]类型不可识别，没有匹配到可实例化的对象！");
		}
	}
}
