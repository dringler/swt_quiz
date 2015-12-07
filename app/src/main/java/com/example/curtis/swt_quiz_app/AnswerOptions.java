package com.example.curtis.swt_quiz_app;


/**
 * Created by curtis on 27/11/15.
 */
public class AnswerOptions {
    String yearNumber;

    public int getYearNumber(String rightAnswer, int difficulty) {
        int rightAnswerInt = Integer.parseInt(rightAnswer);
        int wrongAnswerInt = 0;
        int variation = 0;
        int sign = 0;
        RandomInt randomVariation = new RandomInt();
        RandomInt randomSign = new RandomInt();

        //ger variation
        if (difficulty==0) { //difficulty=easy
            //higher variation from right answer
            variation = randomVariation.getRandInt(5,25);
        } else { //difficulty = hard
            //lower variation from right answer
            variation = randomVariation.getRandInt(1,10);
        }
        //get sign (plus or minus)
        sign = randomSign.getRandInt(0,1);
        switch (sign) {
            case 0: //plus
                wrongAnswerInt = rightAnswerInt + variation;
                break;
            case 1: //minus
                wrongAnswerInt = rightAnswerInt - variation;
                break;
        }
        return wrongAnswerInt;
    }


}
