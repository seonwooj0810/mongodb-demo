package com.example.demo.member.presentation.dto.out;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.member.domain.MemberType;
import com.example.demo.member.domain.MemberWithHouse;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "집 보유 회원")
public record MemberHouseSummaryResponse(
	String id,
	String name,
	String email,
	MemberType memberType,
	List<MemberHouseResponse> houses,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) implements MemberSummaryResponse {

	public static MemberHouseSummaryResponse from(MemberWithHouse member) {
		return new MemberHouseSummaryResponse(
			member.getId(),
			member.getName(),
			member.getEmail(),
			MemberType.HOUSE,
			member.getHouses().stream()
				.map(MemberHouseResponse::from)
				.toList(),
			member.getCreatedAt(),
			member.getUpdatedAt()
		);
	}
}
