package org.asu.ss.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(name = "otp")
public class OTP {

	@Id
	@Column(name = "otp_id")
	private long otp_id;

	@Column(name = "c_id")
	private long c_id;

	@Column(name = "otp_value")
	private String otp_value;
	
	@Column(name = "mobile")
	private String mobile = "6232230201";

	@Column(name = "opt_ts")
	private Date opt_ts;

	@Column(name = "otp_purpose")
	private String otp_purpose;

	@Column(name = "email")
	private String email = "puranamsrinivas26@gmail.com";

	@Column(name = "mobile_carrier")
	private String mobile_carrier = "AT&T";

	public long getOtp_id() {
		return otp_id;
	}

	public void setOtp_id(long otp_id) {
		this.otp_id = otp_id;
	}

	public long getC_id() {
		return c_id;
	}

	public void setC_id(long c_id) {
		this.c_id = c_id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getOpt_ts() {
		return opt_ts;
	}

	public void setOpt_ts(Date opt_ts2) {
		this.opt_ts = opt_ts2;
	}

	public String getOtp_purpose() {
		return otp_purpose;
	}

	public void setOtp_purpose(String otp_purpose) {
		this.otp_purpose = otp_purpose;
	}

	public String getMobile_carrier() {
		return mobile_carrier;
	}

	public void setMobile_carrier(String mobile_carrier) {
		this.mobile_carrier = mobile_carrier;
	}

	public String getOtp_value() {
		return otp_value;
	}

	public void setOtp_value(String otpGenerated) {
		this.otp_value = otpGenerated;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public OTP() {
		super();
	}

	public OTP(long otp_id, String otp_value, long c_id, String mobile, Date opt_ts, String otp_purpose,
			String mobile_carrier, long tempexternaluser_id, String email) {
		super();
		this.otp_id = otp_id;
		this.c_id = c_id;
		this.mobile = mobile;
		this.opt_ts = opt_ts;
		this.otp_purpose = otp_purpose;
		this.mobile_carrier = mobile_carrier;
		this.otp_value = otp_value;
		this.email = email;
	}

	/*@Override
	public String toString() {
		return "OTP [otp_id=" + otp_id + ", c_id=" + c_id + ", otp_value=" + otp_value + ", mobile=" + mobile
				+ ", opt_ts=" + opt_ts + ", otp_purpose=" + otp_purpose + ", email=" + email + ", mobile_carrier="
				+ mobile_carrier + "]";
	}*/

	
}
