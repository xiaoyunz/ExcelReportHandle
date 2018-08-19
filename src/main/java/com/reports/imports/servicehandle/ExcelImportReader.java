package com.reports.imports.servicehandle;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.reports.common.Constants;
import com.reports.common.ImportUtil;
import com.reports.imports.xmlhandle.Columnx;
import com.reports.imports.xmlhandle.Import;
import com.reports.imports.xmlhandle.Rowx;
import com.reports.imports.xmlhandle.Sheetx;

/**
 * 报表读取实现类，兼容xls，xlsx两种格式
 */
public class ExcelImportReader implements ImportReader {
	private static final Logger logger = LoggerFactory.getLogger(ExcelImportReader.class);
	private Workbook workbook = null;
	private Sheet sheet = null;
	private Row row = null;
	private Cell cell = null;

	/**
	 * 读取报表数据
	 */
	@Override
	public Map<String, Object> readImportData(Import importx, InputStream is) {
		Map<String, Object> resultData = new HashMap<String, Object>();
		Map<String, Object> importData = new HashMap<String, Object>();
		StringBuffer errors = new StringBuffer();
		try {
		    workbook = WorkbookFactory.create(is);
			ArrayList<Sheetx> sheets = ImportUtil.getSheets(importx);
			for (Sheetx sheetx : sheets) {
				int sheetIndex = sheetx.getIndex();
				String sName = sheetx.getName();
				sheet = workbook.getSheetAt(sheetIndex);
				int lastRowNum = sheet.getLastRowNum();
				logger.info("----------数据总行数----------    sheet：" + sName + "总行数：" + lastRowNum);
				ArrayList<Rowx> rows = ImportUtil.getRows(sheetx);
				for (Rowx rowx : rows) {
					int rowIndex = rowx.getIndex();
					JSONArray jsonArray = new JSONArray();
					int startRow = rowx.getSread();
					int endRow = rowx.getEread() >= 1 ? rowx.getEread() : lastRowNum;
					logger.info("----------读取数据块----------   sheet index：" + sheetIndex + "  row index：" + rowIndex + "  sread：" + startRow + "  eread："
							+ endRow);
					for (int i = startRow; i <= endRow; i++) {
						row = sheet.getRow(i);
						JSONObject jsonObject = new JSONObject();
						ArrayList<Columnx> columns = ImportUtil.getColumns(rowx);
						for (Columnx column : columns) {
							String value;
							boolean isMerge = ImportUtil.isMergedRegion(sheet, i, column.getIndex());
							if (isMerge) {
								value = ImportUtil.getMergedRegionValue(sheet, column.getType(), i, column.getIndex());
							} else {
								cell = row.getCell(column.getIndex());
								value = ImportUtil.getCellValue(column.getType(), cell);
							}
							
							// 校验读取的数据是否符合要求,若不符合，记录错误信息
							String error = ImportUtil.checkCellValue(sName, i + 1, column, value);
							errors.append(error);

							//去掉空格
							value = StringUtils.isNotBlank(value) ? value.trim() : "";
							// json格式存放字段数据
							jsonObject.put(column.getField(), value);
						}
						//获取excel行号
						jsonObject.put("excel_row_num", row.getRowNum());
						//把一行数据添加到json中
						jsonArray.add(jsonObject);
					}
					// 解析成功后的数据
					importData.put(sheetIndex + "" + rowIndex, jsonArray);
				}
			}
			// 如果解析完成后，有错误信息，返回错误信息，若无，返回解析的数据
			if (StringUtils.isNotBlank(errors.toString())) {
				resultData.put(Constants.IMPORT_RESULT_ERROR_CODE, errors.toString());
			} else {
				resultData.put(Constants.IMPORT_RESULT_SUCCESS_CODE, importData);
			}
			return resultData;
		} catch (Exception e) {
			e.printStackTrace();
			resultData.put(Constants.IMPORT_RESULT_ERROR_CODE, Constants.IMPORT_RESULT_ERROR_NAME);
			return resultData;
		} finally {
			try {
				// 关闭流
				workbook.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
