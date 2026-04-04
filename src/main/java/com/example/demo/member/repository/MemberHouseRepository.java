package com.example.demo.member.repository;

import java.util.List;

import com.example.demo.member.presentation.dto.in.MemberHouseKeyRequest;
import com.example.demo.member.presentation.dto.in.MemberHouseRequest;
import com.example.demo.member.presentation.dto.in.UpdateHouseItemRequest;

public interface MemberHouseRepository {

    boolean existsHouse(String memberId, String block, String unit);

    void addHouse(String memberId, MemberHouseRequest request);

    int updateHouses(String memberId, List<UpdateHouseItemRequest> requests);

    long removeHouses(String memberId, List<MemberHouseKeyRequest> keys);
}
