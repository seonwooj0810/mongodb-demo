package com.example.demo.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberHouse {

	private String block;
	private String unit;
	private String nickname;
	private String memo;
	private Integer floor;
}
