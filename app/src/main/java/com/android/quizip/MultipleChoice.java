package com.android.quizip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class MultipleChoice implements Question {
    private String question;
    private ArrayList<Map.Entry<String, String>> answers = new ArrayList<>();

    public MultipleChoice(String question, Map<String, String> answers) {
        this.question = question;

        for (Map.Entry<String, String> set : answers.entrySet()){
            this.answers.add(set);
        }
    }

    @Override
    public QTypes getType() {
        return QTypes.MULTIPLE_CHOICE;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    public ArrayList<String> getAnswerStrings() {
        ArrayList<String> answerStrings = new ArrayList<>();
        for (Map.Entry<String, String> entry : answers) {
            answerStrings.add(entry.getKey());
        }

        Collections.shuffle(answerStrings);
        return answerStrings;
    }
    @Override
    public String toString(){
        String formatted = question + ": ";

        formatted.concat(answers.get(0) + " ");

        return formatted;
    }

    @Override
    public ArrayList<Map.Entry<String, String>> getAnswers() {
        return answers;
    }
}
