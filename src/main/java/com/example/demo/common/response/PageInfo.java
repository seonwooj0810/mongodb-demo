package com.example.demo.common.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PageInfo {

	private Integer pageNumber;
	private Integer pageSize;
	private Integer totalPages;
	private Long totalElements;
	private boolean isLast;

	public static PageInfo of(int pageNumber, int pageSize, int totalPages, long totalElements, boolean isLast) {
		return PageInfo.builder()
			.pageNumber(pageNumber)
			.pageSize(pageSize)
			.totalPages(totalPages)
			.totalElements(totalElements)
			.isLast(isLast)
			.build();
	}
}
