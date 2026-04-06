package com.example.demo.member.application;

import static com.example.demo.common.error.ErrorCode.MEMBER_NOT_FOUND;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.demo.common.error.CustomException;
import com.example.demo.common.response.PageResponse;
import com.example.demo.member.domain.Member;
import com.example.demo.member.domain.MemberWithAddress;
import com.example.demo.member.domain.MemberWithHouse;
import com.example.demo.member.presentation.dto.in.CreateMemberRequest;
import com.example.demo.member.presentation.dto.in.MemberSearchRequest;
import com.example.demo.member.presentation.dto.out.MemberResponse;
import com.example.demo.member.presentation.dto.out.MemberSummaryResponse;
import com.example.demo.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final MemberDtoMapper memberDtoMapper;

	public PageResponse<MemberSummaryResponse> getMembers(MemberSearchRequest request) {
		Page<Member> members = memberRepository.searchByName(request);
		return PageResponse.from(members.map(m -> m.accept(memberDtoMapper)));
	}

	public MemberResponse getMember(String id) {
		Member member = memberRepository.findById(id)
			.orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
		return MemberResponse.from(member);
	}

	public String createMember(CreateMemberRequest request) {
		Member member = switch (request.type()) {
			case ADDRESS -> new MemberWithAddress(request.name(), request.email());
			case HOUSE -> new MemberWithHouse(request.name(), request.email());
		};
		memberRepository.save(member);
		return member.getId();
	}

	public String updateMember(String id, String name, String email) {
		Member member = memberRepository.findById(id)
			.orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
		member.update(name, email);
		memberRepository.save(member);
		return member.getId();
	}

	public String deleteMember(String id) {
		Member member = memberRepository.findById(id)
			.orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
		memberRepository.deleteById(member.getId());
		return id;
	}
}
