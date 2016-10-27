package org.asu.ss.model;

public class RequestApprove {

	private long custid;
	
	private boolean flag;
	
	public long getCustid() {
		return custid;
	}

	public void setCustid(long custid) {
		this.custid = custid;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public RequestApprove(long custid, boolean flag) {
		super();
		this.custid = custid;
		this.flag = flag;
	}

	public RequestApprove() {
		super();
		// TODO Auto-generated constructor stub
	}

	/*@Override
	public String toString() {
		return "RequestApprove [custid=" + custid + ", flag=" + flag + "]";
	}*/
	
	


}
