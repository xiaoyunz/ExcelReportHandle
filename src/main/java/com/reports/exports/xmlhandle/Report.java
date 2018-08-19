package com.reports.exports.xmlhandle;

import java.io.Serializable;
import java.util.ArrayList;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("report")
public class Report implements Serializable {
	
	private static final long serialVersionUID = -3453588485508687294L;

	@XStreamAsAttribute
	private String name;
	
	@XStreamAsAttribute
	private String filename;
	
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

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public ArrayList<Sheetx> getSheets() {
		return sheets;
	}

	public void setSheets(ArrayList<Sheetx> sheets) {
		this.sheets = sheets;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

}
