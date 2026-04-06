package com.example.demo.member.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Document(collection = "Member")
@org.springframework.data.annotation.TypeAlias("ADDRESS")
public final class MemberWithAddress extends Member {

	private List<MemberAddress> addresses = new ArrayList<>();

	public MemberWithAddress(String name, String email) {
		super(name, email);
	}

	@Override
	public <T> T accept(MemberVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
