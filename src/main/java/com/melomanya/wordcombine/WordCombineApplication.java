package com.melomanya.wordcombine;

import com.melomanya.wordcombine.repository.WordRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class WordCombineApplication {

    final
    WordRepository wordRepository;

    public WordCombineApplication(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(WordCombineApplication.class, args);
    }

}
