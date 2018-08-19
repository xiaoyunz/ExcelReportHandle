package com.reports.exports.xmlhandle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import com.thoughtworks.xstream.XStream;

/**
 * 读取xml文件，解析为对象
 */
public class ReadReportsXml {

	public static Reports readReportsXml(String filePath) throws FileNotFoundException {

		if (filePath != null && filePath.trim().length() > 0) {

			XStream xm = new XStream();
			xm.processAnnotations(Reports.class);

			Reports reports = (Reports) xm.fromXML(new InputStreamReader(new FileInputStream(filePath),Charset.forName("UTF-8")));
			return reports;
		}
		return null;
	}

}
