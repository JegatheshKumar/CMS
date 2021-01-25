package com.csm.client.main.entity;

import java.text.ParseException;
import java.util.Date;

import com.csm.client.main.utilities.FileWatcherUtils;

public class LogEntity {
	public String NarId;
	public Exception exception;
	public String logOccurenceDateTime;
	public String exceptionMessage;

	LogEntity() {

	}

	public LogEntity(String NarId, Exception exception, Date logOccurenceDateTime, String exceptionMessage) {

		this.NarId = NarId;
		this.exception = exception;
		this.logOccurenceDateTime = FileWatcherUtils.convertDate(logOccurenceDateTime);
		this.exceptionMessage = exceptionMessage;
	}

	public String getNarId() {
		return NarId;
	}

	public void setNarId(String NarId) {
		this.NarId = NarId;
	}

	public String getLogOccurenceDateTime() {
		return logOccurenceDateTime;
	}

	public void setLogOccurenceDateTime(Date logOccurenceDateTime) throws ParseException {

		this.logOccurenceDateTime = FileWatcherUtils.convertDate(logOccurenceDateTime);
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public static void main(String args[]) throws Exception {
		FileWatcherUtils.checkandCreateConfigFile();
	}

}
