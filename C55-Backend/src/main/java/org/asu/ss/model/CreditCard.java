package org.asu.ss.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "credit_card")
public class CreditCard {
	
	@Id
	@Column(name = "acc_no")
	private long acc_no;

	@Column(name = "cust_id")
	private long cust_id;
	
	public long getCust_id() {
		return cust_id;
	}

	public void setCust_id(long cust_id) {
		this.cust_id = cust_id;
	}

	@Column(name="card_no")
	private long card_no;
	
	@Column(name="cvv")
	private int cvv;
	
	@Column(name="issue_date")
	private Date issue_date;
	
	@Column(name="expiry_date")
	private Date expiry_date;
	
	@Column(name="card_name")
	private String card_name;
	
	@Column(name="card_type")
	private String cardtype;
	
	@Column(name="credit_limit")
	private double credit_limit;
	
	@Column(name="amount_aggregte")
	private double amount_used;
	
	@Column(name="interest_rate")
	private float interest_rate;
	
	@Column(name="amount_current")
	private double amount_spent;

	public long getAcc_no() {
		return acc_no;
	}

	public void setAcc_no(long acc_no) {
		this.acc_no = acc_no;
	}

	public long getCard_no() {
		return card_no;
	}

	public void setCard_no(long card_no) {
		this.card_no = card_no;
	}

	public int getCvv() {
		return cvv;
	}

	public void setCvv(int cvv) {
		this.cvv = cvv;
	}

	public Date getIssue_date() {
		return issue_date;
	}

	public void setIssue_date(Date issue_date) {
		this.issue_date = issue_date;
	}

	public Date getExpiry_date() {
		return expiry_date;
	}

	public void setExpiry_date(Date expiry_date) {
		this.expiry_date = expiry_date;
	}

	public String getCard_name() {
		return card_name;
	}

	public void setCard_name(String card_name) {
		this.card_name = card_name;
	}

	public String getCardtype() {
		return cardtype;
	}

	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
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

	public float getInterest_rate() {
		return interest_rate;
	}

	public void setInterest_rate(float interest_rate) {
		this.interest_rate = interest_rate;
	}

	public double getAmount_spent() {
		return amount_spent;
	}

	public void setAmount_spent(double amount_spent) {
		this.amount_spent = amount_spent;
	}
	
	

	public CreditCard() {
		super();
	}

	public CreditCard(long acc_no, long cust_id, long card_no, int cvv, Date issue_date, Date expiry_date,
			String card_name, String cardtype, double credit_limit, double amount_used, float interest_rate,
			double amount_spent) {
		super();
		this.acc_no = acc_no;
		this.cust_id = cust_id;
		this.card_no = card_no;
		this.cvv = cvv;
		this.issue_date = issue_date;
		this.expiry_date = expiry_date;
		this.card_name = card_name;
		this.cardtype = cardtype;
		this.credit_limit = credit_limit;
		this.amount_used = amount_used;
		this.interest_rate = interest_rate;
		this.amount_spent = amount_spent;
	}

	/*@Override
	public String toString() {
		return "CreditCard [acc_no=" + acc_no + ", cust_id=" + cust_id + ", card_no=" + card_no + ", cvv=" + cvv
				+ ", issue_date=" + issue_date + ", expiry_date=" + expiry_date + ", card_name=" + card_name
				+ ", cardtype=" + cardtype + ", credit_limit=" + credit_limit + ", amount_used=" + amount_used
				+ ", interest_rate=" + interest_rate + ", amount_spent=" + amount_spent + "]";
	}*/
	
	

}
