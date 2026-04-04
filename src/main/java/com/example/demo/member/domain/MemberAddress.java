package com.example.demo.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberAddress {

	private String addressId;
	private String label;
	private String zipCode;
	private String city;
	private String street;
	private String detail;
	private boolean defaultAddress;
}
