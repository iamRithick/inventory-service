package com.tastygreens.inventoryservice.service;

import com.tastygreens.inventoryservice.model.DbSequences;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MongoDBSequenceGenerator {

    @Autowired
    MongoOperations mongoOperations;

    public long generateSequenceNumber(String sequenceName){
        Query query = new Query(Criteria.where("id").is(sequenceName));
        Update update = new Update().inc("seq",1);
        DbSequences counter = mongoOperations.
                findAndModify(query,update,
                        FindAndModifyOptions.options().returnNew(true).upsert(true),DbSequences.class);
        return  Objects.isNull(counter)?1L:counter.getSeq();
    }
}
