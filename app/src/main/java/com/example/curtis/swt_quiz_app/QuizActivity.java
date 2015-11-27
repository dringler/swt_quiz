package com.example.curtis.swt_quiz_app;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class QuizActivity extends AppCompatActivity {

    List<Question> quesList;
    int score=0;
    int qid=0;
    int difficulty=0;
    int numOfQuestions = 0;
    Question currentQ;
    TextView txtQuestion;
    Button bA, bB, bC, bD;
//    RadioButton rda, rdb, rdc;
//    Button butNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //DB class
//        DbHelper db=new DbHelper(this);
//        quesList=db.getAllQuestions();
//        currentQ=quesList.get(qid);

        //SparqlHelper class
//        SparqlHelper sp = new SparqlHelper();
        //currentQ = sp.getNewQuestion();

        Bundle extras = getIntent().getExtras();
        difficulty = extras.getInt("DIFFICULTY");
        numOfQuestions = extras.getInt("NUM_QUESTIONS");


        AsyncTask<Void, Void, Question> execute = new newQ().execute();
        try {
            currentQ = execute.get();
        } catch (Exception e) {
            System.out.println(e);
        }

        txtQuestion=(TextView)findViewById(R.id.textViewQuestion);
        bA=(Button)findViewById(R.id.buttonAnswer1);
        bB=(Button)findViewById(R.id.buttonAnswer2);
        bC=(Button)findViewById(R.id.buttonAnswer3);
        bD=(Button)findViewById(R.id.buttonAnswer4);

        setQuestionView();

        bA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button answer = (Button) findViewById(R.id.buttonAnswer1);
                if (currentQ.getANSWER().equals(answer.getText())) {
                    score++;
                    rightA();
                } else {
                    wrongA();
                }
                Log.d("buttonA", "Button answer: " + answer);
                Log.d("answer.getText", "answer.getText(): " + answer.getText());
                Log.d("answer", "currentQ.getAnswer(): " + currentQ.getANSWER());
                Log.d("score", "score: " + score);
                checkQid();
            }
        });
        bB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button answer = (Button) findViewById(R.id.buttonAnswer2);
                if (currentQ.getANSWER().equals(answer.getText())) {
                    score++;
                    rightA();
                } else {
                    wrongA();
                }
                checkQid();
            }
        });
        bC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button answer = (Button) findViewById(R.id.buttonAnswer3);
                if (currentQ.getANSWER().equals(answer.getText())) {
                    score++;
                    rightA();
                } else {
                    wrongA();
                }
                checkQid();
            }
        });
        bD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button answer = (Button) findViewById(R.id.buttonAnswer4);
                if (currentQ.getANSWER().equals(answer.getText())) {
                    score++;
                    rightA();
                } else {
                    wrongA();
                }
                checkQid();
            }
        });
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//// Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.activity_quiz, menu);
//        return true;
//    }

    private void rightA() {
        Toast toastA = Toast.makeText(getApplicationContext(), "Correct! =)",
                Toast.LENGTH_SHORT);
        TextView va = (TextView) toastA.getView().findViewById(android.R.id.message);
        if( va != null) va.setGravity(Gravity.CENTER);
        toastA.show();
    }
    private void wrongA() {
        Toast toastW = Toast.makeText(getApplicationContext(), "Wrong! =(\n" +currentQ.getANSWER() + " is the correct answer.",
                Toast.LENGTH_SHORT);
        TextView vw = (TextView) toastW.getView().findViewById(android.R.id.message);
        if( vw != null) vw.setGravity(Gravity.CENTER);
        toastW.show();
    }

    private  void checkQid() {
        if (qid < numOfQuestions) {
            //currentQ = quesList.get(qid);
            AsyncTask<Void, Void, Question> execute = new newQ().execute();
            try {
                currentQ = execute.get();
            } catch (Exception e) {
                System.out.println(e);
            }
            setQuestionView();
        } else {

            Bundle extras = getIntent().getExtras();
            String playerName = extras.getString("PLAYER_NAME");
            int numQuestions = extras.getInt("NUM_QUESTIONS");

//            extras.putInt("score", score); //Your score
//            getIntent().putExtras(extras); //Put your score to your next Intent

            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("score", score);
            intent.putExtra("PLAYER_NAME", playerName);
            intent.putExtra("NUM_QUESTIONS", numQuestions);
            startActivity(intent);

//
//            Intent mIntent = new Intent(this, ResultActivity.class);
//            Bundle mBundle = new Bundle();
//            mBundle.putInt("score", score);
//            mIntent.putExtras(mBundle);
//            startActivity(mIntent);



            finish();
        }
    }
    private void setQuestionView()
    {
        txtQuestion.setText(currentQ.getQUESTION());
        bA.setText(currentQ.getOPTA());
        bB.setText(currentQ.getOPTB());
        bC.setText(currentQ.getOPTC());
        bD.setText(currentQ.getOPTD());
        qid++;
    }

    public void backMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    private class newQ extends AsyncTask<Void, Void, Question> {
        SparqlHelper sp = new SparqlHelper();
        private Exception exception;

        protected Question doInBackground(Void... params) {
            try {
                Question nq = sp.getNewQuestion(difficulty);
                return nq;
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }
    }

}
