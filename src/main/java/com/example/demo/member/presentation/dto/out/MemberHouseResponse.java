package com.example.demo.member.presentation.dto.out;

import com.example.demo.member.domain.MemberHouse;

public record MemberHouseResponse(
	String block,
	String unit,
	String nickname,
	String memo,
	Integer floor
) {
	public static MemberHouseResponse from(MemberHouse house) {
		return new MemberHouseResponse(
			house.getBlock(),
			house.getUnit(),
			house.getNickname(),
			house.getMemo(),
			house.getFloor()
		);
	}
}
