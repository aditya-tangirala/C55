package org.asu.ss.model;

import javax.persistence.Entity;

@Entity
public class CreditCardTransaction {
private long cust_id;
private double t_amt;


public long getCust_id() {
	return cust_id;
}
public void setCust_id(long cust_id) {
	this.cust_id = cust_id;
}
public double getT_amt() {
	return t_amt;
}
public void setT_amt(double t_amt) {
	this.t_amt = t_amt;
}

public CreditCardTransaction(long cust_id, double t_amt) {
	super();
	this.cust_id = cust_id;
	this.t_amt = t_amt;
}
public CreditCardTransaction() {
	super();
	// TODO Auto-generated constructor stub
}
@Override
public String toString() {
	return "CreditCardTransaction [cust_id=" + cust_id + ", t_amt=" + t_amt + "]";
}



}
