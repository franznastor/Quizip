package com.android.quizip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HostQuizActivity extends AppCompatActivity {
    TextView textView;
    Button hostQuiz;
    ArrayList<String> listItems;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_quiz);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        hostQuiz = findViewById(R.id.hostQuizNow);
        textView = findViewById(R.id.textView);
        listItems = new ArrayList<String>();

        hostQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuizHost.getQuizProcessor().createQuiz();
                startActivity(new Intent(getApplicationContext(), HostSettingsActivity.class));
                finish();

            }
        });


        CollectionReference collectionReference = fStore.collection("users").document(fAuth.getCurrentUser().getUid()).collection("quizzes");

        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<String> tempList = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        tempList.add(document.getString("qName"));
                    }

                    Log.d("TAG", listItems.toString());
                    final ListView quizList = (ListView) findViewById(R.id.quizList);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(HostQuizActivity.this, android.R.layout.simple_list_item_1, tempList);
                    quizList.setAdapter(adapter);

                    quizList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            String selectedTest = quizList.getItemAtPosition(i).toString();
                            textView.setText(selectedTest);
                            if (!selectedTest.isEmpty()) {
                                QuizHost.getQParser().setFileName(selectedTest);

                            }
                            else Log.e("TAG", "fileName is Empty");
                        }
                    });


                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }

            }
        });
    }
}
