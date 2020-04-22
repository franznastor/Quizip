package com.android.quizip;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MultipleChoiceTakeQuizActivity extends AppCompatActivity {
    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice_take_quiz);

        next = findViewById(R.id.multipleChoiceNextButton);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuizHost.getNextQuestion(MultipleChoiceTakeQuizActivity.this.getApplicationContext());
            }
        });
    }
}
