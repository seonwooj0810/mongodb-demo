package com.example.demo.member.presentation.dto.in;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.demo.member.domain.MemberType;

import io.swagger.v3.oas.annotations.media.Schema;

public record MemberSearchRequest(
	String name,
	@Schema(description = "회원 유형 필터 (ADDRESS: 주소 보유 회원, HOUSE: 집 보유 회원, 미입력 시 전체 조회)")
	MemberType type,
	Integer page,
	Integer size,
	String sortBy,
	String direction
) {
	public Pageable toPageable() {
		int pageNumber = page != null ? page : 0;
		int pageSize = size != null ? size : 20;
		String field = sortBy != null ? sortBy : "createdAt";
		Sort.Direction dir = "asc".equalsIgnoreCase(direction) ? Sort.Direction.ASC : Sort.Direction.DESC;
		return PageRequest.of(pageNumber, pageSize, Sort.by(dir, field));
	}
}
