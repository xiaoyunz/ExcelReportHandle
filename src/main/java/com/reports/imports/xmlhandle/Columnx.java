package com.reports.imports.xmlhandle;

import java.io.Serializable;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("column")
public class Columnx implements Serializable {
	
	private static final long serialVersionUID = 2038410269074747137L;

	@XStreamAsAttribute
	private String field;
	
	@XStreamAsAttribute
	private String name;
	
	@XStreamAsAttribute
	private String type;
	
	@XStreamAsAttribute
	private int index;
	
	@XStreamAsAttribute
	private String empty = "true";

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getEmpty() {
		return empty;
	}

	public void setEmpty(String empty) {
		this.empty = empty;
	}
	
}
