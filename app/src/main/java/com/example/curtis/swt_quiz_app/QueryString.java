package com.example.curtis.swt_quiz_app;

import java.util.List;
import java.util.Random;

/**
 * Created by curtis on 26/11/15.
 */
public class QueryString {
    private String queryStringQ = ""; //query string that might have quotes
    private  String queryString = "";

    public String getQueryString(int qType, int listNum, List<String> list) {
        //get random index for artist
        RandomInt randomA = new RandomInt();
        int randomArtist = randomA.getRandInt(0, 10); //change maximum according to list size (top x)
        String artist = list.get(randomArtist);

        switch (qType) {
            case 0: //career start of artist/band
                if (listNum == 0) { //bands
                    queryStringQ =
                            "PREFIX  dbo:  <http://dbpedia.org/ontology/>\n" +
                                    "PREFIX  dbp:  <http://dbpedia.org/property/>\n" +
                                    "PREFIX  dbpedia: <http://dbpedia.org/resource/>\n" +
                                    "\n" +
                                    "SELECT DISTINCT  ?artist ?dboStartYear\n" +
                                    "WHERE\n" +
                                    "  { ?artist <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> dbo:Band .\n" +
                                    "    ?artist dbo:activeYearsStartYear ?dboStartYear .\n" +
                                    "FILTER\n" +
                                    "(?artist = <" + artist + ">) .\n" +
                                    "  }";
                    queryString = removeQuotation(queryStringQ);

                } else { //musicians

                    queryStringQ =
                            "PREFIX  dbo:  <http://dbpedia.org/ontology/>\n" +
                                    "PREFIX  dbp:  <http://dbpedia.org/property/>\n" +
                                    "PREFIX  dbpedia: <http://dbpedia.org/resource/>\n" +
                                    "\n" +
                                    "SELECT DISTINCT  ?artist ?dboStartYear\n" +
                                    "WHERE\n" +
                                    "  { ?artist <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> dbo:MusicalArtist .\n" +
                                    "    ?artist dbo:activeYearsStartYear ?dboStartYear .\n" +
                                    "FILTER\n" +
                                    "(?artist = <" + artist +">) .\n" +
                                    "  }";
                    queryString = removeQuotation(queryStringQ);
                }
                break;
            //add more cases here for different question types
            case 1:
                if (listNum == 0) { //bands
                    queryStringQ =
                            "PREFIX  dbo:  <http://dbpedia.org/ontology/>\n" +
                                    "PREFIX  dbp:  <http://dbpedia.org/property/>\n" +
                                    "PREFIX  dbpedia: <http://dbpedia.org/resource/>\n" +
                                    "\n" +
                                    "SELECT DISTINCT  ?artist ?dboEndYear\n" +
                                    "WHERE\n" +
                                    "  { ?artist <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> dbo:Band .\n" +
                                    "    ?artist dbo:activeYearsEndYear ?dboEndYear .\n" +
                                    "FILTER\n" +
                                    "(?artist = <" + artist +">) .\n" +
                                    "  }";
                    queryString = removeQuotation(queryStringQ);
                } else { //musicians
                    queryStringQ =
                            "PREFIX  dbo:  <http://dbpedia.org/ontology/>\n" +
                                    "PREFIX  dbp:  <http://dbpedia.org/property/>\n" +
                                    "PREFIX  dbpedia: <http://dbpedia.org/resource/>\n" +
                                    "\n" +
                                    "SELECT DISTINCT  ?artist ?dboEndYear\n" +
                                    "WHERE\n" +
                                    "  { ?artist <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> dbo:MusicalArtist .\n" +
                                    "    ?artist dbo:activeYearsEndYear ?dboEndYear .\n" +
                                    "FILTER\n" +
                                    "(?artist = <" + artist +">) .\n" +
                                    "  }";
                    queryString = removeQuotation(queryStringQ);
                 }

                break;
        }
        return queryString;
    }
    private static String removeQuotation(String quoted) {
        String unquoted;
        unquoted = quoted.replace("\"", "");
        return unquoted;
    }
}
