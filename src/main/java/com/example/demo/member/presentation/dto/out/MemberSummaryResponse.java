package com.example.demo.member.presentation.dto.out;

import java.time.LocalDateTime;

import com.example.demo.member.domain.Member;

public record MemberSummaryResponse(
	String id,
	String name,
	String email,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {
	public static MemberSummaryResponse from(Member member) {
		return new MemberSummaryResponse(
			member.getId(),
			member.getName(),
			member.getEmail(),
			member.getCreatedAt(),
			member.getUpdatedAt()
		);
	}
}
