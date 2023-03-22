package com.melomanya.wordcombine.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;
import java.util.List;

@Data
@Document("wordlist")
public class WordList {

    @Id
    private String id;

    private List<String> list;
    private String result;
    private long processTime;
    private float similarityRate;

    public WordList() {}

    public WordList(String id, List<String> list, String result, long processTime, float similarityRate, float frequency) {
        this.id = id;
        this.list = list;
        this.result = result;
        this.processTime = processTime;
        this.similarityRate = similarityRate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public long getProcessTime() {
        return processTime;
    }

    public void setProcessTime(long processTime) {
        this.processTime = processTime;
    }

    public float getSimilarityRate() {
        return similarityRate;
    }

    public void setSimilarityRate(float similarityRate) {
        this.similarityRate = similarityRate;
    }
}
