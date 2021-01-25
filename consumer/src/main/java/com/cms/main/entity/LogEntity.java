package com.cms.main.entity;

public class LogEntity {
	public String NarId;

	public String getNarId() {
		return NarId;
	}

	public void setNarId(String narId) {
		NarId = narId;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public String getLogOccurenceDateTime() {
		return logOccurenceDateTime;
	}

	public void setLogOccurenceDateTime(String logOccurenceDateTime) {
		this.logOccurenceDateTime = logOccurenceDateTime;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public Exception exception;
	public String logOccurenceDateTime;
	public String exceptionMessage;
}
