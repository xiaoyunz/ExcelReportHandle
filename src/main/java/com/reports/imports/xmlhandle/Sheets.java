package com.reports.imports.xmlhandle;

import java.io.Serializable;
import java.util.ArrayList;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class Sheets implements Serializable {
	
	private static final long serialVersionUID = 3357385687679099601L;
	
	@XStreamImplicit
	private ArrayList<Sheetx> sheets;

	public ArrayList<Sheetx> getSheets() {
		return sheets;
	}

	public void setSheets(ArrayList<Sheetx> sheets) {
		this.sheets = sheets;
	}
	
}
