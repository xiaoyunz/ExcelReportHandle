package com.reports.imports.xmlhandle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import com.reports.exports.xmlhandle.Reports;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 读取xml文件，解析为对象
 */
public class ReadImportsXml {
	
	public static Imports readImportsXml(String filePath) throws FileNotFoundException {

		if (filePath != null && filePath.trim().length() > 0) {

			XStream xm = new XStream(new DomDriver());    //注意：不是new Xstream(); 否则报错：java.lang.NoClassDefFoundError: org/xmlpull/v1/XmlPullParserFactory
			xm.processAnnotations(Imports.class);         //如果是用注解的方式，这句不能少

			Imports imports = (Imports) xm.fromXML(new InputStreamReader(new FileInputStream(filePath),Charset.forName("UTF-8")));
			return imports;
		}
		return null;
	}
}
