package com.example.demo.member.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.demo.common.domain.BaseTimeEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Document(collection = "Member")
public abstract sealed class Member extends BaseTimeEntity
	permits MemberWithAddress, MemberWithHouse {

	@Id
	private String id;

	private String name;

	private String email;

	protected Member(String name, String email) {
		this.name = name;
		this.email = email;
	}

	public void update(String name, String email) {
		this.name = name;
		this.email = email;
	}

	public abstract <T> T accept(MemberVisitor<T> visitor);
}
