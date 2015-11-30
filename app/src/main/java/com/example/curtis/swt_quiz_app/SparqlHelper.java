package com.example.curtis.swt_quiz_app;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFactory;
import com.hp.hpl.jena.query.ResultSetFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


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

    public Question getNewQuestion(int diff,  List<String> b, List<String> bS, List<String> iB, List<String> aB, List<String> m,  List<String> mS, List<String> iM, List<String> aM,  List<String> cs) {

        int difficulty = diff;
        List<String> bands = b;
        List<String> bandsSong = bS;
        List<String> inactiveBands = iB;
        List<String> allBands = aB;
        List<String> musicians = m;
        List<String> musiciansSong = mS;
        List<String> inactiveMusicians = iM;
        List<String> allMusicians = aM;
        List<String> countriesAndStates = cs;



        int qType = 0; //question type
        int listNum = 0; //band/artist identifier, 0:band, 1:musicalArtist
        int resultCounter = 0; //counts the number of result of the query

        String queryString = "";
        String artist = "";
        String startYear = "";
        String endYear = "";
        String hometown = "";
        String albumname = "";
        String songname = "";

        Question q = new Question();
        QueryString qs = new QueryString();

        //random question type
        RandomInt randomIntQ = new RandomInt();
        qType = randomIntQ.getRandInt(0, 4); //randInt(min, max)

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
            case 2: //2:hometown of artist/band
                listNum = randomIntL.getRandInt(0, 1);
                if (listNum == 0) { //band
                    queryString = qs.getQueryString(qType, listNum, allBands);
                } else { //musician
                    queryString = qs.getQueryString(qType, listNum, allMusicians);
                }
                break;
            case 3: //3:which album is from the following artist/band
                listNum = randomIntL.getRandInt(0, 1);
                if (listNum == 0) { //band
                    queryString = qs.getQueryString(qType, listNum, allBands);
                } else { //musician
                    queryString = qs.getQueryString(qType, listNum, allMusicians);
                }
                break;
            case 4: //4: which song is from the following artist/band
                listNum = randomIntL.getRandInt(0, 1);
                if (listNum == 0) { //band
                    queryString = qs.getQueryString(qType, listNum, bandsSong);
                } else { //musician
                    queryString = qs.getQueryString(qType, listNum, musiciansSong);
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

            List<String> seenArtist = new ArrayList<String>();
            String currentArtist = "";
            String lastArtist = "";
            String lastAnswer ="";

            String rightAnswer = "";
            String wrongAnswer1 = "";
            String wrongAnswer2 = "";
            String wrongAnswer3 = "";

            nextResultLoop:
            while (results.hasNext()) {
                QuerySolution sol = results.next();
                ResultVariables resultVariables = new ResultVariables();
                List<String> wrongAnswers = new ArrayList<String>();

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
                        break;
                    case 2: //2:hometown of artist/band
                        switch (resultCounter) {
                            case 0:
                                artist = resultVariables.getArtist(sol);
                                hometown = resultVariables.getHometown(sol);

                                //check for next artist without increasing result counter
                               if (artist.equals(lastArtist) || lastArtist == "") {
                                   if (!countriesAndStates.contains(hometown)) {
                                       q.setQUESTION("Where is " + artist + " from?");
                                       q.setANSWER(hometown);
                                       rightAnswer = hometown;
                                       seenArtist.add(artist);
                                       lastArtist = "";
                                       resultCounter++;
                                   } else {
                                       lastArtist = artist;
                                       lastAnswer = hometown;
                                   }
                                //no valid values in hometown attribute
                               } else {
                                   q.setQUESTION("Where is " + lastArtist + " from?");
                                   q.setANSWER(lastAnswer);
                                   rightAnswer = lastAnswer;
                                   seenArtist.add(lastArtist);
                                   resultCounter++;
                                   //do case 1 here already as new artist is queried and old artist has no valid attribute
                                   if (!seenArtist.contains(resultVariables.getArtist(sol))){
                                       currentArtist = resultVariables.getArtist(sol);
                                       wrongAnswer1 = resultVariables.getHometown(sol);
                                       if (!countriesAndStates.contains(wrongAnswer1)) {
                                           seenArtist.add(resultVariables.getArtist(sol));
                                           lastArtist = "";
                                           resultCounter++;
                                       } else {
                                           lastArtist = currentArtist;
                                           lastAnswer = hometown;
                                       }
                                   }
                               }
                                break;
                            case 1:
                                currentArtist = resultVariables.getArtist(sol);
                                if (currentArtist.equals(lastArtist) || lastArtist =="") {
                                    if (!seenArtist.contains(resultVariables.getArtist(sol))){
                                        wrongAnswer1 = resultVariables.getHometown(sol);
                                        if (!countriesAndStates.contains(wrongAnswer1)) {
                                            seenArtist.add(resultVariables.getArtist(sol));
                                            lastArtist = "";
                                            resultCounter++;
                                        } else {
                                            lastArtist = currentArtist;
                                            lastAnswer = hometown;
                                        }
                                    }
                                } else {
                                    wrongAnswer1 = lastAnswer;
                                    seenArtist.add(lastArtist);
                                    resultCounter++;
                                    //do case 2 already as new artist is queried and old artist has no valid attribute
                                    if (!seenArtist.contains(resultVariables.getArtist(sol))){
                                        wrongAnswer2 = resultVariables.getHometown(sol);
                                        if (!countriesAndStates.contains(wrongAnswer2)) {
                                            seenArtist.add(resultVariables.getArtist(sol));
                                            lastArtist = "";
                                            resultCounter++;
                                        } else {
                                            lastArtist = currentArtist;
                                            lastAnswer = hometown;
                                        }
                                    }
                                }
                                break;
                            case 2:
                                currentArtist = resultVariables.getArtist(sol);
                                if (currentArtist.equals(lastArtist) || lastArtist == "") {
                                    if (!seenArtist.contains(resultVariables.getArtist(sol))) {
                                        wrongAnswer2 = resultVariables.getHometown(sol);
                                        if (!countriesAndStates.contains(wrongAnswer2)) {
                                            seenArtist.add(resultVariables.getArtist(sol));
                                            lastArtist = "";
                                            resultCounter++;
                                        } else {
                                            lastArtist = currentArtist;
                                            lastAnswer = hometown;
                                        }
                                    }
                                } else {
                                    wrongAnswer1 = lastAnswer;
                                    seenArtist.add(lastArtist);
                                    resultCounter++;
                                    //do case 2 already as new artist is queried and old artist has no valid attribute
                                    if (!seenArtist.contains(resultVariables.getArtist(sol))){
                                        wrongAnswer3 = resultVariables.getHometown(sol);
                                        if (!countriesAndStates.contains(wrongAnswer3)) {
                                            seenArtist.add(resultVariables.getArtist(sol));
                                            lastArtist = "";
                                            resultCounter++;
                                        } else {
                                            lastArtist = currentArtist;
                                            lastAnswer = hometown;
                                        }
                                    }

                                }
                                break;
                            case 3:
                                    if (!seenArtist.contains(resultVariables.getArtist(sol))) {
                                        wrongAnswer3 = resultVariables.getHometown(sol);
                                        if (!countriesAndStates.contains(wrongAnswer3) || results.hasNext() == false) {
                                            seenArtist.add(resultVariables.getArtist(sol));
                                            lastArtist = "";
                                            resultCounter++;
                                            break nextResultLoop;
                                        }
                                    }
                                break;
                            default:
                                if (!seenArtist.contains(resultVariables.getArtist(sol))){
                                    wrongAnswer3 = resultVariables.getHometown(sol);
                                        seenArtist.add(resultVariables.getArtist(sol));
                                }
                                break;
                        }
                        continue nextResultLoop;
                    case 3: //3:which album is from the following artist/band
                        switch (resultCounter) {
                            case 0:
                                artist = resultVariables.getArtist(sol);
                                albumname = resultVariables.getAlbumname(sol);
                                q.setQUESTION("Which album was released by \n" + artist + "?");
                                q.setANSWER(albumname);
                                rightAnswer = albumname;
                                seenArtist.add(artist);
                                resultCounter++;
                                break;
                            case 1:
                                if (!seenArtist.contains(resultVariables.getArtist(sol))){
                                    wrongAnswer1 = resultVariables.getAlbumname(sol);
                                    seenArtist.add(resultVariables.getArtist(sol));
                                    resultCounter++;
                                }
                                break;
                            case 2:
                                if (!seenArtist.contains(resultVariables.getArtist(sol))){
                                    wrongAnswer2 = resultVariables.getAlbumname(sol);
                                    seenArtist.add(resultVariables.getArtist(sol));
                                    resultCounter++;
                                }
                                break;
                            case 3:
                                if (!seenArtist.contains(resultVariables.getArtist(sol))){
                                    wrongAnswer3 = resultVariables.getAlbumname(sol);
                                    seenArtist.add(resultVariables.getArtist(sol));
                                    resultCounter++;
                                }
                                break;
                            default:
                                if (!seenArtist.contains(resultVariables.getArtist(sol))){
                                    wrongAnswer3 = resultVariables.getAlbumname(sol);
                                    seenArtist.add(resultVariables.getArtist(sol));
                                }
                                break;
                        }
                        continue nextResultLoop;
                    case 4: //3:which song is from the following artist/band
                        switch (resultCounter) {
                            case 0:
                                artist = resultVariables.getArtist(sol);
                                songname = resultVariables.getSongname(sol);
                                q.setQUESTION("Which song was released by \n" + artist + "?");
                                q.setANSWER(songname);
                                rightAnswer = songname;
                                seenArtist.add(artist);
                                resultCounter++;
                                break;
                            case 1:
                                if (!seenArtist.contains(resultVariables.getArtist(sol))){
                                    wrongAnswer1 = resultVariables.getSongname(sol);
                                    seenArtist.add(resultVariables.getArtist(sol));
                                    resultCounter++;
                                }
                                break;
                            case 2:
                                if (!seenArtist.contains(resultVariables.getArtist(sol))){
                                    wrongAnswer2 = resultVariables.getSongname(sol);
                                    seenArtist.add(resultVariables.getArtist(sol));
                                    resultCounter++;
                                }
                                break;
                            case 3:
                                if (!seenArtist.contains(resultVariables.getArtist(sol))){
                                    wrongAnswer3 = resultVariables.getSongname(sol);
                                    seenArtist.add(resultVariables.getArtist(sol));
                                    resultCounter++;
                                }
                                break;
                            default:
                                if (!seenArtist.contains(resultVariables.getArtist(sol))){
                                    wrongAnswer3 = resultVariables.getSongname(sol);
                                    seenArtist.add(resultVariables.getArtist(sol));
                                }
                                break;
                        }
                        continue nextResultLoop;
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


        } catch (Exception e) {
            e.printStackTrace();
            //no dbpedia connection
            //"The web-site you are currently trying to access is under maintenance at this time.""
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
