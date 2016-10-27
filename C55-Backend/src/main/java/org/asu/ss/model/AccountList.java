package org.asu.ss.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="account")
public class AccountList {
	
	@Id
	@Column(name = "acc_no")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	String acc_no;

	public String getAcc_no() {
		return acc_no;
	}

	public void setAcc_no(String acc_no) {
		this.acc_no = acc_no;
	}

	public AccountList(String acc_no) {
		super();
		this.acc_no = acc_no;
	}

	public AccountList() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "AccountList [acc_no=" + acc_no + "]";
	}
	

}
