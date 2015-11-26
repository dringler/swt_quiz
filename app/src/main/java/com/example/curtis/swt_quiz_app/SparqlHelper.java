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

        String queryString = "";
        String artist = "";
        String startYear = "";
        String endYear = "";
        //random question type
        //int qType = randInt(0, 1); //randInt(min, max)
        int qType = 0;
        switch (qType) {
            //start year of artist/band
            case 0:
                int rOffset = randInt(0, 100);
                queryString =
                        "PREFIX  dbo:  <http://dbpedia.org/ontology/>\n" +
                                "PREFIX  dbp:  <http://dbpedia.org/property/>\n" +
                                "PREFIX  dbpedia: <http://dbpedia.org/resource/>\n" +
                                "\n" +
                                "SELECT DISTINCT  ?artist ?dboStartYear ?dboEndYear\n" +
                                "WHERE\n" +
                                "  { ?artist <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> dbo:MusicalArtist .\n" +
                                "    ?artist dbo:activeYearsStartYear ?dboStartYear\n" +
                                "    OPTIONAL\n" +
                                "      { ?artist dbo:activeYearsEndYear ?dboEndYear}\n" +
                                "  }\n" +
                               // "ORDER BY DESC(?dboStartYear)\n" +
                                "OFFSET " + rOffset + "\n" +
                                "LIMIT   1";
                //queryString = "PREFIX dbo: <http://dbpedia.org/ontology/>\n" + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" + "PREFIX dbp: <http://dbpedia.org/property/>\n" + "PREFIX dct: <http://purl.org/dc/terms/>\n" + "PREFIX dbc: <http://dbpedia.org/resource/Category:>\n" + "\n" + "SELECT DISTINCT ?city_name ?sun\n" + "WHERE\n" + " { ?city dct:subject dbc:Capitals_in_Europe ;\n" + " rdfs:label ?city_name ;\n" + " dbp:yearSun ?sun\n" + " OPTIONAL\n" + " { ?city dbo:populationTotal ?population_total }\n" + " OPTIONAL\n" + " { ?city dbp:populationBlank ?population_blank }\n" + " FILTER ( ( ?population_total > 2000000 ) || ( ?population_blank > 2000000 ) )\n" + " FILTER langMatches(lang(?city_name), \"EN\")\n" + " }\n" + "LIMIT 10";
                break;
            //add more cases here for different question types
           // case 1:
            //...
            //    break;
        }

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

                //get artist
                if (sol.get("artist") == null) {
                    artist = null;
                } else if (sol.get("artist").isLiteral()) {
                    artist = sol.getLiteral("artist").toString();
                } else {
                    artist = sol.getResource("artist").getURI().substring(28);
                }
                System.out.println("artist: " + artist);

                //get dboStartYear
                if (sol.get("dboStartYear") == null) {
                    startYear = null;
                } else if (sol.get("dboStartYear").isLiteral()) {
                    startYear = sol.getLiteral("dboStartYear").toString().substring(0, 4);
                } else {
                    startYear = sol.getResource("dboStartYear").getURI();
                }
                System.out.println("startYear: " + startYear);

                //get dboEndYear (if available)
                if (sol.get("dboEndYear") == null) {
                    endYear = null;
                } else if (sol.get("dboEndYear").isLiteral()) {
                    endYear = sol.getLiteral("dboEndYear").toString().substring(0, 4);
                } else {
                    endYear = sol.getResource("dboEndYear").getURI();
                }
                System.out.println("endYear: " + endYear);

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


    public int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max-min)+1) + min;
        return randomNum;
    }

}
