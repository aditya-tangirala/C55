package org.asu.ss.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="resetrequest")
public class PasswordReset {
	
	@Id
	@Column(name = "e_id")
	private long eid;
	
	@Column(name = "flag")
	private boolean flag;
	
	public long getEid() {
		return eid;
	}

	public void setEid(long eid) {
		this.eid = eid;
	}

	
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public PasswordReset(long eid, boolean flag) {
		super();
		this.eid = eid;
		this.flag = flag;
	}

	public PasswordReset() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
