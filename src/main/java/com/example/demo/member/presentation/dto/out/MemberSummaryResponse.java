package com.example.demo.member.presentation.dto.out;

import java.time.LocalDateTime;

import com.example.demo.member.domain.MemberType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonTypeInfo(
	use = JsonTypeInfo.Id.NAME,
	property = "memberType",
	include = JsonTypeInfo.As.EXISTING_PROPERTY,
	visible = true
)
@JsonSubTypes({
	@JsonSubTypes.Type(value = MemberAddressSummaryResponse.class, name = "ADDRESS"),
	@JsonSubTypes.Type(value = MemberHouseSummaryResponse.class, name = "HOUSE")
})
@Schema(
	oneOf = {MemberAddressSummaryResponse.class, MemberHouseSummaryResponse.class},
	discriminatorProperty = "memberType",
	discriminatorMapping = {
		@DiscriminatorMapping(value = "ADDRESS", schema = MemberAddressSummaryResponse.class),
		@DiscriminatorMapping(value = "HOUSE", schema = MemberHouseSummaryResponse.class)
	}
)
public sealed interface MemberSummaryResponse
	permits MemberAddressSummaryResponse, MemberHouseSummaryResponse {
	String id();
	String name();
	String email();
	MemberType memberType();
	LocalDateTime createdAt();
	LocalDateTime updatedAt();
}
