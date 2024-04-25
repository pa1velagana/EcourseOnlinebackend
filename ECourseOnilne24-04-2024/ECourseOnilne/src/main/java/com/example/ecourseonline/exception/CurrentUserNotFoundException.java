package com.example.ecourseonline.exception;

public class CurrentUserNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String errMsg;
	public CurrentUserNotFoundException(String errMsg) {
		super();
		this.errMsg = errMsg;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

}
