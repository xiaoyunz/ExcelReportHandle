package com.reports.imports.xmlhandle;

import java.io.Serializable;
import java.util.ArrayList;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("import")
public class Import implements Serializable {
	
	private static final long serialVersionUID = 6331809447615356103L;

	@XStreamAsAttribute
	private String name;
	
	@XStreamAlias("template")
	private String template;
	
	@XStreamImplicit
	private ArrayList<Sheetx> sheets;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public ArrayList<Sheetx> getSheets() {
		return sheets;
	}

	public void setSheets(ArrayList<Sheetx> sheets) {
		this.sheets = sheets;
	}
	
}
