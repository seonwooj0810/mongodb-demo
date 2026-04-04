package com.example.demo.member.presentation;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.response.ApiResult;
import com.example.demo.common.response.PageResponse;
import com.example.demo.member.application.MemberAddressService;
import com.example.demo.member.presentation.dto.in.UpdateAddressRequest;
import com.example.demo.member.presentation.dto.out.MemberAddressResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/members/{memberId}/addresses")
@RequiredArgsConstructor
@Tag(name = "02. 배송지")
public class MemberAddressController {

	private final MemberAddressService memberAddressService;

	@GetMapping
	public ApiResult<PageResponse<MemberAddressResponse>> getAddresses(
		@PathVariable String memberId,
		@ParameterObject Pageable pageable
	) {
		return ApiResult.success(memberAddressService.getAddresses(memberId, pageable));
	}

	@PutMapping("/{addressId}")
	public ApiResult<Boolean> updateAddress(
		@PathVariable String memberId,
		@PathVariable String addressId,
		@Valid @RequestBody UpdateAddressRequest request
	) {
		return ApiResult.success(memberAddressService.updateAddress(memberId, addressId, request));
	}
}
