package com.example.curtis.swt_quiz_app;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by curtis on 26/11/15.
 */
public class QueryString {
    private String queryStringQ = ""; //query string that might have quotes
    private  String queryString = "";

    public String getQueryString(int qType, int listNum, List<String> list) {
        String artist = "";
        List<String> fourArtists = new ArrayList<String>();
        String artist2 = "";
        String artist3 = "";
        String artist4 = "";


        switch (qType) {
            case 0: //career start of artist/band
                artist = getRandomArtist(list);
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
                                    "(?artist = <" + artist + ">) .\n" +
                                    "  }";
                    queryString = removeQuotation(queryStringQ);
                }
                break;
            case 1: //career end of artist/band
                artist = getRandomArtist(list);
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
                                    "(?artist = <" + artist + ">) .\n" +
                                    "  }";
                    queryString = removeQuotation(queryStringQ);
                } else { //musicians
                    queryStringQ =
                            "PREFIX  dbo:  <http://dbpedia.org/ontology/>\n" +
                                    "PREFIX  dbp:  <http://dbpedia.org/property/>\n" +
                                    "PREFIX  dbpedia: <http://dbpedia.org/resource/>\n" +
                                    "\n" +
                                    "SELECT DISTINCT ?artist ?dboEndYear\n" +
                                    "WHERE\n" +
                                    "  { ?artist <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> dbo:MusicalArtist .\n" +
                                    "    ?artist dbo:activeYearsEndYear ?dboEndYear .\n" +
                                    "FILTER\n" +
                                    "(?artist = <" + artist + ">) .\n" +
                                    "  }";
                    queryString = removeQuotation(queryStringQ);
                }
                break;
            case 2: //hometown of artist/band
                fourArtists = getFourRandomArtists(list);
                artist = fourArtists.get(0);
                artist2 = fourArtists.get(1);
                artist3 = fourArtists.get(2);
                artist4 = fourArtists.get(3);

                if (listNum == 0) { //bands
                    queryStringQ =
                            "PREFIX  dbo:  <http://dbpedia.org/ontology/>\n" +
                                    "PREFIX  dbp:  <http://dbpedia.org/property/>\n" +
                                    "PREFIX  dbpedia: <http://dbpedia.org/resource/>\n" +
                                    "SELECT DISTINCT ?artist ?hometown\n" +
                                    "WHERE {\n" +
                                    "?artist a dbo:Band .\n" +
                                    "?artist dbo:hometown ?hometown .\n" +
                                    "FILTER (?artist = <" + artist + "> || " +
                                    "?artist = <" + artist2 + "> || " +
                                    "?artist = <" + artist3 + "> || " +
                                    "?artist = <" + artist4 + ">) .\n" +
                                    "}";
                    queryString = removeQuotation(queryStringQ);
                } else { //musicians
                    queryStringQ =
                            "PREFIX  dbo:  <http://dbpedia.org/ontology/>\n" +
                                    "PREFIX  dbp:  <http://dbpedia.org/property/>\n" +
                                    "PREFIX  dbpedia: <http://dbpedia.org/resource/>\n" +
                                    "SELECT DISTINCT ?artist ?hometown\n" +
                                    "WHERE {\n" +
                                    "?artist a dbo:MusicalArtist .\n" +
                                    "?artist dbo:hometown ?hometown .\n" +
                                    "FILTER (?artist = <" + artist + "> || " +
                                    "?artist = <" + artist2 + "> || " +
                                    "?artist = <" + artist3 + "> || " +
                                    "?artist = <" + artist4 + ">) .\n" +
                                    "}";
                    queryString = removeQuotation(queryStringQ);
                }
                break;
            case 3: //3:which album is from the following artist/band
                fourArtists = getFourRandomArtists(list);
                artist = fourArtists.get(0);
                artist2 = fourArtists.get(1);
                artist3 = fourArtists.get(2);
                artist4 = fourArtists.get(3);

                if (listNum == 0) { //bands
                    queryStringQ =
                            "PREFIX  dbo:  <http://dbpedia.org/ontology/>\n" +
                                    "PREFIX  dbp:  <http://dbpedia.org/property/>\n" +
                                    "PREFIX  dbpedia: <http://dbpedia.org/resource/>\n" +
                                    "SELECT DISTINCT ?artist ?albumname \n" +
                                    "WHERE {\n" +
                                    "?artist a dbo:Band .\n" +
                                    "?albumname dbo:artist ?artist .\n" +
                                    "?albumname a dbo:Album .\n" +
                                    "FILTER (?artist = <" + artist + "> || " +
                                    "?artist = <" + artist2 + "> || " +
                                    "?artist = <" + artist3 + "> || " +
                                    "?artist = <" + artist4 + ">) .\n" +
                                    "}" +
                                    "ORDER BY ?artist";
                    queryString = removeQuotation(queryStringQ);
                } else { //musicians
                    queryStringQ =
                            "PREFIX  dbo:  <http://dbpedia.org/ontology/>\n" +
                                    "PREFIX  dbp:  <http://dbpedia.org/property/>\n" +
                                    "PREFIX  dbpedia: <http://dbpedia.org/resource/>\n" +
                                    "SELECT DISTINCT ?artist ?albumname \n" +
                                    "WHERE {\n" +
                                    "?artist a dbo:MusicalArtist .\n" +
                                    "?albumname dbo:artist ?artist .\n" +
                                    "?albumname a dbo:Album .\n" +
                                    "FILTER (?artist = <" + artist + "> || " +
                                    "?artist = <" + artist2 + "> || " +
                                    "?artist = <" + artist3 + "> || " +
                                    "?artist = <" + artist4 + ">) .\n" +
                                    "}" +
                                    "ORDER BY ?artist";
                    queryString = removeQuotation(queryStringQ);
                }
                break;
            case 4: //4:which song is from the following artist/band
                fourArtists = getFourRandomArtists(list);
                artist = fourArtists.get(0);
                artist2 = fourArtists.get(1);
                artist3 = fourArtists.get(2);
                artist4 = fourArtists.get(3);

                if (listNum == 0) { //bands
                    queryStringQ =
                            "PREFIX  dbo:  <http://dbpedia.org/ontology/>\n" +
                                    "PREFIX  dbp:  <http://dbpedia.org/property/>\n" +
                                    "PREFIX  dbpedia: <http://dbpedia.org/resource/>\n" +
                                    "SELECT DISTINCT ?artist ?songname \n" +
                                    "WHERE {\n" +
                                    "?artist a dbo:Band .\n" +
                                    "?songname dbo:artist ?artist .\n" +
                                    "?songname a dbo:Song .\n" +
                                    "FILTER (?artist = <" + artist + "> || " +
                                    "?artist = <" + artist2 + "> || " +
                                    "?artist = <" + artist3 + "> || " +
                                    "?artist = <" + artist4 + ">) .\n" +
                                    "}" +
                                    "ORDER BY ?artist";
                    queryString = removeQuotation(queryStringQ);
                } else { //musicians
                    queryStringQ =
                            "PREFIX  dbo:  <http://dbpedia.org/ontology/>\n" +
                                    "PREFIX  dbp:  <http://dbpedia.org/property/>\n" +
                                    "PREFIX  dbpedia: <http://dbpedia.org/resource/>\n" +
                                    "SELECT DISTINCT ?artist ?songname \n" +
                                    "WHERE {\n" +
                                    "?artist a dbo:MusicalArtist .\n" +
                                    "?songname dbo:artist ?artist .\n" +
                                    "?songname a dbo:Song .\n" +
                                    "FILTER (?artist = <" + artist + "> || " +
                                    "?artist = <" + artist2 + "> || " +
                                    "?artist = <" + artist3 + "> || " +
                                    "?artist = <" + artist4 + ">) .\n" +
                                    "}" +
                                    "ORDER BY ?artist";
                    queryString = removeQuotation(queryStringQ);
                }
                break;
            case 5: //5: which artist is from the following band
                fourArtists = getFourRandomArtists(list);
                //get four bands
                artist = fourArtists.get(0);
                artist2 = fourArtists.get(1);
                artist3 = fourArtists.get(2);
                artist4 = fourArtists.get(3);

                queryStringQ =
                        "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                                "PREFIX dbo:<http://dbpedia.org/ontology/>\n" +
                                "SELECT DISTINCT ?band ?artist\n" +
                                "WHERE {\n" +
                                "?band rdf:type dbo:Band.\n" +
                                "?artist rdf:type dbo:MusicalArtist .\n" +
                                "?band dbo:bandMember ?artist .\n" +
                                "FILTER (?band = <" + artist + "> || " +
                                "?band = <" + artist2 + "> || " +
                                "?band = <" + artist3 + "> || " +
                                "?band = <" + artist4 + ">) .\n" +
                                "}" +
                                "ORDER BY ?band";
                queryString = removeQuotation(queryStringQ);
                break;

        }
        return queryString;
    }

    private String getRandomArtist(List<String> list) {
        //get random index for artist
        RandomInt randomA = new RandomInt();
        int randomArtist = randomA.getRandInt(0, list.size()); //change maximum according to list size (top x)
        String artist = list.get(randomArtist);
        return artist;
    }

    private List<String> getFourRandomArtists(List<String> list) {
        List<String> fourArtists = new ArrayList<String>();
        //get random index for artist
        boolean searching = true;
        while (searching) {
            RandomInt randomA = new RandomInt();
            int randomArtist = randomA.getRandInt(0, list.size()); //change maximum according to list size (top x)
            String artist = list.get(randomArtist);
            if (!fourArtists.contains(artist)) {
                fourArtists.add(artist);
            }
            if (fourArtists.size()==4) {
                searching = false;
            }
        }
        return fourArtists;
    }

    private static String removeQuotation(String quoted) {
        String unquoted;
        unquoted = quoted.replace("\"", "");
        return unquoted;
    }
}
