package uy.com.pepeganga.business.common.models;

public class ReasonResponse {

	boolean success;
	String reason;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}

	public ReasonResponse() {
	}

	public ReasonResponse(boolean success, String reason) {
		this.success = success;
		this.reason = reason;
	}
}
