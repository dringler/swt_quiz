package com.example.curtis.swt_quiz_app;

import com.hp.hpl.jena.query.QuerySolution;

/**
 * Created by curtis on 26/11/15.
 */
public class ResultVariables {
    String artist = "";
    String band = "";
    String startYear = "";
    String endYear = "";
    String releaseDate = "";
    String hometown = "";
    String albumname ="";
    String songname = "";

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
        return artist;
    }

    public String getStartYear(QuerySolution sol) {
        if (sol.get("dboStartYear") == null) {
            startYear = null;
        } else if (sol.get("dboStartYear").isLiteral()) {
            startYear = sol.getLiteral("dboStartYear").toString().substring(0, 4);
        } else {
            startYear = sol.getResource("dboStartYear").getURI();
        }
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
        return endYear;
    }
    public String getReleaseDate(QuerySolution sol) {
        if (sol.get("releaseDate") == null) {
            releaseDate = null;
        } else if (sol.get("releaseDate").isLiteral()) {
            releaseDate = sol.getLiteral("releaseDate").toString().substring(0, 4);
        } else {
            releaseDate = sol.getResource("releaseDate").getURI();
        }
        return releaseDate;
    }



    public String getHometown(QuerySolution sol) {
        if (sol.get("hometown") == null) {
            hometown = null;
        } else if (sol.get("hometown").isLiteral()) {
            hometown = sol.getLiteral("hometown").toString();
        } else {
            hometown = sol.getResource("hometown").getURI().substring(28);
            int index = 0;
            index = hometown.indexOf("(");
            if (index > 2) {
                hometown = hometown.substring(0, index - 1);
            }
            hometown = hometown.replaceAll("_", " ");
        }
        return hometown;
    }

    public String getAlbumname(QuerySolution sol) {
        if (sol.get("albumname") == null) {
            albumname = null;
        } else if (sol.get("albumname").isLiteral()) {
            albumname = sol.getLiteral("albumname").toString();
        } else {
            albumname = sol.getResource("albumname").getURI().substring(28);
            int index = 0;
            index = albumname.indexOf("(");
            if (index > 2) {
                albumname = albumname.substring(0, index - 1);
            }
            albumname = albumname.replaceAll("_", " ");
        }
        return albumname;
    }

    public String getSongname(QuerySolution sol) {
        if (sol.get("songname") == null) {
            songname = null;
        } else if (sol.get("songname").isLiteral()) {
            songname = sol.getLiteral("songname").toString();
        } else {
            songname = sol.getResource("songname").getURI().substring(28);
            int index = 0;
            index = songname.indexOf("(");
            if (index > 2) {
                songname = songname.substring(0, index - 1);
            }
            songname = songname.replaceAll("_", " ");
        }
        return songname;
    }
    public String getBand(QuerySolution sol) {
        if (sol.get("band") == null) {
            band = null;
        } else if (sol.get("band").isLiteral()) {
            band = sol.getLiteral("band").toString();
        } else {
            band = sol.getResource("band").getURI().substring(28);
            int index = 0;
            index = band.indexOf("(");
            if (index > 2) {
                band = band.substring(0, index - 1);
            }
            band = band.replaceAll("_", " ");
        }
        return band;
    }
}
