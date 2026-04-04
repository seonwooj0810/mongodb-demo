package com.example.demo.common.response;

import java.util.List;

import org.springframework.data.domain.Page;

public record PageResponse<T>(
	List<T> list,
	PageInfo pageInfo
) {
	public static <T> PageResponse<T> from(Page<T> page) {
		return new PageResponse<>(
			page.getContent(),
			PageInfo.of(
				page.getPageable().getPageNumber(),
				page.getPageable().getPageSize(),
				page.getTotalPages(),
				page.getTotalElements(),
				page.isLast()
			)
		);
	}
}
