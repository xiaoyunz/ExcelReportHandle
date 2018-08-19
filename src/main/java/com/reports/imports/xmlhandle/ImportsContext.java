package com.reports.imports.xmlhandle;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * 初始化xml文件，存放imports配置信息
 */
@Component
public class ImportsContext implements InitializingBean {
	
	private static final Logger logger = LoggerFactory.getLogger(ImportsContext.class);

	private final Map<String, Import> context = new HashMap<String, Import>();

	public ImportsContext() {
		super();
	}

	public Import getImport(String name) {
		return context.get(name);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initContext();
	}

	public void initContext() throws Exception {
		String xmlfile = Thread.currentThread().getContextClassLoader().getResource("import-config.xml").getPath();
		xmlfile = URLDecoder.decode(xmlfile,"utf-8"); //这步是很关键的
		logger.info("-----加载import-config.xml 定义文件:-----"+xmlfile);
		Imports imports = ReadImportsXml.readImportsXml(xmlfile);
		if (null != imports && imports.getImports() != null && imports.getImports().size() > 0) {
			for (Import i : imports.getImports()) {
				String name = i.getName();
				if (context.containsKey(name)) {
					throw new Exception("imports name already exist" + i.getName());
				}
				context.put(name, i);
			}
		}
	}

}
