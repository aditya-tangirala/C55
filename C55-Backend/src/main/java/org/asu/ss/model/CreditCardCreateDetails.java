package org.asu.ss.model;

import javax.persistence.Entity;

@Entity
public class CreditCardCreateDetails {

	private long acc_no;
	private long cust_id;
	
	public long getCust_id() {
		return cust_id;
	}
	public void setCust_id(long cust_id) {
		this.cust_id = cust_id;
	}
	public long getAcc_no() {
		return acc_no;
	}
	public void setAcc_no(long acc_no) {
		this.acc_no = acc_no;
	}
	public CreditCardCreateDetails(long acc_no, long cust_id) {
		super();
		this.acc_no = acc_no;
		this.cust_id = cust_id;
	}
	public CreditCardCreateDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "CreditCardCreateDetails [acc_no=" + acc_no + ", cust_id=" + cust_id + "]";
	}

	
}
