package com.example.demo.member.presentation.dto.in;

import jakarta.validation.constraints.NotBlank;

public record UpdateAddressRequest(
	@NotBlank(message = "배송지명은 필수입니다.")
	String label,

	@NotBlank(message = "우편번호는 필수입니다.")
	String zipCode,

	@NotBlank(message = "도시는 필수입니다.")
	String city,

	@NotBlank(message = "도로명은 필수입니다.")
	String street,

	String detail,

	boolean defaultAddress
) {
}
