package com.library.exception.handler;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage {
	private int statusCode;
	private String message;
	private Date timestamp;

	@Override
	public String toString() {
		return "ResponseMessage [statusCode=" + statusCode + ", message=" + message + ", timestamp=" + timestamp + "]";
	}

}
