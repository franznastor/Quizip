
package com.android.quizip;



import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class QuizProcessor {
    private ArrayList<TrueFalse> trueFalse = new ArrayList<>();
    private ArrayList<Matching> matching = new ArrayList<>();
    private ArrayList<MultipleChoice> multipleChoice = new ArrayList<>();
    private ArrayList<Question> mixed = new ArrayList<>();
    private int counter = 0;


    public QuizProcessor() {

    }

    public Question getQuestion() {
        if (!mixed.isEmpty()) {
            Question temp = mixed.get(0);
            mixed.remove(0);
            return temp;
        } else
            //TODO method for ending a quiz
            return null;
    }

    public void resetCounter() {
        counter = 0;
    }


    public void setTrueFalse(ArrayList<TrueFalse> trueFalse){
        for (int i = 0; i < trueFalse.size(); i++){
            this.trueFalse.add(trueFalse.get(i));
        }
        if (!this.trueFalse.isEmpty()){
            Log.e("TAG", "Successfully wrote to true false in quiz processor");
        }
    }

    public void setMatching(ArrayList<Matching> matching){
        for (int i = 0; i < matching.size(); i++){
            this.matching.add(matching.get(i));
        }
        if (!this.matching.isEmpty()){
            Log.e("TAG", "Successfully wrote to matching in quiz processor");
        }
    }

    public void setMultipleChoice(ArrayList<MultipleChoice> multipleChoice){
        for (int i = 0; i < multipleChoice.size(); i++){
            this.multipleChoice.add(multipleChoice.get(i));
        }
        if (!this.multipleChoice.isEmpty()){
            Log.e("TAG", "Successfully wrote to multiple choice in quiz processor");
        }
    }

    public void createQuiz(){
        QuizHost.getQParser().parseTrueFalse();
        QuizHost.getQParser().parseMatching();
        QuizHost.getQParser().parseMultipleChoice();

           for (int i = 0; i < trueFalse.size(); i++) {
               mixed.add(trueFalse.get(i));
           }

        for (int i = 0; i < matching.size(); i++) {
            mixed.add(matching.get(i));
        }
        for (int i = 0; i < multipleChoice.size(); i++) {
            mixed.add(multipleChoice.get(i));
        }
        Collections.shuffle(mixed);
    }

    public ArrayList<Question> getMixed(){
        return mixed;
    }

    public boolean checkAnswer(String userAnswer, Question question) {
        QTypes type = question.getType();

        if (QTypes.TRUE_FALSE.equals(type)) {
            return userAnswer.equalsIgnoreCase((String) question.getAnswers().get(0));
        }

        else if (QTypes.MATCHING.equals(type)) {

            if (question.getAnswers().get(counter).equals(userAnswer)) {
                counter++;
                return true;
            } else {
                counter++;
                return false;
            }

        } else if (QTypes.MULTIPLE_CHOICE.equals(type)) {
            MultipleChoice q = (MultipleChoice) question;
            for (Map.Entry<String, String> entry : q.getAnswers()) {
                if (userAnswer.equalsIgnoreCase(entry.getKey()) && entry.getValue().equals("C")) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    public Boolean mixedHasNext() {
        if (!mixed.isEmpty()) {
            return true;
        } else return false;
    }


}

