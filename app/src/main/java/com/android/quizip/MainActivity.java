package com.android.quizip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    Button logoutBtn, createBtn, takeBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    DocumentReference documentReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logoutBtn = findViewById(R.id.logout);
        createBtn = findViewById(R.id.createQuiz);
        takeBtn = findViewById(R.id.takeQuiz);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CreateActivity.class));
                finish();
            }
        });

        takeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TakeQuizActivity.class));
                finish();
            }
        });

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        documentReference = fStore.collection("users").document(fAuth.getCurrentUser().getUid()).collection("quizzes").document("test quiz");

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Map<String, Map<String, String>> tempMap;
                ArrayList<Matching> matching = new ArrayList<>();
                if (task.isSuccessful()){
                    tempMap = (Map<String, Map<String, String>>) task.getResult().get("matching");
                    TextView testView = findViewById(R.id.testView);
                    for (Map.Entry<String, Map<String, String>> set : tempMap.entrySet()) {
                        matching.add(new Matching(set.getKey(), set.getValue()));
                        testView.append(tempMap.toString());
                    }
                }
           }
        });

    }

}
