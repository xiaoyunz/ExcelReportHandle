package com.reports.entity;

import java.math.BigDecimal;

/**
 * 销售统计实体
 * @author zhouxiaoyun
 *
 */
public class SaleStatisticsEntity {
	
	private String name;  //姓名
	private String dep_no;  //部门
	private BigDecimal sales_amount_total;  //总销售金额
	private BigDecimal complete_rate;  //完成率
	private int ranking;  //排名
	
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
	public BigDecimal getSales_amount_total() {
		return sales_amount_total;
	}
	public void setSales_amount_total(BigDecimal sales_amount_total) {
		this.sales_amount_total = sales_amount_total;
	}
	public BigDecimal getComplete_rate() {
		return complete_rate;
	}
	public void setComplete_rate(BigDecimal complete_rate) {
		this.complete_rate = complete_rate;
	}
	public int getRanking() {
		return ranking;
	}
	public void setRanking(int ranking) {
		this.ranking = ranking;
	}
	
}
