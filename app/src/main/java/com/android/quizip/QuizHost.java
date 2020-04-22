package com.android.quizip;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

public class QuizHost {
    private static Question currentQuestion;
    private static QuizProcessor quizProcessor;
    private static QParser parser;


    public static void getNextQuestion(Context context) {
        //TODO for some reason the arraylist of questions is empty here and i have no clue why
        if (!quizProcessor.getMixed().isEmpty()) {
            currentQuestion = quizProcessor.getQuestion();
            if (currentQuestion.getType() == QTypes.TRUE_FALSE) {
                context.startActivity(new Intent(context.getApplicationContext(), TFTakeQuizActivity.class));

            } else if (currentQuestion.getType() == QTypes.MATCHING) {
                context.startActivity(new Intent(context.getApplicationContext(), MatchingTakeQuizActivity.class));

            } else if (currentQuestion.getType() == QTypes.MULTIPLE_CHOICE) {
                context.startActivity(new Intent(context.getApplicationContext(), MultipleChoiceTakeQuizActivity.class));
            }
            //TODO method for ending the quiz
        }
        else Log.e("TAG", "mixed is empty");

   }

    public static Question getCurrentQuestion(){
        return currentQuestion;
    }

    public static void setQuizProcessor(QuizProcessor newQuizProcessor){
        quizProcessor = newQuizProcessor;
    }

    public static QParser getQParser(){
        return parser;
    }

    public static void setQParser(QParser qParser){
        parser = qParser;
    }

    public static QuizProcessor getQuizProcessor(){
        return quizProcessor;
    }
}
