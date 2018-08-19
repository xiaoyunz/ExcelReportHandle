package com.reports.exports.xmlhandle;

import java.io.Serializable;
import java.util.ArrayList;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("row")
public class Rowx implements Serializable {

	private static final long serialVersionUID = -8280581529534298767L;
	
	@XStreamAsAttribute
	private int index;
	
	@XStreamAsAttribute
	private int swrite;
	
	@XStreamAsAttribute
	private String datakey;
	
	@XStreamImplicit
	private ArrayList<Columnx> Columns;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getSwrite() {
		return swrite;
	}

	public void setSwrite(int swrite) {
		this.swrite = swrite;
	}

	public ArrayList<Columnx> getColumns() {
		return Columns;
	}

	public void setColumns(ArrayList<Columnx> columns) {
		Columns = columns;
	}

	public String getDatakey() {
		return datakey;
	}

	public void setDatakey(String datakey) {
		this.datakey = datakey;
	}

}
