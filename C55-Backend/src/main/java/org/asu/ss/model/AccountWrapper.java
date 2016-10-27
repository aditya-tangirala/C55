package org.asu.ss.model;

import javax.persistence.Entity;

@Entity
public class AccountWrapper {

	private long cust_id;
	
	private double acc_balance;
	
	private String type;

	public long getCust_id() {
		return cust_id;
	}

	public void setCust_id(long cust_id) {
		this.cust_id = cust_id;
	}

	public double getAcc_balance() {
		return acc_balance;
	}

	public void setAcc_balance(double acc_balance) {
		this.acc_balance = acc_balance;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "AccountWrapper [cust_id=" + cust_id + ", acc_balance=" + acc_balance + ", type=" + type + "]";
	}
	
}
