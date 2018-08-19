package com.reports.imports.xmlhandle;

import java.io.Serializable;
import java.util.ArrayList;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("sheet")
public class Sheetx implements Serializable {
	
	private static final long serialVersionUID = 2011894695366624695L;

	@XStreamAsAttribute
	private int index;
	
	@XStreamAsAttribute
	private String name;
	
	@XStreamImplicit
	private ArrayList<Rowx> rows;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Rowx> getRows() {
		return rows;
	}

	public void setRows(ArrayList<Rowx> rows) {
		this.rows = rows;
	}
	
}
