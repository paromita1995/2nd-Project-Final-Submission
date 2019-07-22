package com.niit.Articulation.Model;

import java.io.Serializable;

import javax.persistence.Transient;

import org.springframework.stereotype.Component;

@Component
public class BaseDomain implements Serializable

{
	
	@Transient
public String errorCode;
public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	@Transient
	public String errorMessage;
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	
	
}
