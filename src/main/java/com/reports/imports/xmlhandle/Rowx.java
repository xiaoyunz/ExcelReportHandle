package com.reports.imports.xmlhandle;

import java.io.Serializable;
import java.util.ArrayList;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("row")
public class Rowx implements Serializable {
	
	private static final long serialVersionUID = 8983523417138738439L;

	@XStreamAsAttribute
	private int index;
	
	@XStreamAsAttribute
	private int sread;
	
	@XStreamAsAttribute
	private int eread;
	
	@XStreamImplicit
	private ArrayList<Columnx> Columns;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getSread() {
		return sread;
	}

	public void setSread(int sread) {
		this.sread = sread;
	}

	public int getEread() {
		return eread;
	}

	public void setEread(int eread) {
		this.eread = eread;
	}

	public ArrayList<Columnx> getColumns() {
		return Columns;
	}

	public void setColumns(ArrayList<Columnx> columns) {
		Columns = columns;
	}
	
}
