package org.asu.ss.model;

public class QuickPay {

	private long from_acc;
	private long to_acc;
	private long t_id;
	private double t_amount;
	private String email = null;
	private long mobile = 0;
	private String remarks;
	
	public long getFromAcc()
	{
		return this.from_acc;
	}
	
	public long getToAcc()
	{
		return this.to_acc;
	}
	
	public long getTID()
	{
		return this.t_id;
	}
	
	public double getTAmount()
	{
		return this.t_amount;
	}
	
	public String getEmail()
	{
		return this.email;
	}
	
	public long getMobile()
	{
		return this.mobile;
	}
	
	public String getRemarks()
	{
		return this.remarks;
	}
	
	
	public void setFromAcc(long from_acc)
	{
		this.from_acc = from_acc;
	}
	
	public void setToAcc(long to_acc)
	{
		this.to_acc = to_acc;
	}
	
	public void setTID(long t_id)
	{
		this.t_id = t_id;
	}
	
	public void setTAmount(long t_amount)
	{
		this.t_amount = t_amount;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public void setMobile(long mobile)
	{
		this.mobile = mobile;
	}
	
	public void setRemarks(String remarks)
	{
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "QuickPay [from_acc=" + from_acc + ", to_acc=" + to_acc + ", t_id=" + t_id + ", t_amount=" + t_amount
				+ ", email=" + email + ", mobile=" + mobile + ", remarks=" + remarks + "]";
	}
	
	
}
