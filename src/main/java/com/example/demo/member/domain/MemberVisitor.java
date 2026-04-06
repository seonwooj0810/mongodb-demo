package com.example.demo.member.domain;

public interface MemberVisitor<T> {
	T visit(MemberWithAddress member);
	T visit(MemberWithHouse member);
}
