package com.example.curtis.swt_quiz_app;

import java.util.Random;

/**
 * Created by curtis on 26/11/15.
 */
public class QueryString {
    private  String queryString = "";

    public String getQueryString(int qType) {
        switch (qType) {
            //career start of artist/band
            case 0:
                RandomInt randomInt = new RandomInt();
                int rOffset = randomInt.getRandInt(0, 100);
                queryString =
                        "PREFIX  dbo:  <http://dbpedia.org/ontology/>\n" +
                                "PREFIX  dbp:  <http://dbpedia.org/property/>\n" +
                                "PREFIX  dbpedia: <http://dbpedia.org/resource/>\n" +
                                "\n" +
                                "SELECT DISTINCT  ?artist ?dboStartYear ?dboEndYear\n" +
                                "WHERE\n" +
                                "  { ?artist <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> dbo:MusicalArtist .\n" +
                                "    ?artist dbo:activeYearsStartYear ?dboStartYear .\n" +
                                "    OPTIONAL\n" +
                                "      { ?artist dbo:activeYearsEndYear ?dboEndYear} .\n" +
                                "FILTER\n" +
                                "(?dboStartYear > 1970-01-01) .\n"+
                                "  }\n" +
                                // "ORDER BY DESC(?dboStartYear)\n" +
                                "OFFSET " + rOffset + "\n" +
                                "LIMIT   1";

                break;
            //add more cases here for different question types
            case 1:
                break;
        }
        return queryString;
    }
}
