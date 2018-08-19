package com.reports.imports.servicehandle;

import com.reports.common.Constants;

/**
 * 导入报表工厂类
 */
public class ImportReaderFactory {
	
	public static ImportReader createInstance(String type) {
		
		if (Constants.REPORT_TYPE_EXCEL_XLS.equals(type) || Constants.REPORT_TYPE_EXCEL_XLSX.equals(type)) {
			return new ExcelImportReader();
		} else {
			throw new RuntimeException("type[" + type + "]类型不可识别，没有匹配到可实例化的对象！");
		}
	}

}
