package com.reports.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONArray;
import com.reports.common.Constants;
import com.reports.imports.servicehandle.ImportReaderService;
import com.reports.imports.xmlhandle.Columnx;
import com.reports.imports.xmlhandle.Import;
import com.reports.imports.xmlhandle.ImportsContext;
import com.reports.imports.xmlhandle.Rowx;
import com.reports.imports.xmlhandle.Sheetx;

/**
 * excel报表导入测试类
 * @author zhouxiaoyun
 *
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ExcelImportReportTest {

	@Autowired
	private ImportsContext importsContext;

	/**
	 * 测试方法执行前的操作，比如初始化数据等
	 */
	@Before
	public void before() {
		System.out.println("测试开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}

	/**
	 * 测试方法执行后的操作，比如释放连接等
	 */
	@After
	public void after() {
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<测试完成");
	}

	/**
	 * 测试import-config.xml配置的报表节点信息
	 */
	@Test
	public void testImportReportXMl() {
		String reprotName = "部门月度销售统计";
		Import importx = importsContext.getImport(reprotName); // 获取IMport
		System.out.println("==============解析报表：<<  " + reprotName + "  >>配置的xml信息如下 ：===============");
		System.out.println("报表标识：" + importx.getName() + "  ,报表模板名称" + importx.getTemplate());
		ArrayList<Sheetx> sheets = importx.getSheets();
		System.out.println("报表Sheet总数：" + sheets.size());
		for (Sheetx sheet : sheets) {
			System.out.println("-------------------------");
			System.out.println("Sheet序号：" + sheet.getIndex() + "  ,Sheet名称：" + sheet.getName());
			ArrayList<Rowx> rows = sheet.getRows();
			for (Rowx row : rows) {
				System.out.println("-------------------------");
				System.out.println("数据块序号：" + row.getIndex() + "  ,读取起始行：" + row.getSread() + "  ,读取结束行：" + row.getEread());
				ArrayList<Columnx> columns = row.getColumns();
				for (Columnx column : columns) {
					System.out.println("数据块列信息如下：");
					System.out.println("index：" + column.getIndex()+"  ,name：" + column.getName() + "  ,field：" + column.getField() + "  ,empty：" + column.getEmpty() + "  ,type：" + column.getType());
				}
			}
		}
	}
	
	/**
	 * 测试解析读取数据
	 * @throws FileNotFoundException 
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testImportReportData() throws Exception {
		File excelFile = new File("d:\\部门月度销售统计_20180808.xlsx");
		InputStream is = new FileInputStream(excelFile);
		String reprotName = "部门月度销售统计";
		Import importx = importsContext.getImport(reprotName); // 获取IMport
		ImportReaderService service = new ImportReaderService(importx, Constants.REPORT_TYPE_EXCEL_XLSX);
		Map<String, Object> result = service.readImportData(is);
		//若解析出错
		if (result.containsKey(Constants.IMPORT_RESULT_ERROR_CODE)) {
			System.out.println("-------解析出错-------"  + result.get(Constants.IMPORT_RESULT_ERROR_CODE));
		} else {
			Map<String, Object> map = (Map<String, Object>)result.get(Constants.IMPORT_RESULT_SUCCESS_CODE);
		//	System.out.println("-------解析成功-------"  + map);
			System.out.println("-------解析成功，解析后的数据块如下");
			System.out.println("-------月度销售报表明细<8月销售明细>-------");
			JSONArray row01_data = (JSONArray)map.get("01");
			for (int i = 0; i < row01_data.size(); i++) {
				System.out.println(row01_data.get(i));
			}
			System.out.println("-------部门排名-------");
			JSONArray row11_data = (JSONArray)map.get("11");
			for (int i = 0; i < row11_data.size(); i++) {
				System.out.println(row11_data.get(i));
			}
			System.out.println("-------个人排名-------");
			JSONArray row12_data = (JSONArray)map.get("12");
			for (int i = 0; i < row12_data.size(); i++) {
				System.out.println(row12_data.get(i));
			}
			System.out.println("-------奖金+提成-------");
			JSONArray row13_data = (JSONArray)map.get("13");
			for (int i = 0; i < row13_data.size(); i++) {
				System.out.println(row13_data.get(i));
			}
		}
		is.close();
	}

}
