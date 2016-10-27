package org.asu.ss.model;

public class BackendResponse {

	public static final String SUCCESS = "Success";
	public static final String FAILURE = "Failure";
	
	private String status;
	private String error;
	private Object data;
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "BackendResponse [status=" + status + ", error=" + error + ", data=" + data+ "]";

	}
	
	
	
}
