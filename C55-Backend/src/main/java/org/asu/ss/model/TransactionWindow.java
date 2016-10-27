package org.asu.ss.model;

import java.sql.Timestamp;

public class TransactionWindow {

	private String startDate;
	private String endDate;
	private long acc_no;
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public long getAcc_no() {
		return acc_no;
	}
	public void setAcc_no(long acc_no) {
		this.acc_no = acc_no;
	}
	@Override
	public String toString() {
		return "TransactionWindow [startDate=" + startDate + ", endDate=" + endDate + "]";
	}
	
	
}
