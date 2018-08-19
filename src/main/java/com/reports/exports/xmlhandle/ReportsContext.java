package com.reports.exports.xmlhandle;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * 初始化xml文件，存放reports配置信息
 */
@Component
public class ReportsContext implements InitializingBean {
	private static final Logger logger = LoggerFactory.getLogger(ReportsContext.class);

	private final Map<String, Report> context = new HashMap<String, Report>();

	public ReportsContext() {
		super();
	}

	public Report getReport(String rname) {
		return context.get(rname);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initContext();
	}

	public void initContext() throws Exception {
		String xmlfile = Thread.currentThread().getContextClassLoader().getResource("export-config.xml").getPath();
		xmlfile = URLDecoder.decode(xmlfile,"utf-8"); //这步是很关键的
		logger.info("-----加载export-config.xml 定义文件:-----"+xmlfile);
		Reports reports = ReadReportsXml.readReportsXml(xmlfile);
		if (null != reports && reports.getReports() != null && reports.getReports().size() > 0) {
			for (Report r : reports.getReports()) {
				String name = r.getName();
				if (context.containsKey(name)) {
					throw new Exception("reports name already exist" + r.getName());
				}
				context.put(name, r);
			}
		}
	}

}
