package com.android.quizip;

import java.util.ArrayList;
import java.util.Map;

public class Matching implements Question {
    private String statement;
    private ArrayList<String> matchingKey = new ArrayList<String>();
    private ArrayList<String> answers = new ArrayList<>();
    private ArrayList<String> tempArray, tempArrayTwo;

    public Matching(String statement, Map<String, String> questions) {
        this.statement = statement;

        for (Map.Entry<String, String> set : questions.entrySet()) {
            matchingKey.add(set.getKey());
            answers.add(set.getValue());
        }
        tempArray = matchingKey;
        tempArrayTwo = matchingKey;
    }

    @Override
    public QTypes getType() {
        return QTypes.MATCHING;
    }

    @Override
    public String getQuestion() {
        return statement;
    }

    public String getNextStatement() {
        String temp = null;
        if (!tempArrayTwo.isEmpty()) {
            temp = tempArrayTwo.get(0);
            tempArrayTwo.remove(0);
        }
        return temp;
    }

    public String getNextMatchingKey() {
        String temp = null;
        if (!tempArray.isEmpty()) {
            temp = tempArray.get(0);
            tempArray.remove(0);
        }
        return temp;
    }

    @Override
    public ArrayList<String> getAnswers() {
        return answers;
    }

    public ArrayList<String> getMatchingKey() {
        return matchingKey;
    }

    public int getMatchingKeySize() {
        return matchingKey.size();
    }

    @Override
    public String toString(){
        String formatted = statement + ": ";

        for (String temp : matchingKey) {
            formatted.concat(temp + " ");
        }
        for (String temp : answers) {
            formatted.concat(temp + " ");
        }
        return formatted;
    }
}