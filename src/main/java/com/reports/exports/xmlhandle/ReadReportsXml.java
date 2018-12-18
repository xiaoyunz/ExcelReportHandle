package com.reports.exports.xmlhandle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 读取xml文件，解析为对象
 */
public class ReadReportsXml {

	public static Reports readReportsXml(String filePath) throws FileNotFoundException {

		if (filePath != null && filePath.trim().length() > 0) {

			XStream xm = new XStream(new DomDriver());    //注意：不是new Xstream(); 否则报错：java.lang.NoClassDefFoundError: org/xmlpull/v1/XmlPullParserFactory
			xm.processAnnotations(Reports.class);         //如果是用注解的方式，这句不能少

			Reports reports = (Reports) xm.fromXML(new InputStreamReader(new FileInputStream(filePath),Charset.forName("UTF-8")));
			return reports;
		}
		return null;
	}

}
