package com.reports.exports.xmlhandle;

import java.io.Serializable;
import java.util.ArrayList;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 根节点
 */
@XStreamAlias("reports")
public class Reports implements Serializable {
	
	private static final long serialVersionUID = -8043300420346338742L;
	
	@XStreamImplicit
	private ArrayList<Report> reports;

	public ArrayList<Report> getReports() {
		return reports;
	}

	public void setReports(ArrayList<Report> reports) {
		this.reports = reports;
	}

}
