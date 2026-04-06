package com.example.demo.member.repository;

import java.util.List;

import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.example.demo.member.domain.MemberHouse;
import com.example.demo.member.domain.MemberWithHouse;
import com.example.demo.member.presentation.dto.in.MemberHouseKeyRequest;
import com.example.demo.member.presentation.dto.in.MemberHouseRequest;
import com.example.demo.member.presentation.dto.in.UpdateHouseItemRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberHouseRepositoryImpl implements MemberHouseRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public boolean existsHouse(String memberId, String block, String unit) {
        Query query = Query.query(
            Criteria.where("_id").is(memberId)
                .and("houses").elemMatch(
                    Criteria.where("block").is(block).and("unit").is(unit)
                )
        );
        return mongoTemplate.exists(query, MemberWithHouse.class);
    }

    @Override
    public void addHouse(String memberId, MemberHouseRequest request) {
        Query query = Query.query(Criteria.where("_id").is(memberId));
        Update update = new Update().push("houses", new MemberHouse(request.block(), request.unit(), null, null, null));
        mongoTemplate.updateFirst(query, update, MemberWithHouse.class);
    }

    @Override
    public int updateHouses(String memberId, List<UpdateHouseItemRequest> requests) {
        BulkOperations bulkOps = mongoTemplate.bulkOps(BulkOperations.BulkMode.ORDERED, MemberWithHouse.class);

        for (UpdateHouseItemRequest request : requests) {
            Query query = Query.query(
                Criteria.where("_id").is(memberId)
                    .and("houses").elemMatch(
                        Criteria.where("block").is(request.block()).and("unit").is(request.unit())
                    )
            );

            Update update = new Update()
                .set("houses.$.nickname", request.nickname())
                .set("houses.$.memo", request.memo())
                .set("houses.$.floor", request.floor());

            bulkOps.updateOne(query, update);
        }

        return bulkOps.execute().getModifiedCount();
    }

    @Override
    public long removeHouses(String memberId, List<MemberHouseKeyRequest> keys) {
        Query query = Query.query(Criteria.where("_id").is(memberId));

        List<Criteria> conditions = keys.stream()
            .map(key -> Criteria.where("block").is(key.block()).and("unit").is(key.unit()))
            .toList();

        Update update = new Update().pull("houses",
            Query.query(new Criteria().orOperator(conditions)));

        return mongoTemplate.updateFirst(query, update, MemberWithHouse.class).getModifiedCount();
    }
}
