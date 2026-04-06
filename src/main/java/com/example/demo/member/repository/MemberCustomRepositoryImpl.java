package com.example.demo.member.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import com.example.demo.member.domain.Member;
import com.example.demo.member.domain.MemberType;
import com.example.demo.member.domain.MemberWithAddress;
import com.example.demo.member.domain.MemberWithHouse;
import com.example.demo.member.presentation.dto.in.MemberSearchRequest;
import com.example.demo.member.presentation.dto.in.UpdateAddressRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {

	private final MongoTemplate mongoTemplate;

	@Override
	public Page<Member> searchByName(MemberSearchRequest request) {
		Query query = new Query().with(request.toPageable());

		if (StringUtils.hasText(request.name())) {
			query.addCriteria(Criteria.where("name").regex(request.name(), "i"));
		}

		Class<? extends Member> entityClass = resolveEntityClass(request.type());
		List<Member> members = (List<Member>) (List<?>) mongoTemplate.find(query, entityClass);

		return PageableExecutionUtils.getPage(
			members,
			request.toPageable(),
			() -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), entityClass)
		);
	}

	@Override
	public long updateAddress(String memberId, String addressId, UpdateAddressRequest request) {
		Query query = Query.query(
			Criteria.where("_id").is(memberId)
				.and("addresses.addressId").is(addressId)
		);

		Update update = new Update()
			.set("addresses.$.label", request.label())
			.set("addresses.$.zipCode", request.zipCode())
			.set("addresses.$.city", request.city())
			.set("addresses.$.street", request.street())
			.set("addresses.$.detail", request.detail())
			.set("addresses.$.defaultAddress", request.defaultAddress());

		return mongoTemplate.updateFirst(query, update, MemberWithAddress.class).getModifiedCount();
	}

	private Class<? extends Member> resolveEntityClass(MemberType type) {
		if (type == null) return Member.class;
		return switch (type) {
			case ADDRESS -> MemberWithAddress.class;
			case HOUSE -> MemberWithHouse.class;
		};
	}
}
