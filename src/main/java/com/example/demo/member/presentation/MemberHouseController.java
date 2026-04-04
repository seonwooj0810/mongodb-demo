package com.example.demo.member.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.response.ApiResult;
import com.example.demo.member.application.MemberHouseService;
import com.example.demo.member.presentation.dto.in.MemberHouseKeyRequest;
import com.example.demo.member.presentation.dto.in.MemberHouseRequest;
import com.example.demo.member.presentation.dto.in.UpdateHouseItemRequest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/members/{memberId}/houses")
@RequiredArgsConstructor
@Tag(name = "03. 동/호수")
public class MemberHouseController {

	private final MemberHouseService memberHouseService;

	@PostMapping
	public ApiResult<String> addHouse(
		@PathVariable String memberId,
		@Valid @RequestBody MemberHouseRequest request
	) {
		return ApiResult.success(memberHouseService.addHouse(memberId, request));
	}

	@PutMapping
	public ApiResult<String> updateHouses(
		@PathVariable String memberId,
		@Valid @RequestBody List<UpdateHouseItemRequest> requests
	) {
		return ApiResult.success(memberHouseService.updateHouses(memberId, requests));
	}

	@DeleteMapping
	public ApiResult<String> removeHouses(
		@PathVariable String memberId,
		@Valid @RequestBody List<MemberHouseKeyRequest> keys
	) {
		return ApiResult.success(memberHouseService.removeHouses(memberId, keys));
	}
}
