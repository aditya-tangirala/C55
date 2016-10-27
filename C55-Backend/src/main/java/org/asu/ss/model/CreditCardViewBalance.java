package org.asu.ss.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Credit_Card")
public class CreditCardViewBalance {
	
	@Id
	@Column(name = "cust_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long cust_id;
	
	@Column(name="interest_rate")
	private long balance;
	
	@Column(name="credit_limit")
	private double credit_limit;
	
	@Column(name="amount_current")
	private double amount_used;

	public long getCust_id() {
		return cust_id;
	}
	public void setCust_id(long cust_id) {
		this.cust_id = cust_id;
	}
	public long getBalance() {
		return balance;
	}
	public void setBalance(long balance) {
		this.balance = balance;
	}

	public CreditCardViewBalance() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CreditCardViewBalance(long cust_id, long balance, double credit_limit, double amount_used) {
		super();
		this.cust_id = cust_id;
		this.balance = balance;
		this.credit_limit = credit_limit;
		this.amount_used = amount_used;
	}
	public double getCredit_limit() {
		return credit_limit;
	}
	public void setCredit_limit(double credit_limit) {
		this.credit_limit = credit_limit;
	}
	public double getAmount_used() {
		return amount_used;
	}
	public void setAmount_used(double amount_used) {
		this.amount_used = amount_used;
	}
	@Override
	public String toString() {
		return "CreditCardViewBalance [cust_id=" + cust_id + ", balance=" + balance + ", credit_limit=" + credit_limit
				+ ", amount_used=" + amount_used + "]";
	}
	
	
}
