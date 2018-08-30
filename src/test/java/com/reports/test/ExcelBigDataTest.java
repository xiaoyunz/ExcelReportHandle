package com.reports.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.reports.imports.bigdatahandle.ExcelBigDataReader;

/**
 * excel超大数据解析测试（内存占用少）
 *
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ExcelBigDataTest {

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
	 * 测试Excel大数据
	 * @throws Exception 
	 */
	@Test
	public void testExcelBigDataImport() throws Exception {
		String path = "d:\\企业名单(经纪).xlsx";

		ExcelBigDataReader.readExcel(path);
	}

}
