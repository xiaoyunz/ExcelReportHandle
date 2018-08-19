package com.reports.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.reports.common.Constants;
import com.reports.common.DateUtil;
import com.reports.entity.SalaryDetailEntity;
import com.reports.entity.SaleStatisticsEntity;
import com.reports.entity.SalesDetailEntity;
import com.reports.exports.servicehandle.ReportMakeService;
import com.reports.exports.xmlhandle.Columnx;
import com.reports.exports.xmlhandle.Report;
import com.reports.exports.xmlhandle.ReportsContext;
import com.reports.exports.xmlhandle.Rowx;
import com.reports.exports.xmlhandle.Sheetx;

/**
 * excel报表导出测试类
 * @author zhouxiaoyun
 *
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ExcelExportReportTest {

	@Autowired
	private ReportsContext reportsContext;

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
	 * 测试export-config.xml配置的报表节点信息
	 */
	@Test
	public void testExportReportXMl() {
		String reprotName = "部门月度销售统计";
		Report report = reportsContext.getReport(reprotName);
		System.out.println("==============解析报表：<<  " + reprotName + "  >>配置的xml信息如下 ：===============");
		System.out.println("报表标识：" + report.getName() + "  ,报表模板名称" + report.getTemplate());
		ArrayList<Sheetx> sheets = report.getSheets();
		System.out.println("报表Sheet总数：" + sheets.size());
		for (Sheetx sheet : sheets) {
			System.out.println("-------------------------");
			System.out.println("Sheet序号：" + sheet.getIndex() + "  ,Sheet名称：" + sheet.getName());
			ArrayList<Rowx> rows = sheet.getRows();
			for (Rowx row : rows) {
				System.out.println("-------------------------");
				System.out.println(
						"数据块序号：" + row.getIndex() + "  ,写入起始行：" + row.getSwrite() + "  ,数据key：" + row.getDatakey());
				ArrayList<Columnx> columns = row.getColumns();
				for (Columnx column : columns) {
					System.out.println("数据块列信息如下：");
					System.out.println("index：" + column.getIndex() + "  ,name：" + column.getName() + "  ,field："
							+ column.getField() + "  ,formatter：" + column.getFormatter());
				}
			}
		}
	}

	/**
	 * 测试写入数据
	 * 
	 * @throws FileNotFoundException
	 */
	@SuppressWarnings({ "unchecked", "hiding" })
	@Test
	public <T> void testImportReportData() throws Exception {
		String excel_save_path = "d:\\template"; // excel生成报表保存路径
		String excel_template_path = "d:\\template"; // excel报表模板路
		Report report = reportsContext.getReport("部门月度销售统计"); // 获取report
		
		//准备测试数据
		Map<String,List<T>> dataMap = new HashMap<String,List<T>>();
		List<SalesDetailEntity> list1 = new ArrayList<SalesDetailEntity>();
		List<SaleStatisticsEntity> list2 = new ArrayList<SaleStatisticsEntity>();
		List<SalaryDetailEntity> list3 = new ArrayList<SalaryDetailEntity>();
		for (int i=0;i<3;i++) {
			SalesDetailEntity salesDetailEntity = new SalesDetailEntity();
			salesDetailEntity.setS_no(i);
			salesDetailEntity.setName("测试"+i);
			salesDetailEntity.setDep_no("测试"+i);
			salesDetailEntity.setSales_amount(new BigDecimal(100.99 * i));
			salesDetailEntity.setSales_date("2018-08-08");
			list1.add(salesDetailEntity);
		}
		for (int i=0;i<3;i++) {
			SaleStatisticsEntity saleStatisticsEntity = new SaleStatisticsEntity();
			saleStatisticsEntity.setName("测试"+i);
			saleStatisticsEntity.setDep_no("测试"+i);
			saleStatisticsEntity.setSales_amount_total(new BigDecimal(100.99 * i));
			saleStatisticsEntity.setComplete_rate(new BigDecimal(10.45 * i));
			saleStatisticsEntity.setRanking(i);
			list2.add(saleStatisticsEntity);
		}
		for (int i=0;i<3;i++) {
			SalaryDetailEntity salaryDetailEntity = new SalaryDetailEntity();
			salaryDetailEntity.setName("测试"+i);
			salaryDetailEntity.setBasic_salary(new BigDecimal(10 * i));
			salaryDetailEntity.setSales_salary(new BigDecimal(20 * i));
			salaryDetailEntity.setBonus_salary(new BigDecimal(30 * i));
			salaryDetailEntity.setSalary_total(new BigDecimal(40 * i));
			list3.add(salaryDetailEntity);
		}
		dataMap.put("0", (List<T>) list1);
		dataMap.put("1", (List<T>) list2);  //可以共用数据源datakey
		dataMap.put("2", (List<T>) list3);

		//文件保存路径
		String saveFileDir = excel_save_path + File.separator + Constants.REPORT_EXPORT_BUSINESS_NAME + File.separator
				+ DateUtil.formatDate(new Date(), "yyyyMMdd");

		// 报表中的参数p{1},p{2},按顺序添加参数
		List<String> paramsList = new ArrayList<>();
		paramsList.add("20180809");
		
		//有模板测试
		ReportMakeService reportMakeService = new ReportMakeService(report, Constants.REPORT_TYPE_EXCEL_XLSX);
		String reportFileName = reportMakeService.writeReport(saveFileDir, excel_template_path, dataMap, paramsList, true);
		if (StringUtils.isNotBlank(reportFileName)) {
			System.out.println("-----报表生成成功，保存路径为：" + reportFileName);
		} else {
			System.out.println("-----报表生成失败");
		}

	}

}
