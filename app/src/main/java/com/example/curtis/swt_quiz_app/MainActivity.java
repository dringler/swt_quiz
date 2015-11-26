package com.example.curtis.swt_quiz_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public final static String PLAYER_NAME = "com.example.curtis.quizapp_v2.PLAYER_NAME";
    public final static String NUM_QUESTIONS = "com.example.curtis.quizapp_v2.NUM_QUESTIONS";

    // min and max number of Questions
    public final int minQ = 1;
    public final int maxQ = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //restrict the number of questions
        EditText et = (EditText) findViewById(R.id.editTextNumberOfQuestions);
        et.setFilters(new InputFilter[]{new InputFilterMinMax(minQ, maxQ)});

        //set placeholder name
        EditText pn = (EditText) findViewById(R.id.editTextName);
        pn.setText("Dan");
    }

    /** Called when the user clicks the Start Quiz button */
    public void startQuiz(View view) {

        Intent intent = new Intent(this, QuizActivity.class);

        //Get player name
        EditText editTextPlayerName = (EditText) findViewById(R.id.editTextName);
        String playerName = editTextPlayerName.getText().toString();

        //Get number of questions
        EditText editTextNumQuestions = (EditText) findViewById(R.id.editTextNumberOfQuestions);
        String numQuestionsString = editTextNumQuestions.getText().toString();

        //check if player name is entered correctly
        if(TextUtils.isEmpty(playerName)) {
            editTextPlayerName.setError("Please enter your name.");
            return;
        } else {
            intent.putExtra("PLAYER_NAME", playerName);
        }

        //check if number of questions is entered correctly
        if(TextUtils.isEmpty(numQuestionsString)) {
            Toast.makeText(getApplicationContext(), "Please enter the number of questions.", Toast.LENGTH_SHORT).show();
            return;
        } else {
            int numQuestions = Integer.parseInt(editTextNumQuestions.getText().toString());
            intent.putExtra("NUM_QUESTIONS", numQuestions);
        }

        //start quiz activity
        startActivity(intent);

    }
}

