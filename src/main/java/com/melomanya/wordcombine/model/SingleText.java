package com.melomanya.wordcombine.model;

import java.util.ArrayList;
import java.util.List;

public class SingleText {

    private String text;

    public SingleText() {

    }

    public SingleText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static List<String> convert(List<SingleText> singleTextList) {
        ArrayList<String> strList = new ArrayList<>();
        for (SingleText singleText : singleTextList) {
            strList.add(singleText.text);
        }
        return strList;
    }
}
