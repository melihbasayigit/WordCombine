package com.melomanya.wordcombine.config;

import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

public class MongoDBConfig extends AbstractMongoClientConfiguration {


    @Override
    protected String getDatabaseName() {
        return "wordappdatabase";
    }
}
