package com.example.demo.member.presentation.dto.out;

import com.example.demo.member.domain.MemberAddress;

public record MemberAddressResponse(
	String addressId,
	String label,
	String zipCode,
	String city,
	String street,
	String detail,
	boolean defaultAddress
) {
	public static MemberAddressResponse from(MemberAddress address) {
		return new MemberAddressResponse(
			address.getAddressId(),
			address.getLabel(),
			address.getZipCode(),
			address.getCity(),
			address.getStreet(),
			address.getDetail(),
			address.isDefaultAddress()
		);
	}
}
