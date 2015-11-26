package com.example.curtis.swt_quiz_app;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFactory;
import com.hp.hpl.jena.query.ResultSetFormatter;

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

    public Question getNewQuestion() {
       // String[] q = new String[5]; //index: 0=question, 1=right answer, 1-4=answer choices
        Question q = new Question();
        QueryString qs = new QueryString();

        String queryString = "";
        String artist = "";
        String startYear = "";
        String endYear = "";
        //random question type
        //int qType = randInt(0, 1); //randInt(min, max)
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

               /* for (String var : columnNames) {

//                String myArtist = sol.getLiteral("artist").getString();
//                String myStartYear = sol.getLiteral("dboStartYear").getString();
//                String myEndYear = sol.getLiteral("dboEndYear").getString();

                    resultsBuffer.append(var +": ");

                    // Data value will be null if optional and not present
                    if (sol.get(var) == null) {
                        resultsBuffer.append("{null}");
                        // Test whether the returned value is a literal value
                    } else if (sol.get(var).isLiteral()) {
                        resultsBuffer.append(sol.getLiteral(var).toString());
                        // Otherwise the returned value is a URI
                    } else {
                        resultsBuffer.append(sol.getResource(var).getURI());
                    }
                    resultsBuffer.append('\n');
                }
                resultsBuffer.append("-----------------\n");
*/
                //System.out.println(sol.getLiteral("artist").getString());

                q.setQUESTION("question placeholder with artist " + artist);
                q.setANSWER("answer: " + startYear);
                q.setOPTA("answer: " + startYear);
                q.setOPTB("wrong option");
                q.setOPTC("wrong option");
                q.setOPTD("wrong option");


                // System.out.println(resultsBuffer.toString());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        qexec.close();


        return q;
    }

}
