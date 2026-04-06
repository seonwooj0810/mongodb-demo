package com.example.demo.config;

import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import com.example.demo.member.domain.MemberWithAddress;
import com.example.demo.member.domain.MemberWithHouse;

@Configuration
public class MongoConfig {

	@Bean
	public MongoMappingContext mongoMappingContext(MongoCustomConversions conversions) {
		MongoMappingContext context = new MongoMappingContext();
		context.setInitialEntitySet(Set.of(MemberWithAddress.class, MemberWithHouse.class));
		context.setSimpleTypeHolder(conversions.getSimpleTypeHolder());
		return context;
	}

	@Bean
	public MappingMongoConverter mappingMongoConverter(
		MongoDatabaseFactory factory,
		MongoCustomConversions conversions,
		MongoMappingContext context
	) {
		DbRefResolver resolver = new DefaultDbRefResolver(factory);
		MappingMongoConverter converter = new MappingMongoConverter(resolver, context);
		converter.setCustomConversions(conversions);
		converter.setCodecRegistryProvider(factory);
		converter.setTypeMapper(new DefaultMongoTypeMapper("memberType"));
		return converter;
	}
}
