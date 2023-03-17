package com.melomanya.wordcombine.service;

import com.melomanya.wordcombine.model.WordList;
import com.melomanya.wordcombine.repository.WordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordService {

    private final WordRepository repository;

    public WordService(WordRepository repository) {
        this.repository = repository;
    }

    public List<WordList> findAll() {
        return repository.findAll();
    }

    public WordList save(WordList wordList) {
        return repository.save(wordList);
    }

    public WordList findById(String id) {
        return repository.findById(id).orElse(null);
    }


}
