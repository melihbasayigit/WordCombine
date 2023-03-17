package com.melomanya.wordcombine.repository;

import com.melomanya.wordcombine.model.WordList;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WordRepository extends MongoRepository<WordList, String> {

    List<WordList> findById();

}
