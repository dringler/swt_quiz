# swt_quiz_app
Android Semantic Web Quiz Application for the Semantic Web Technologies course at the University of Mannheim.

The app does not prestore the question and answer possibilities for the quiz. Everything is queried randomly and dynamically from DBpedia.
Only top 100 lists are stored on the device (top 100 bands, top 100 songs, etc.)
For each new question one out of eight different question types is selected randomly:
* career start of an artist/band
* career end of an artist/band
* hometown of an artist/band
* album by an artist/band
* song by an artist/band
* member of a band
* album release year
* song release year

Afterwards the dynamically created query is send to the DBpedia SPARQL endpoint to receive one correct and several wrong answer possibilities. The app tries to ensure that the wrong answer possibilities are not correct by chance (e.g. many artist are from London so one random wrong answer might be correct).
Question difficulty is ensured by receiving more or less famous bands/artists.

![alt tag](https://raw.githubusercontent.com/dringler/swt_quiz_app/master/Quiz_App.png)
