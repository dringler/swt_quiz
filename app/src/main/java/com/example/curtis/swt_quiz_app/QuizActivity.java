package com.example.curtis.swt_quiz_app;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    List<Question> quesList;
    int score = 0;
    int qid = 0;
    int difficulty = 0;
    int numOfQuestions = 0;
    Question currentQ;
    TextView txtQuestion;
    Button bA, bB, bC, bD;
    List<String> bands = new ArrayList<String>();
    List<String> inactiveBands = new ArrayList<String>();
    List<String> musicians = new ArrayList<String>();
    List<String> inactiveMusicians = new ArrayList<String>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

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

        CsvParserClass csvParser = new CsvParserClass();

        bands = csvParser.getCSV("bands_10.csv");
        inactiveBands = csvParser.getCSV("inactive_bands_10.csv");
        musicians = csvParser.getCSV("musicians_10.csv");
        inactiveMusicians = csvParser.getCSV("inactive_musicians_10.csv");


        AsyncTask<Void, Void, Question> execute = new NewQ().execute();
        try {
            currentQ = execute.get();
        } catch (Exception e) {
            System.out.println(e);
        }

        txtQuestion = (TextView) findViewById(R.id.textViewQuestion);
        bA = (Button) findViewById(R.id.buttonAnswer1);
        bB = (Button) findViewById(R.id.buttonAnswer2);
        bC = (Button) findViewById(R.id.buttonAnswer3);
        bD = (Button) findViewById(R.id.buttonAnswer4);

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
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
        if (va != null) va.setGravity(Gravity.CENTER);
        toastA.show();
    }

    private void wrongA() {
        Toast toastW = Toast.makeText(getApplicationContext(), "Wrong! =(\n" + currentQ.getANSWER() + " is the correct answer.",
                Toast.LENGTH_SHORT);
        TextView vw = (TextView) toastW.getView().findViewById(android.R.id.message);
        if (vw != null) vw.setGravity(Gravity.CENTER);
        toastW.show();
    }

    private void checkQid() {
        if (qid < numOfQuestions) {
            //currentQ = quesList.get(qid);
            AsyncTask<Void, Void, Question> execute = new NewQ().execute();
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

            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("score", score);
            intent.putExtra("PLAYER_NAME", playerName);
            intent.putExtra("NUM_QUESTIONS", numQuestions);
            startActivity(intent);

            finish();
        }
    }

    private void setQuestionView() {
        try {
            txtQuestion.setText(currentQ.getQUESTION());
            bA.setText(currentQ.getOPTA());
            bB.setText(currentQ.getOPTB());
            bC.setText(currentQ.getOPTC());
            bD.setText(currentQ.getOPTD());
            qid++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void backMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private class NewQ extends AsyncTask<Void, Void, Question> {
        SparqlHelper sp = new SparqlHelper();
        private Exception exception;

        protected Question doInBackground(Void... params) {
            try {
                Question nq = sp.getNewQuestion(difficulty, bands, inactiveBands, musicians, inactiveMusicians);
                return nq;
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }
    }

    private class CsvParserClass {
        private List<String> getCSV(String filepath) {
            List<String> returnList = new ArrayList<String>();

            InputStreamReader is = null;
            try {
                is = new InputStreamReader(getAssets()
                        .open(filepath));

                BufferedReader reader = new BufferedReader(is);
                reader.readLine();
                String data;
                while ((data = reader.readLine()) != null) {
                    String[] line = data.split(",");
                    if (line.length > 1) {
                        returnList.add(line[0]);
                        String artist = line[0];
                        String rank = line[1];
                        Log.d("csv output", "added artist: " + artist + " with rank: " + rank + " to ArrayList");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        return returnList;
        }

    }
    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Quiz Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.curtis.swt_quiz_app/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Quiz Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.curtis.swt_quiz_app/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


}
