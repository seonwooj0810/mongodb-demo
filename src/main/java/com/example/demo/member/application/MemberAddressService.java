package com.example.demo.member.application;

import static com.example.demo.common.error.ErrorCode.ADDRESS_NOT_FOUND;
import static com.example.demo.common.error.ErrorCode.MEMBER_NOT_FOUND;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.common.error.CustomException;
import com.example.demo.common.response.PageInfo;
import com.example.demo.common.response.PageResponse;
import com.example.demo.member.domain.MemberAddress;
import com.example.demo.member.domain.MemberWithAddress;
import com.example.demo.member.presentation.dto.in.UpdateAddressRequest;
import com.example.demo.member.presentation.dto.out.MemberAddressResponse;
import com.example.demo.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberAddressService {

	private final MemberRepository memberRepository;

	public PageResponse<MemberAddressResponse> getAddresses(String memberId, Pageable pageable) {
		MemberWithAddress member = findMemberWithAddress(memberId);

		List<MemberAddress> addresses = member.getAddresses();

		int total = addresses.size();
		int start = (int) pageable.getOffset();
		int end = Math.min(start + pageable.getPageSize(), total);

		List<MemberAddressResponse> content = (start >= total)
			? List.of()
			: addresses.subList(start, end).stream()
				.map(MemberAddressResponse::from)
				.toList();

		int totalPages = (int) Math.ceil((double) total / pageable.getPageSize());
		boolean isLast = end >= total;

		return new PageResponse<>(
			content,
			PageInfo.of(pageable.getPageNumber(), pageable.getPageSize(), totalPages, total, isLast)
		);
	}

	public Boolean updateAddress(String memberId, String addressId, UpdateAddressRequest request) {
		long modifiedCount = memberRepository.updateAddress(memberId, addressId, request);
		if (modifiedCount == 0) {
			throw new CustomException(ADDRESS_NOT_FOUND);
		}
		return true;
	}

	private MemberWithAddress findMemberWithAddress(String memberId) {
		return memberRepository.findById(memberId)
			.filter(m -> m instanceof MemberWithAddress)
			.map(m -> (MemberWithAddress) m)
			.orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
	}
}
