package com.example.demo.member.presentation.dto.out;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.member.domain.Member;
import com.example.demo.member.domain.MemberWithAddress;
import com.example.demo.member.domain.MemberWithHouse;

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
		List<MemberAddressResponse> addresses = member instanceof MemberWithAddress m
			? m.getAddresses().stream().map(MemberAddressResponse::from).toList()
			: List.of();

		List<MemberHouseResponse> houses = member instanceof MemberWithHouse m
			? m.getHouses().stream().map(MemberHouseResponse::from).toList()
			: List.of();

		return new MemberResponse(
			member.getId(),
			member.getName(),
			member.getEmail(),
			addresses,
			houses,
			member.getCreatedAt(),
			member.getUpdatedAt()
		);
	}
}
