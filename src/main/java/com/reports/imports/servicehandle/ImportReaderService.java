package com.reports.imports.servicehandle;

import java.io.InputStream;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.reports.imports.xmlhandle.Import;

/**
 * 报表导入读取服务类
 */
public class ImportReaderService {
	
	private static final Logger logger = LoggerFactory.getLogger(ImportReaderService.class);
	
	private Import importx = null;
	
	private ImportReader importReader = null;
	
	/**
	 * 实例化
	 * @param importx  import.xml中定义的报表节点
	 * @param fileType  导入的文件类型
	 */
	public ImportReaderService(Import importx, String fileType) {
		this.importx = importx;
		importReader = ImportReaderFactory.createInstance(fileType);
	}
	
	/**
	 * 读取报表数据
	 * @param is  输入流
	 * @return
	 */
	public Map<String, Object> readImportData(InputStream is) {
		return importReader.readImportData(importx, is);
	}

}
