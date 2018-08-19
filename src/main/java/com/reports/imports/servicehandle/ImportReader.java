package com.reports.imports.servicehandle;

import java.io.InputStream;
import java.util.Map;
import com.reports.imports.xmlhandle.Import;

/**
 * 报表导入读取接口类
 */
public interface ImportReader {
	
	/**
	 * 读取导入报表数据
	 * @param importx  import-config.xml中定义的报表节点
	 * @param is 报表输入流
	 * @return
	 */
	public Map<String,Object> readImportData(Import importx, InputStream is);

}
