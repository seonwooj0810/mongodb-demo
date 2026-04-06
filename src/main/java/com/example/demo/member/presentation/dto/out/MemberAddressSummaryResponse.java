package com.example.demo.member.presentation.dto.out;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.member.domain.MemberType;
import com.example.demo.member.domain.MemberWithAddress;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "주소 보유 회원")
public record MemberAddressSummaryResponse(
	String id,
	String name,
	String email,
	MemberType memberType,
	List<MemberAddressResponse> addresses,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) implements MemberSummaryResponse {

	public static MemberAddressSummaryResponse from(MemberWithAddress member) {
		return new MemberAddressSummaryResponse(
			member.getId(),
			member.getName(),
			member.getEmail(),
			MemberType.ADDRESS,
			member.getAddresses().stream()
				.map(MemberAddressResponse::from)
				.toList(),
			member.getCreatedAt(),
			member.getUpdatedAt()
		);
	}
}
