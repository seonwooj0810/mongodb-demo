package com.example.demo.member.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.demo.common.domain.BaseTimeEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Document(collection = "members")
public class Member extends BaseTimeEntity {

	@Id
	private String id;

	private String name;

	private String email;

	private List<MemberAddress> addresses = new ArrayList<>();

	private List<MemberHouse> houses = new ArrayList<>();

	public Member(String name, String email) {
		this.name = name;
		this.email = email;
	}

	public void update(String name, String email) {
		this.name = name;
		this.email = email;
	}
}
