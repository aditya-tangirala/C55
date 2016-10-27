package org.asu.ss.model;

import javax.persistence.Entity;

@Entity
public class CreditCardUpdateLimit {
	
	private long ccno;
	private double newlimit;
	public long getCcno() {
		return ccno;
	}
	public void setCcno(long ccno) {
		this.ccno = ccno;
	}
	
	public CreditCardUpdateLimit(long ccno, double newlimit) {
		super();
		this.ccno = ccno;
		this.newlimit = newlimit;
	}
	public double getNewlimit() {
		return newlimit;
	}
	public void setNewlimit(double newlimit) {
		this.newlimit = newlimit;
	}
	public CreditCardUpdateLimit() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "CreditCardUpdateLimit [ccno=" + ccno + ", newlimit=" + newlimit + "]";
	}

	
}
