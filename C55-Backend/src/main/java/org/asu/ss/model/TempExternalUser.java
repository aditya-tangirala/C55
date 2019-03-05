package org.asu.ss.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tempexternaluser")
public class TempExternalUser {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "flag")
	private int flag;

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

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

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
	
	public String getOtp_value() {
		return otp_value;
	}

	public void setOtp_value(String otp_value) {
		this.otp_value = otp_value;
	}

	public int isFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public TempExternalUser() {
		super();
	}

	public TempExternalUser(long id, long cust_id, String email, String mobile_carrier, String mobile, String item,
			String otp_value, int flag) {
		super();
		this.id = id;
		this.cust_id = cust_id;
		this.email = email;
		this.mobile_carrier = mobile_carrier;
		this.mobile = mobile;
		this.item = item;
		this.otp_value = otp_value;
		this.flag=flag;

	}

	/*@Override
	public String toString() {
		return "TempExternalUser [id=" + id + ", cust_id=" + cust_id + ", email=" + email + ", otp_value=" + otp_value
				+ ", mobile_carrier=" + mobile_carrier + ", mobile=" + mobile + ", item=" + item + "]";
	}*/
	
	
	
}
