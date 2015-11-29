package com.example.curtis.swt_quiz_app;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFactory;
import com.hp.hpl.jena.query.ResultSetFormatter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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

    public Question getNewQuestion(int diff,  List<String> b,  List<String> iB,  List<String> m,  List<String> iM) {

        int difficulty = diff;
        List<String> bands = b;
        List<String> inactiveBands = iB;
        List<String> musicians = m;
        List<String> inactiveMusicians = iM;

        int qType = 0; //question type
        int listNum = 0; //band/artist identifier, 0:band, 1:musicalArtist


        String queryString = "";
        String artist = "";
        String startYear = "";
        String endYear = "";

        Question q = new Question();
        QueryString qs = new QueryString();

        //random question type
        RandomInt randomIntQ = new RandomInt();
        qType = randomIntQ.getRandInt(0, 1); //randInt(min, max)

        RandomInt randomIntL = new RandomInt();
        switch (qType) {
            case 0: //0:career start of artist/band
                listNum = randomIntL.getRandInt(0, 1);
                if (listNum == 0) { //band
                    queryString = qs.getQueryString(qType, listNum, bands);
                } else { //musician
                    queryString = qs.getQueryString(qType, listNum, musicians);
                }
                break;
            case 1: //1:career end of artist/band
                listNum = randomIntL.getRandInt(0, 1);
                if (listNum == 0) { //band
                    queryString = qs.getQueryString(qType, listNum, inactiveBands);
                } else { //musician
                    queryString = qs.getQueryString(qType, listNum, inactiveMusicians);
                }
                break;
        }



        Query query = QueryFactory.create(queryString);
        String service = "http://dbpedia.org/sparql";
        QueryExecution qexec = QueryExecutionFactory.sparqlService(service, query);

        try {

            ResultSet results = qexec.execSelect();
            ResultSet output = ResultSetFactory.copyResults(qexec.execSelect());
            System.out.println(ResultSetFormatter.asText(output));
            //ResultSetFormatter.out(System.out, output);


            while (results.hasNext()) {
                QuerySolution sol = results.next();
                ResultVariables resultVariables = new ResultVariables();
                List<String> wrongAnswers = new ArrayList<String>();

                String rightAnswer = "";
                String wrongAnswer1 = "";
                String wrongAnswer2 = "";
                String wrongAnswer3 = "";


                switch (qType) {

                    case 0: //0:Career start of artist/band
                        //get artist
                        artist = resultVariables.getArtist(sol);

                        //get dboStartYear
                        startYear = resultVariables.getStartYear(sol);

                        q.setQUESTION("When did the musical career of \n" + artist + " start?");
                        q.setANSWER(startYear);

                        //ger right answer
                        rightAnswer = startYear;

                        wrongAnswers = getWrongAnswerOptionsYear(rightAnswer, difficulty);
                        wrongAnswer1 = wrongAnswers.get(0);
                        wrongAnswer2 = wrongAnswers.get(1);
                        wrongAnswer3 = wrongAnswers.get(2);

                        break;
                    case 1: //0:Career end of artist/band
                        //get artist
                        artist = resultVariables.getArtist(sol);

                        //get dboEndYear (if available)
                        endYear = resultVariables.getEndYear(sol);

                        q.setQUESTION("When did the musical career of \n" + artist + " end?");
                        q.setANSWER(endYear);

                        //ger right answer
                        rightAnswer = endYear;

                        wrongAnswers = getWrongAnswerOptionsYear(rightAnswer, difficulty);
                        wrongAnswer1 = wrongAnswers.get(0);
                        wrongAnswer2 = wrongAnswers.get(1);
                        wrongAnswer3 = wrongAnswers.get(2);
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
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        qexec.close();

        return q;
    }

    private List<String> getWrongAnswerOptionsYear(String rA, int difficulty) {

        String rightAnswer = rA;

        //get current year
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);

        //store the results
        List<String> result = new ArrayList<String>();

        boolean w1search = true;
        boolean w2search = true;
        boolean w3search = true;
        String wrongAnswer1 = "";
        String wrongAnswer2 = "";
        String wrongAnswer3 = "";
        int wrongAnswerInt1 = 0;
        int wrongAnswerInt2 = 0;
        int wrongAnswerInt3 = 0;

        //get first wrong answer
        while (w1search) {
            AnswerOptions w1 = new AnswerOptions();
            wrongAnswerInt1 = w1.getYearNumber(rightAnswer, difficulty);
            wrongAnswer1 = String.valueOf(wrongAnswerInt1);
            if (!wrongAnswer1.equals(rightAnswer) &&
                    wrongAnswerInt1 <= currentYear) {
                result.add(wrongAnswer1);
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
                result.add(wrongAnswer2);
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
                result.add(wrongAnswer3);
                w3search = false;
            }
        }
        return result;
    }

}
