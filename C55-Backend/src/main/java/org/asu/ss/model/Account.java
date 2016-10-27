package org.asu.ss.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class Account {

	@Id
	@Column(name = "acc_no")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long acc_no;
	
	@Column(name="type") 
	private String type;
	
	@Column(name="acc_balance")
	private double acc_balance;
	
	@Column(name="creation_date")
	private Date creation_date;

	public long getAcc_no() {
		return acc_no;
	}

	public void setAcc_no(long acc_no) {
		this.acc_no = acc_no;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getAcc_balance() {
		return acc_balance;
	}

	public void setAcc_balance(double acc_balance) {
		this.acc_balance = acc_balance;
	}

	public Date getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}
	
	public Account() {
		super();
	}

	public Account(long acc_no, String type, double acc_balance, Date creation_date) {
		super();
		this.acc_no = acc_no;
		this.type = type;
		this.acc_balance = acc_balance;
		this.creation_date = creation_date;
	}
	
	/*@Override
	public String toString() {
		return "Account [acc_no=" + acc_no + ", type=" + type + ", acc_balance=" + acc_balance + ", creation_date="
				+ creation_date + "]";
	}*/


}
