package org.asu.ss.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "transaction")
public class Transaction {

	@Id
	@Column(name = "t_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long t_id=1;

	@Column(name = "from_acc")
	private long from_acc;

	@Column(name = "to_acc")
	private long to_acc;
	
	@Column(name = "t_custid")
	private long t_custid;

	@Column(name = "t_timestamp")
	private Date t_timestamp;

	@Column(name = "t_amount")
	private double t_amount;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "severity")
	private String severity="";
	
	@Column(name = "t_status")
	private String t_status="";
	
	@Column(name = "otp_status")
	private String otp_status="";
	
	@JsonIgnore
	@Column(name="otp_id")
	private long otp_id;
	
	@JsonIgnore
	@Column(name="otp_value")
	private String otp_value;
	
	@Column(name ="mobile")
	private String mobile="";
	
	@JsonIgnore
	@Column(name="email")
	private String email="";

	public long getT_id() {
		return t_id;
	}

	public void setT_id(long t_id) {
		this.t_id = t_id;
	}

	public long getFrom_acc() {
		return from_acc;
	}

	public void setFrom_acc(long from_acc) {
		this.from_acc = from_acc;
	}

	public long getTo_acc() {
		return to_acc;
	}

	public void setTo_acc(long to_acc) {
		this.to_acc = to_acc;
	}

	public long getT_custid() {
		return t_custid;
	}

	public void setT_custid(long t_custid) {
		this.t_custid = t_custid;
	}

	public Date getT_timestamp() {
		return t_timestamp;
	}

	public void setT_timestamp(Date t_timestamp) {
		this.t_timestamp = t_timestamp;
	}

	public double getT_amount() {
		return t_amount;
	}

	public void setT_amount(double t_amount) {
		this.t_amount = t_amount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getT_status() {
		return t_status;
	}

	public void setT_status(String t_status) {
		this.t_status = t_status;
	}

	public String getOtp_status() {
		return otp_status;
	}

	public void setOtp_status(String otp_status) {
		this.otp_status = otp_status;
	}

	public long getOtp_id() {
		return otp_id;
	}

	public void setOtp_id(long otp_id) {
		this.otp_id = otp_id;
	}

	public String getOtp_value() {
		return otp_value;
	}

	public void setOtp_value(String otp_value) {
		this.otp_value = otp_value;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	
	public Transaction() {
		super();
	}

	public Transaction(long t_id, long from_acc, long to_acc, long t_custid, Date t_timestamp, double t_amount,
			String remarks, String severity, String t_status, String otp_status, long otp_id, String otp_value,
			String mobile, String email) {
		super();
		this.t_id = t_id;
		this.from_acc = from_acc;
		this.to_acc = to_acc;
		this.t_custid = t_custid;
		this.t_timestamp = t_timestamp;
		this.t_amount = t_amount;
		this.remarks = remarks;
		this.severity = severity;
		this.t_status = t_status;
		this.otp_status = otp_status;
		this.otp_id = otp_id;
		this.otp_value = otp_value;
		this.mobile = mobile;
		this.email = email;
	}

	@Override
	public String toString() {
		return "Transaction [t_id=" + t_id + ", from_acc=" + from_acc + ", to_acc=" + to_acc + ", t_custid=" + t_custid
				+ ", t_timestamp=" + t_timestamp + ", t_amount=" + t_amount + ", remarks=" + remarks + ", severity="
				+ severity + ", t_status=" + t_status + ", otp_status=" + otp_status + ", otp_id=" + otp_id
				+ ", otp_value=" + otp_value + ", mobile=" + mobile + ", email=" + email + "]";
	}
	
}
