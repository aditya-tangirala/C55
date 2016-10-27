package org.asu.ss.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "tempexternalusertrivial")
public class TempExternalUserTrivial {
	
	@Id
	@Column(name = "cust_id")
	private long cust_id;
	
	@Column(name="f_name")
	private String f_name;
	
	@Column(name="password")
	private String password;
	
	@Column(name="l_name")
	private String l_name;
	
	@Column(name="address")
	private String address;
	
	@Column(name = "item")
	private String item;
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public long getCust_id() {
		return cust_id;
	}

	public void setCust_id(long cust_id) {
		this.cust_id = cust_id;
	}

	public String getF_name() {
		return f_name;
	}

	public void setF_name(String f_name) {
		this.f_name = f_name;
	}

	public String getL_name() {
		return l_name;
	}

	public void setL_name(String l_name) {
		this.l_name = l_name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public TempExternalUserTrivial() {
		super();
	}
	
	public TempExternalUserTrivial(long cust_id, String f_name, String l_name, String address, String item, String password) {
		super();
		this.cust_id = cust_id;
		this.f_name = f_name;
		this.l_name = l_name;
		this.address = address;
		this.item = item;
		this.password = password;
	}

	@Override
	public String toString() {
		return "TempExternalUserTrivial [cust_id=" + cust_id + ", f_name=" + f_name + ", password=" + password
				+ ", l_name=" + l_name + ", address=" + address + ", item=" + item + "]";
	}
	
	
}
