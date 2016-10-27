package org.asu.ss.model;



public class CreditCardAmountDue {
	private double amtdue;

	public double getAmtdue() {
		return amtdue;
	}

	public void setAmtdue(double amtdue) {
		this.amtdue = amtdue;
	}

	public CreditCardAmountDue(double amtdue) {
		super();
		this.amtdue = amtdue;
	}

	public CreditCardAmountDue() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "CreditCardAmountDue [amtdue=" + amtdue + "]";
	}
	
	
	
}

