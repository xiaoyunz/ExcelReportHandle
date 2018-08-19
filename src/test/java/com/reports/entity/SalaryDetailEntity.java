package com.reports.entity;

import java.math.BigDecimal;

/**
 * 工资实体
 * @author zhouxiaoyun
 *
 */
public class SalaryDetailEntity {
	
	private String name;  //姓名
	private BigDecimal basic_salary;  //基本工资
	private BigDecimal sales_salary;  //提成工资
	private BigDecimal bonus_salary;  //奖金
	private BigDecimal  salary_total;  //总计

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getBasic_salary() {
		return basic_salary;
	}

	public void setBasic_salary(BigDecimal basic_salary) {
		this.basic_salary = basic_salary;
	}

	public BigDecimal getSales_salary() {
		return sales_salary;
	}

	public void setSales_salary(BigDecimal sales_salary) {
		this.sales_salary = sales_salary;
	}

	public BigDecimal getBonus_salary() {
		return bonus_salary;
	}

	public void setBonus_salary(BigDecimal bonus_salary) {
		this.bonus_salary = bonus_salary;
	}

	public BigDecimal getSalary_total() {
		return salary_total;
	}

	public void setSalary_total(BigDecimal salary_total) {
		this.salary_total = salary_total;
	}
	
	
	

}
