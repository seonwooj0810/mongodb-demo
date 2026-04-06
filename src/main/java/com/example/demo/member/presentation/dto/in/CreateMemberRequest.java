package com.example.demo.member.presentation.dto.in;

import com.example.demo.member.domain.MemberType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateMemberRequest(
	@NotBlank(message = "이름은 필수입니다.")
	String name,

	@NotBlank(message = "이메일은 필수입니다.")
	@Email(message = "이메일 형식이 올바르지 않습니다.")
	String email,

	@NotNull(message = "회원 유형은 필수입니다.")
	MemberType type
) {
}
