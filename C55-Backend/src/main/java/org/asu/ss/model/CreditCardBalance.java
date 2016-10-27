package org.asu.ss.model;

import javax.persistence.Entity;

@Entity
public class CreditCardBalance {
	
	private long cust_id;
	private double balance;

	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public long getCust_id() {
		return cust_id;
	}
	public void setCust_id(long cust_id) {
		this.cust_id = cust_id;
	}
	public CreditCardBalance(long cust_id, double balance) {
		super();
		this.cust_id = cust_id;
		this.balance = balance;
	}
	public CreditCardBalance() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "CreditCardBalance [cust_id=" + cust_id + ", balance=" + balance + "]";
	}
	
	

}
