package com.reports.imports.xmlhandle;

import java.io.Serializable;
import java.util.ArrayList;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 根节点
 */
@XStreamAlias("imports")
public class Imports implements Serializable {

	private static final long serialVersionUID = 4090880466890473008L;
	
	@XStreamImplicit
	private ArrayList<Import> imports;

	public ArrayList<Import> getImports() {
		return imports;
	}

	public void setImports(ArrayList<Import> imports) {
		this.imports = imports;
	}
	
}
