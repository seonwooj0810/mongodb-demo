package com.example.demo.member.presentation;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.response.ApiResult;
import com.example.demo.common.response.PageResponse;
import com.example.demo.member.application.MemberService;
import com.example.demo.member.presentation.dto.in.CreateMemberRequest;
import com.example.demo.member.presentation.dto.in.MemberSearchRequest;
import com.example.demo.member.presentation.dto.in.UpdateMemberRequest;
import com.example.demo.member.presentation.dto.out.MemberAddressSummaryResponse;
import com.example.demo.member.presentation.dto.out.MemberHouseSummaryResponse;
import com.example.demo.member.presentation.dto.out.MemberResponse;
import com.example.demo.member.presentation.dto.out.MemberSummaryResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Tag(name = "01. 회원")
public class MemberController {

	private final MemberService memberService;

	@GetMapping
	@Operation(
		summary = "회원 목록 조회",
		description = "type 파라미터로 주소 보유 회원(ADDRESS) 또는 집 보유 회원(HOUSE)을 구분하여 조회합니다."
	)
	@ApiResponse(responseCode = "200", content = @Content(
		schema = @Schema(
			oneOf = {MemberAddressSummaryResponse.class, MemberHouseSummaryResponse.class},
			discriminatorProperty = "memberType",
			discriminatorMapping = {
				@DiscriminatorMapping(value = "ADDRESS", schema = MemberAddressSummaryResponse.class),
				@DiscriminatorMapping(value = "HOUSE", schema = MemberHouseSummaryResponse.class)
			}
		)
	))
	public ApiResult<PageResponse<MemberSummaryResponse>> getMembers(@ParameterObject MemberSearchRequest request) {
		return ApiResult.success(memberService.getMembers(request));
	}

	@GetMapping("/{id}")
	public ApiResult<MemberResponse> getMember(@PathVariable String id) {
		return ApiResult.success(memberService.getMember(id));
	}

	@PostMapping
	public ApiResult<String> createMember(@Valid @RequestBody CreateMemberRequest request) {
		return ApiResult.success(memberService.createMember(request));
	}

	@PutMapping("/{id}")
	public ApiResult<String> updateMember(
		@PathVariable String id,
		@Valid @RequestBody UpdateMemberRequest request
	) {
		return ApiResult.success(memberService.updateMember(id, request.name(), request.email()));
	}

	@DeleteMapping("/{id}")
	public ApiResult<String> deleteMember(@PathVariable String id) {
		return ApiResult.success(memberService.deleteMember(id));
	}
}
