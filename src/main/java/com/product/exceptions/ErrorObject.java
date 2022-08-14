package com.product.exceptions;

public class ErrorObject {
	String message;
	Integer statusCode;
	Long timestemp;
	public ErrorObject(String message, Integer statusCode, Long timestemp) {
		super();
		this.message = message;
		this.statusCode = statusCode;
		this.timestemp = timestemp;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ErrorObject() {

	}
	public Integer getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	public Long getTimestemp() {
		return timestemp;
	}
	public void setTimestemp(Long timestemp) {
		this.timestemp = timestemp;
	}

}
