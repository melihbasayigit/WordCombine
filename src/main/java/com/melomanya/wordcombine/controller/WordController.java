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
    static float totalSimilarityRate = 0;
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
        System.out.println(totalSimilarityRate);
        wordList.setSimilarityRate((totalSimilarityRate / (float) wordList.getList().size()));
        totalSimilarityRate = 0;
        System.out.println(wordList);
        return wordList;
    }

    @PostMapping(path = "/save")
    public WordList save(@RequestBody WordList wordList) {
        System.out.println("SAVE");
        return service.save(wordList);
    }

    private WordList createWordListClass(List<String> strList) {
        long startMillis = System.nanoTime();
        WordList ws = new WordList();
        ws.setList(strList);
        ws.setResult(processWords(ws));
        long lastMillis = System.nanoTime();
        ws.setProcessTime(lastMillis - startMillis);
        return ws;
    }

    private List<String> processWords(WordList wordList) {
        String result = select(wordList.getList().toArray(new String[0]));
        List<String> stringList = new ArrayList<>();
        stringList.add(result);
        return stringList;
        //return process(0, wordList.getList());
    }

    public static String compare_2_strings(String _str1, String _str2) {
        String[] str1 = _str1.split(" ");
        String[] str2 = _str2.split(" ");

        if (str1.length == str2.length && str1.length == 1) {
            //return compareWithChar(str1[0], str2[0]);
        }
        List<String> firstUniques = new ArrayList<>();
        List<String> secondUniques = new ArrayList<>();
        List<String> duplicated = new ArrayList<>();
        boolean isFound = false;
        int index = -1;
        int counter = 0;
        boolean finishTheLoop = false;
        for (int i=0; i<str1.length; i++) {
            if (finishTheLoop)
                break;
            for (int k=0; k<str2.length; k++) {
                if (isFound && !str1[i].equals(str2[k])) {
                    finishTheLoop = true;
                    index = k+1;
                    break;
                }
                if (str1[i].equals(str2[k])) {
                    isFound = true;
                    duplicated.add(str1[i]);
                    counter++;
                    break;
                }
            }
            if (!isFound) {
                firstUniques.add(str1[i]);
            }
        }
        if (index == -1) {
            return _str1;
        }
        else {
            for (int i = index; i<str2.length; i++) {
                secondUniques.add(str2[i]);
            }
        }
        int total;
        if (str1.length < str2.length)
            total = str1.length;
        else
            total = str2.length;
        if ((float)counter / (float)total < 0.1999f) {
            totalSimilarityRate += (float)counter / (float)total;
            return _str1;
        }
        firstUniques.addAll(duplicated);
        firstUniques.addAll(secondUniques);
        return String.join(" ", firstUniques);
    }

    private static String compareWithChar(String _str1, String _str2) {
        char[] str1 = _str1.toCharArray();
        char[] str2 = _str2.toCharArray();
        String firstUniques = "";
        String secondUniques = "";
        String duplicated = "";
        boolean isFound = false;
        for (int i = 0; i< str1.length; i++) {
            for (int k=0; k < str2.length; k++) {
                if (str1[i] == str2[k]) {
                    isFound = true;
                    duplicated += str1[i];
                    break;
                }
                else {
                    if (isFound) {
                        secondUniques += str2[k];
                    }
                    else {
                        firstUniques += str1[i];
                    }
                }
            }
            if (!isFound) {
                firstUniques += str1[i];
            }
        }

        if (!isFound) {
            return null;
        }
        return firstUniques + duplicated + secondUniques;
    }

    private static List<String> process(int startIndex, List<String> _stringList) {
        List<String> stringList = new ArrayList<>(_stringList);
        System.out.println(startIndex);
        System.out.println(stringList);
        boolean isFound = false;
        if (startIndex >= stringList.size() - 1) {
            return stringList;
        }
        for (int i=1+startIndex; i<stringList.size(); i++) {
            //String result = compare_2_strings(stringList.get(startIndex), stringList.get(i));
            String result = compareWords(stringList.get(startIndex), stringList.get(i));
            if (!result.isEmpty()) {
                System.out.println("buldu");
                stringList.set(0, result);
                stringList.remove(i);
                isFound = true;
                process(startIndex, stringList);
                break;
            }
        }
        if (!isFound) {
            process(startIndex+1, stringList);
        }
        return stringList;
    }

    static int compareChars(String myString, String myString2) {
        float shortOne = 0;
        float cont = 0;
        int control = 1;

        if (myString.length() > myString2.length()) {
            shortOne = myString2.length();
        } else {
            shortOne = myString.length();
        }

        if (myString.length() < 4 || myString2.length() < 4) {
            for (int i = 0; i < shortOne; i++) {
                if (myString.charAt(i) != myString2.charAt(i)) {
                    control = 0;
                }
            }
            if (control == 0) {
                return 0;
            } else {
                return 1;
            }

        }

        for (int i = 0; i < shortOne; i++) {
            if (myString.charAt(i) != myString2.charAt(i)) {
                cont = i;
                break;
            } else {
                if (i + 1 == shortOne) {
                    cont = i + 1;
                } else {
                    continue;
                }
            }
        }
        if (cont / shortOne > 0.5) {
            return 1;
        } else {
            return 0;
        }
    }

    static String addFront(int i, int j, String str1[], String str2[]) {// bri gelimeninöncesi varsa onu eklemek için
        String myString = "";
        if (i != 0) {
            if (j != 0) {
                if (i > j) {
                    for (int k = 0; k < i; k++) {
                        myString = myString + " " + str1[k];
                    }
                } else {
                    for (int k = 0; k < j; k++) {
                        myString = myString + " " + str2[k];
                    }

                }
            } else {
                for (int k = 0; k < i; k++) {
                    myString = myString + " " + str1[k];
                }
            }
        } else if (j != 0) {
            for (int k = 0; k < j; k++) {
                myString = myString + " " + str2[k];
            }
        }
        return myString.trim();
    }

    static String addBack(int i, String[] str, String myString) {
        for (int k = i; k < str.length; k++) {
            myString = myString + " " + str[k];
        }
        return myString.trim();
    }

    static String compareWords(String _str1, String _str2) {
        String[] str1 = _str1.split(" ");
        String[] str2 = _str2.split(" ");
        String myString = "";
        boolean addFrotnCont = true;

        for (int i = 0; i < str1.length; i++) {
            for (int j = 0; j < str2.length; j++) {
                if (!str1[i].equalsIgnoreCase(str2[j])) {
                    if (compareChars(str1[i], str2[j]) == 1) {
                        if (addFrotnCont == true) {
                            myString = addFront(i, j, str1, str2);
                            addFrotnCont = false;
                        }

                        if (str1[i].length() > str2[j].length()) {
                            myString = myString + " " + str1[i];
                        } else {
                            myString = myString + " " + str2[j];
                        }

                        if (i + 1 != str1.length) {
                            myString = addBack(i + 1, str1, myString);
                        } else if (j + 1 != str2.length) {
                            myString = addBack(j + 1, str2, myString);
                        }

                        return myString.trim();
                    }
                } else {
                    if (addFrotnCont == true) {
                        myString = addFront(i, j, str1, str2);
                        addFrotnCont = false;
                    }

                    if (j + 1 == str2.length) {
                        //if(i-1>=0)
                        myString = addBack(i, str1, myString);
                        return myString.trim();
                    }

                    if (i + 1 != str1.length) {
                        i++;
                    } else {
                        if(j-1>=0){
                            myString = addBack(j-1, str2, myString);
                        }else{
                            myString = addBack(j, str2, myString);
                        }

                    }
                }
            }
        }
        return myString.trim();

    }

    static String select(String[] stringList) {
        String myString = "";
        String tempString = stringList[0];
        for (int i = 0; i < stringList.length - 1; i++) {
            for (int j = i + 1; j < stringList.length; j++) {
                tempString = compareWords(stringList[i], stringList[j]);
                if (tempString.length() > myString.length()) {
                    myString = tempString;
                }
                tempString = compareWords(myString, stringList[j]);
                if (tempString.length() > myString.length()) {
                    myString = tempString;
                }
            }

        }
        for (int i = 0; i < stringList.length - 1; i++) {
            for (int j = i + 1; j < stringList.length; j++) {
                tempString = compareWords(stringList[i], stringList[j]);
                if (tempString.length() > myString.length()) {
                    myString = tempString;
                }
                tempString = compareWords(myString, stringList[j]);
                if (tempString.length() > myString.length()) {
                    myString = tempString;
                }
            }

        }



        return myString;
    }

    public static void main(String[] args) {
        List<String> stringList = new ArrayList<>();
        stringList.add("Ali eve gel");
        stringList.add("eve gelme dükkana git");
        stringList.add("Ahmet dun gec kaldi");
        stringList.add("gelme dükkana geçmeden yemek yemeyi unutma");
        System.out.println(select(stringList.toArray(stringList.toArray(new String[0]))));
        //System.out.println(process(0, stringList));

        /*
        list.add("The weather is the most important subject in the land. In Europe, \n" +
                "people say, ‘He is the  type of person who talks about the \n" +
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
            */

    }
}