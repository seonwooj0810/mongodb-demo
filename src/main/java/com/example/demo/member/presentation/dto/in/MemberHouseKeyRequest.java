package com.example.demo.member.presentation.dto.in;

import jakarta.validation.constraints.NotBlank;

public record MemberHouseKeyRequest(
	@NotBlank(message = "동은 필수입니다.")
	String block,

	@NotBlank(message = "호수는 필수입니다.")
	String unit
) {
}
