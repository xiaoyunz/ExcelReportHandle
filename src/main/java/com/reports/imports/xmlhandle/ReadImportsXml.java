package com.reports.imports.xmlhandle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import com.thoughtworks.xstream.XStream;

/**
 * 读取xml文件，解析为对象
 */
public class ReadImportsXml {
	
	public static Imports readImportsXml(String filePath) throws FileNotFoundException {

		if (filePath != null && filePath.trim().length() > 0) {

			XStream xm = new XStream();
			xm.processAnnotations(Imports.class);

			Imports imports = (Imports) xm.fromXML(new InputStreamReader(new FileInputStream(filePath),Charset.forName("UTF-8")));
			return imports;
		}
		return null;
	}
}
