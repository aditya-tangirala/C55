package org.asu.ss.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tempexternaluser")
public class ObjecttoUI {

	@Id
	@Column(name = "id")
	private long id;

	@Column(name = "cust_id")
	private long cust_id;

	@Column(name = "email")
	private String email;
	
	@Column(name = "otp_value")
	private String otp_value;

	@Column(name = "mobile_carrier")
	private String mobile_carrier;

	@Column(name = "mobile")
	private String mobile;
	
	@Column(name = "item")
	private String item;
	
	@Column(name = "message")
	private String message;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCust_id() {
		return cust_id;
	}

	public void setCust_id(long cust_id) {
		this.cust_id = cust_id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOtp_value() {
		return otp_value;
	}

	public void setOtp_value(String otp_value) {
		this.otp_value = otp_value;
	}

	public String getMobile_carrier() {
		return mobile_carrier;
	}

	public void setMobile_carrier(String mobile_carrier) {
		this.mobile_carrier = mobile_carrier;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public ObjecttoUI() {
		super();
	}

	public ObjecttoUI(long id, long cust_id, String email, String otp_value, String mobile_carrier, String mobile,
			String item, String message) {
		super();
		this.id = id;
		this.cust_id = cust_id;
		this.email = email;
		this.otp_value = otp_value;
		this.mobile_carrier = mobile_carrier;
		this.mobile = mobile;
		this.item = item;
		this.message = message;
	}

	@Override
	public String toString() {
		return "ObjecttoUI [id=" + id + ", cust_id=" + cust_id + ", email=" + email + ", otp_value=" + otp_value
				+ ", mobile_carrier=" + mobile_carrier + ", mobile=" + mobile + ", item=" + item + ", message="
				+ message + "]";
	}
	
	
	
}