package com.melomanya.wordcombine.controller;

import com.melomanya.wordcombine.model.SingleText;
import com.melomanya.wordcombine.model.WordList;
import com.melomanya.wordcombine.service.WordService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public WordList insertWords(@RequestBody List<SingleText> singleTextList) {
        System.out.println("abc");
        List<String> stringList = SingleText.convert(singleTextList);
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
        double similarityRate = 0.199999f;

        return "result";
    }

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("The weather is the most important subject in the land. In Europe, \n" +
                "people say, ‘He is the type of person who talks about the \n" +
                "weather,’ to show that somebody is very boring. In England, the \n" +
                "weather is always an interesting, exciting subject and you must be \n" +
                "good at talking about it.");
        list.add("is always an interesting, exciting subject and you must be \n" +
                "good at talking about it.\n" +
                "\n" +
                "George Mikes wrote this book to tell the English what he thought \n" +
                "about them. He is both funny and rude about the strange things \n" +
                "English people do and say - the things that make them different \n" +
                "from other Europeans. In this book you will learn many useful \n" +
                "rules about being English.");
        list.add("George Mikes wrote this book to tell the English what he thought \n" +
                "about them. He is both funny and rude about the strange things \n" +
                "English people do and say - the things that make them different \n" +
                "from other Europeans. In this book you will learn many useful \n" +
                "rules about being English. You will learn how to talk about the \n" +
                "weather, and what to say when somebody brings you a cup of tea \n" +
                "at 5 o’clock in the morning. You will discover what the English \n" +
                "really think of clever people and doctors. ");
        list.add("You will discover what the English \n" +
                "really think of clever people and doctors. This book will help you \n" +
                "to be more like the English. As George Mikes says: ‘If you are \n" +
                "like the English, they think you are funny. If you are not like \n" +
                "them, they think you are even funnier.’\n" +
                "\n" +
                "George Mikes was born in Hungary in 1921. He studied law at \n" +
                "Budapest University, and then began to write for newspapers. He \n" +
                "came to London for two weeks just before the Second World War \n" +
                "began, and made England his home for the rest of his life. During \n" +
                "the war he worked for the BBC, making radio programmes for \n" +
                "Hungary.");


    }




}
