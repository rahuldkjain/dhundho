package com.dundho.search.repository;

import com.dundho.search.entity.Mongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MongoRepo extends MongoRepository<Mongo,Long> {
    List<String> findByVal(String val);
}
