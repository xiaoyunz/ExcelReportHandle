package com.reports.common;

public class Constants {
	
	/**
	 * 报表类型为  excel03
	 */
	public static final String REPORT_TYPE_EXCEL_XLS = ".xls";
	
	/**
	 * 报表类型为  excel07+
	 */
	public static final String REPORT_TYPE_EXCEL_XLSX = ".xlsx";
	
	/**
	 * 报表类型为 csv
	 */
	public static final String REPORT_TYPE_CSV = ".csv";
	
	/**
	 * 业务名称，作为报表业务目录名，导出文件按业务目录存储
	 */
	public static final String REPORT_EXPORT_BUSINESS_NAME = "sales_report";

	/**
	 * csv分批下载最大数据量，默认为20000。
	 */
	public static final long REPORT_EXPORTPAGEROWS_MAX_RECORDS = 20000;

	/**
	 * csv允许的最大下载量（execl sheet最大量），默认1048576。
	 */
	public static final long REPORT_EXPORTTOTAL_MAX_RECORDS = 1048576;
	
	/**
	 * import时，解析时成功，返回的成功码
	 */
	public static final String IMPORT_RESULT_SUCCESS_CODE = "200";
	
	/**
	 * import时，解析时出错后，返回的错误码
	 */
	public static final String IMPORT_RESULT_ERROR_CODE = "201";
	
	/**
	 * import时，解析时异常，返回异常提示
	 */
	public static final String IMPORT_RESULT_ERROR_NAME = "解析过程出现异常，请联系管理员";
	
	/**
	 * import时，错误信息的分隔符
	 */
	public static final String IMPORT_ERRORS_CHECK_SEPARATOR = "\r\n";
	
	/**
	 * import时，检验内容是否为空
	 */
	public static final String IMPORT_ERRORS_CHECK_EMPTY = "数据不允许为空";
	
	/**
	 * import时，检验内容是否为int类型
	 */
	public static final String IMPORT_ERRORS_CHECK_TYPE_INT = "数据必须为整型";
	
	/**
	 * import时，检验内容是否为number类型
	 */
	public static final String IMPORT_ERRORS_CHECK_TYPE_NUMBER = "数据必须为浮点类型";
	
	/**
	 * import时，检验内容是否为short date类型
	 */
	public static final String IMPORT_ERRORS_CHECK_TYPE_SDATE = "数据必须为日期类型，正确格式如：yyyy-MM-dd";
	
	/**
	 * import时，检验内容是否为long date类型
	 */
	public static final String IMPORT_ERRORS_CHECK_TYPE_LDATE = "数据必须为日期类型，正确格式如：yyyy-MM-dd HH:mm:ss";
	/**
	 * import时，检验内容是否为time类型
	 */
	public static final String IMPORT_ERRORS_CHECK_TYPE_TIME = "数据必须为日期时间类型，正确格式如：HH:mm:ss";

}
