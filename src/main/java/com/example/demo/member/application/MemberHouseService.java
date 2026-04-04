package com.example.demo.member.application;

import static com.example.demo.common.error.ErrorCode.HOUSE_DUPLICATED;
import static com.example.demo.common.error.ErrorCode.HOUSE_NOT_FOUND;
import static com.example.demo.common.error.ErrorCode.MEMBER_NOT_FOUND;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.common.error.CustomException;
import com.example.demo.member.presentation.dto.in.MemberHouseKeyRequest;
import com.example.demo.member.presentation.dto.in.MemberHouseRequest;
import com.example.demo.member.presentation.dto.in.UpdateHouseItemRequest;
import com.example.demo.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberHouseService {

	private final MemberRepository memberRepository;

	public String addHouse(String memberId, MemberHouseRequest request) {
		validateMemberExists(memberId);
		validateHouseNotDuplicated(memberId, request.block(), request.unit());
		memberRepository.addHouse(memberId, request);
		return memberId;
	}

	@Transactional
	public String updateHouses(String memberId, List<UpdateHouseItemRequest> requests) {
		int modifiedCount = memberRepository.updateHouses(memberId, requests);
		if (modifiedCount != requests.size()) {
			throw new CustomException(HOUSE_NOT_FOUND);
		}
		return memberId;
	}

	public String removeHouses(String memberId, List<MemberHouseKeyRequest> keys) {
		long modifiedCount = memberRepository.removeHouses(memberId, keys);
		if (modifiedCount == 0) {
			throw new CustomException(HOUSE_NOT_FOUND);
		}
		return memberId;
	}

	private void validateMemberExists(String memberId) {
		if (!memberRepository.existsById(memberId)) {
			throw new CustomException(MEMBER_NOT_FOUND);
		}
	}

	private void validateHouseNotDuplicated(String memberId, String block, String unit) {
		if (memberRepository.existsHouse(memberId, block, unit)) {
			throw new CustomException(HOUSE_DUPLICATED);
		}
	}
}
