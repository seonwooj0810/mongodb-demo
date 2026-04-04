package com.example.demo.member.repository;

import com.example.demo.member.domain.Member;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MemberRepository extends MongoRepository<Member, String>, MemberCustomRepository, MemberHouseRepository {
}
