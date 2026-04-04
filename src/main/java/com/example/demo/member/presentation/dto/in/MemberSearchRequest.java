package com.example.demo.member.presentation.dto.in;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public record MemberSearchRequest(
	String name,
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
