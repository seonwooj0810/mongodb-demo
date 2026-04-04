package com.example.demo.common.error;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	MEMBER_NOT_FOUND(NOT_FOUND, "회원을 찾을 수 없습니다."),
	ADDRESS_NOT_FOUND(NOT_FOUND, "배송지를 찾을 수 없습니다."),
	HOUSE_NOT_FOUND(NOT_FOUND, "해당 동/호수를 찾을 수 없습니다."),
	HOUSE_DUPLICATED(CONFLICT, "이미 등록된 동/호수입니다.");

	private final HttpStatus status;
	private final String message;
}
