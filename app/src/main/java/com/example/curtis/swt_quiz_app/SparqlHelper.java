package com.example.curtis.swt_quiz_app;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFactory;
import com.hp.hpl.jena.query.ResultSetFormatter;

import java.util.Calendar;
import java.util.Random;


/**
 * Created by curtis on 24/11/15.
 */
public class SparqlHelper {
    //private static final String KEY_ID = "id";
    private static final String KEY_QUES = "question";
    private static final String KEY_OPTA= "opta"; //option a
    private static final String KEY_OPTB= "optb"; //option b
    private static final String KEY_OPTC= "optc"; //option c
    private static final String KEY_OPTD = "optd"; //option d
    private static final String KEY_ANSWER = "answer"; //correct option

    public Question getNewQuestion(int diff) {
       // String[] q = new String[5]; //index: 0=question, 1=right answer, 1-4=answer choices
        Question q = new Question();
        QueryString qs = new QueryString();

        String queryString = "";
        String artist = "";
        String startYear = "";
        String endYear = "";
        //random question type
        //int qType = randInt(0, 1); //randInt(min, max)
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);

        int difficulty = diff;
        int qType = 0; //0:Carrer start of artist
        queryString = qs.getQueryString(qType);

        Query query = QueryFactory.create(queryString);
        String service = "http://dbpedia.org/sparql";
        QueryExecution qexec = QueryExecutionFactory.sparqlService(service, query);

        try {

            //ResultSet results = ResultSetFactory.copyResults(qexec.execSelect());
            ResultSet results = qexec.execSelect();
            ResultSet output = ResultSetFactory.copyResults(qexec.execSelect());
            System.out.println(ResultSetFormatter.asText(output));
            //ResultSetFormatter.out(System.out, output);
            //StringBuffer resultsBuffer = new StringBuffer();

            //List<String> columnNames = results.getResultVars();

            while (results.hasNext()) {
                QuerySolution sol = results.next();
                ResultVariables resultVariables = new ResultVariables();
                //get artist
               artist = resultVariables.getArtist(sol);

                //get dboStartYear
                startYear = resultVariables.getStartYear(sol);

                //get dboEndYear (if available)
               endYear = resultVariables.getEndYear(sol);


                q.setQUESTION("When did the musical career of " + artist + " start?");
                q.setANSWER(startYear);

                boolean w1search = true;
                boolean w2search = true;
                boolean w3search = true;
                String rightAnswer = "";
                String wrongAnswer1 = "";
                String wrongAnswer2 = "";
                String wrongAnswer3 = "";
                int wrongAnswerInt1 = 0;
                int wrongAnswerInt2 = 0;
                int wrongAnswerInt3 = 0;

                //ger right answer
                rightAnswer = startYear;

                //get first wrong answer
                while (w1search) {
                    AnswerOptions w1 = new AnswerOptions();
                    wrongAnswerInt1 = w1.getYearNumber(rightAnswer, difficulty);
                    wrongAnswer1 = String.valueOf(wrongAnswerInt1);
                    if (!wrongAnswer1.equals(rightAnswer) &&
                            wrongAnswerInt1 <= currentYear) {
                        w1search = false;
                    }
                }

                //get second wrong answer
                while (w2search) {
                    AnswerOptions w2 = new AnswerOptions();
                    wrongAnswerInt2 = w2.getYearNumber(rightAnswer, difficulty);
                    wrongAnswer2 = String.valueOf(wrongAnswerInt2);
                    if (!wrongAnswer2.equals(rightAnswer) &&
                            !wrongAnswer2.equals(wrongAnswer1) &&
                            wrongAnswerInt2 <= currentYear) {
                        w2search = false;
                    }
                }

                //get third wrong answer
                while (w3search) {
                    AnswerOptions w3 = new AnswerOptions();
                    wrongAnswerInt3 = w3.getYearNumber(rightAnswer, difficulty);
                    wrongAnswer3 = String.valueOf(wrongAnswerInt3);
                    if (!wrongAnswer3.equals(rightAnswer) &&
                            !wrongAnswer3.equals(wrongAnswer1) &&
                            !wrongAnswer3.equals(wrongAnswer2) &&
                            wrongAnswerInt3 <= currentYear) {
                        w3search = false;
                    }
                }

                RandomInt randomAnswer = new RandomInt();
                int answerButton = randomAnswer.getRandInt(1, 4);

                switch (answerButton) {
                    case 1:
                        q.setOPTA(rightAnswer);
                        q.setOPTB(wrongAnswer1);
                        q.setOPTC(wrongAnswer2);
                        q.setOPTD(wrongAnswer3);
                        break;
                    case 2:
                        q.setOPTA(wrongAnswer1);
                        q.setOPTB(rightAnswer);
                        q.setOPTC(wrongAnswer2);
                        q.setOPTD(wrongAnswer3);
                        break;
                    case 3:
                        q.setOPTA(wrongAnswer1);
                        q.setOPTB(wrongAnswer2);
                        q.setOPTC(rightAnswer);
                        q.setOPTD(wrongAnswer3);
                        break;
                    case 4:
                        q.setOPTA(wrongAnswer1);
                        q.setOPTB(wrongAnswer2);
                        q.setOPTC(wrongAnswer3);
                        q.setOPTD(rightAnswer);
                        break;
                }

                // System.out.println(resultsBuffer.toString());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        qexec.close();


        return q;
    }

}
