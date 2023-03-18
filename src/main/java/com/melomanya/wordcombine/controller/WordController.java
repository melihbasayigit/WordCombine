package com.melomanya.wordcombine.controller;

import com.melomanya.wordcombine.model.WordList;
import com.melomanya.wordcombine.service.WordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/word")
public class WordController {

    private final WordService service;

    public WordController(WordService service) {
        this.service = service;
    }

    @GetMapping(path = "/get")
    public List<WordList> getTest() {
        return service.findAll();
    }

    @GetMapping(path = "/get/{id}")
    public WordList getById(@PathVariable String id) {
        return service.findById(id);
    }

    @PostMapping(path = "/insert")
    public WordList insertWords(@RequestBody List<String> stringList) {
        WordList wordList = createWordListClass(stringList);
        return service.save(wordList);
    }

    private WordList createWordListClass(List<String> strList) {
        long startMillis = System.currentTimeMillis();
        System.out.println(startMillis);
        WordList ws = new WordList();
        ws.setList(strList);
        ws.setResult(processWords(ws));
        long lastMillis = System.currentTimeMillis();
        System.out.println(lastMillis);
        ws.setProcessTime(lastMillis - startMillis);
        return ws;
    }

    private String processWords(WordList wordList) {

        return "result";
    }



}
