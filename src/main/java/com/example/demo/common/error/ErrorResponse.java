package com.example.demo.common.error;

import static lombok.AccessLevel.*;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@JsonPropertyOrder({"success", "message", "code", "timestamp"})
public class ErrorResponse {
	private boolean success;
	private String code;
	private String message;
	private String timestamp;

	public ErrorResponse(String message) {
		this.success = false;
		this.message = message;
		this.timestamp = LocalDateTime.now().toString();
	}

	public static ErrorResponse of(String errorMessage) {
		return new ErrorResponse(errorMessage);
	}
}