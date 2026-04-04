package com.example.demo.member.repository;

import org.springframework.data.domain.Page;

import com.example.demo.member.domain.Member;
import com.example.demo.member.presentation.dto.in.MemberSearchRequest;
import com.example.demo.member.presentation.dto.in.UpdateAddressRequest;

public interface MemberCustomRepository {

    Page<Member> searchByName(MemberSearchRequest request);

    long updateAddress(String memberId, String addressId, UpdateAddressRequest request);
}
