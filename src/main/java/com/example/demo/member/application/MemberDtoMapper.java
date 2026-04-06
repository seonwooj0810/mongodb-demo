package com.example.demo.member.application;

import org.springframework.stereotype.Component;

import com.example.demo.member.domain.MemberVisitor;
import com.example.demo.member.domain.MemberWithAddress;
import com.example.demo.member.domain.MemberWithHouse;
import com.example.demo.member.presentation.dto.out.MemberAddressSummaryResponse;
import com.example.demo.member.presentation.dto.out.MemberHouseSummaryResponse;
import com.example.demo.member.presentation.dto.out.MemberSummaryResponse;

@Component
public class MemberDtoMapper implements MemberVisitor<MemberSummaryResponse> {

	@Override
	public MemberSummaryResponse visit(MemberWithAddress member) {
		return MemberAddressSummaryResponse.from(member);
	}

	@Override
	public MemberSummaryResponse visit(MemberWithHouse member) {
		return MemberHouseSummaryResponse.from(member);
	}
}
