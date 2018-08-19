package com.reports.exports.xmlhandle;

import java.io.Serializable;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("column")
public class Columnx implements Serializable {
	
	private static final long serialVersionUID = 3328941849902555168L;

	@XStreamAsAttribute
	private String field;
	
	@XStreamAsAttribute
	private String name;
	
	@XStreamAsAttribute
	private boolean hidden;
	
	@XStreamAsAttribute
	private String formatter;
	
	@XStreamAsAttribute
	private int index;

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

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public String getFormatter() {
		return formatter;
	}

	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
