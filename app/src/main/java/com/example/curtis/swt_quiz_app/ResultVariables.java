package com.example.curtis.swt_quiz_app;

import com.hp.hpl.jena.query.QuerySolution;

/**
 * Created by curtis on 26/11/15.
 */
public class ResultVariables {
    String artist = "";
    String startYear = "";
    String endYear = "";

    public String getArtist(QuerySolution sol) {
        if (sol.get("artist") == null) {
            artist = null;
        } else if (sol.get("artist").isLiteral()) {
            artist = sol.getLiteral("artist").toString();
        } else {
            artist = sol.getResource("artist").getURI().substring(28);
            int index = 0;
            index = artist.indexOf("(");
            if (index > 2) {
                artist = artist.substring(0, index - 1);
            }
            artist = artist.replaceAll("_", " ");
        }
        System.out.println("artist: " + artist);
        return artist;
    }

    public String getStartYear(QuerySolution sol) {
        if (sol.get("dboStartYear") == null) {
            startYear = null;
        } else if (sol.get("dboStartYear").isLiteral()) {
            startYear = sol.getLiteral("dboStartYear").toString().substring(0, 4);
            int startYearInt = Integer.valueOf(startYear);
        } else {
            startYear = sol.getResource("dboStartYear").getURI();
        }
        System.out.println("startYear: " + startYear);
        return startYear;
    }

    public String getEndYear(QuerySolution sol) {
        if (sol.get("dboEndYear") == null) {
            endYear = null;
        } else if (sol.get("dboEndYear").isLiteral()) {
            endYear = sol.getLiteral("dboEndYear").toString().substring(0, 4);
        } else {
            endYear = sol.getResource("dboEndYear").getURI();
        }
        System.out.println("endYear: " + endYear);
        return endYear;
    }
}
