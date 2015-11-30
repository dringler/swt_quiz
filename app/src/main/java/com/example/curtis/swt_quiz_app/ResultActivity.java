package com.example.curtis.swt_quiz_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //get text view
        TextView congrat = (TextView) findViewById(R.id.textViewCongratulations);
        TextView correctA = (TextView) findViewById(R.id.textViewCorrectAnswers);
        TextView wrongA = (TextView) findViewById(R.id.textViewWrongAnswers);
        TextView percentageC = (TextView) findViewById(R.id.textViewResultPercentage);
        TextView userMsg = (TextView) findViewById(R.id.textViewUserMsg);

        //get values
        Bundle extras = getIntent().getExtras();
        int score = extras.getInt("score");
        String playerName = extras.getString("PLAYER_NAME");
        int totalQ = extras.getInt("NUM_QUESTIONS");

        //display values
        congrat.setText("Congratulations " + playerName + "!");
        correctA.setText(String.valueOf(score));
        wrongA.setText(String.valueOf(totalQ - score));
        double perResult = (( (double) score / (double) totalQ) * 100);
        int perResultInt = (int) perResult;
        String perResultText = String.valueOf(perResultInt);
        percentageC.setText(perResultText + "%");

        if (perResultInt < 25) {
            userMsg.setText("Try again!");
        } else if (perResultInt < 50) {
            userMsg.setText("Almost good =)");
        } else if (perResultInt < 75) {
            userMsg.setText("Well done! =)");
        } else {
            userMsg.setText("Excellent! =)");

        }

    }

    public void backMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

}
