package com.example.demo.member.presentation.dto.out;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.member.domain.Member;

public record MemberResponse(
	String id,
	String name,
	String email,
	List<MemberAddressResponse> addresses,
	List<MemberHouseResponse> houses,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {
	public static MemberResponse from(Member member) {
		return new MemberResponse(
			member.getId(),
			member.getName(),
			member.getEmail(),
			member.getAddresses().stream()
				.map(MemberAddressResponse::from)
				.toList(),
			member.getHouses().stream()
				.map(MemberHouseResponse::from)
				.toList(),
			member.getCreatedAt(),
			member.getUpdatedAt()
		);
	}
}
