package com.reports.entity;

import java.math.BigDecimal;

/**
 * 销售明细实体
 * @author zhouxiaoyun
 *
 */
public class SalesDetailEntity {
	
	private int s_no;   //序号
	private String name;  //姓名
	private String dep_no;  //部门
	private BigDecimal sales_amount;  //销售金额
	private String sales_date;  //销售日期
	
	public int getS_no() {
		return s_no;
	}
	public void setS_no(int s_no) {
		this.s_no = s_no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDep_no() {
		return dep_no;
	}
	public void setDep_no(String dep_no) {
		this.dep_no = dep_no;
	}
	public BigDecimal getSales_amount() {
		return sales_amount;
	}
	public void setSales_amount(BigDecimal sales_amount) {
		this.sales_amount = sales_amount;
	}
	public String getSales_date() {
		return sales_date;
	}
	public void setSales_date(String sales_date) {
		this.sales_date = sales_date;
	}
	

}
