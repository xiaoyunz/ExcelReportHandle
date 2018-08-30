package com.reports.imports.bigdatahandle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

/**
 * POI读取excel有两种模式，一种是用户模式，一种是事件驱动模式 采用SAX事件驱动模式解决XLSX文件，可以有效解决用户模式内存溢出的问题，
 * 该模式是POI官方推荐的读取大数据的模式， 在用户模式下，数据量较大，Sheet较多，或者是有很多无用的空行的情况下，容易出现内存溢出
 *
 */
public class ExcelBigDataReader {
	
	// excel2003扩展名
	public static final String EXCEL03_EXTENSION = ".xls";
	
	// excel2007扩展名
	public static final String EXCEL07_EXTENSION = ".xlsx";

	/**
	 * 每获取一条记录，即打印
	 * 在flume里每获取一条记录即发送，而不必缓存起来，可以大大减少内存的消耗，这里主要是针对flume读取大数据量excel来说的
	 * 
	 * @param sheetName
	 * @param sheetIndex
	 * @param curRow
	 * @param cellList
	 */
	public static void sendRows(String filePath, String sheetName, int sheetIndex, int curRow, List<String> cellList) {
		StringBuffer oneLineSb = new StringBuffer();
		oneLineSb.append(filePath);
		oneLineSb.append("--");
		oneLineSb.append("sheet" + sheetIndex);
		oneLineSb.append("::" + sheetName);// 加上sheet名
		oneLineSb.append("--");
		oneLineSb.append("row::" + curRow);
		oneLineSb.append("--::");
		for (String cell : cellList) {
			oneLineSb.append(cell.trim());
			oneLineSb.append("|");
		}
		String oneLine = oneLineSb.toString();
		if (oneLine.endsWith("|")) {
			oneLine = oneLine.substring(0, oneLine.lastIndexOf("|"));
		} // 去除最后一个分隔符

		System.out.println(oneLine);
	}

	public static void readExcel(String fileName) throws Exception {
		int totalRows = 0;
		if (fileName.endsWith(EXCEL03_EXTENSION)) { // 处理excel2003文件
			ExcelXlsReader excelXls = new ExcelXlsReader();
			totalRows = excelXls.process(fileName);
		} else if (fileName.endsWith(EXCEL07_EXTENSION)) {// 处理excel2007文件
			ExcelXlsxReaderWithDefaultHandler excelXlsxReader = new ExcelXlsxReaderWithDefaultHandler();
			totalRows = excelXlsxReader.process(fileName);
		} else {
			throw new Exception("文件格式错误，fileName的扩展名只能是xls或xlsx。");
		}
		System.out.println("发送的总行数：" + totalRows);
	}

	public static void copyToTemp(File file, String tmpDir) throws Exception {
		FileInputStream fis = new FileInputStream(file);
		File file1 = new File(tmpDir);
		if (file1.exists()) {
			file1.delete();
		}
		FileOutputStream fos = new FileOutputStream(tmpDir);
		byte[] b = new byte[1024];
		int n = 0;
		while ((n = fis.read(b)) != -1) {
			fos.write(b, 0, n);
		}
		fis.close();
		fos.close();
	}

}
