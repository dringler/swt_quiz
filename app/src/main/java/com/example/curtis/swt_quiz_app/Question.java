package com.example.curtis.swt_quiz_app;

/**
 * Created by curtis on 06/11/15.
 */
public class Question {
    private int ID;
    private String QUESTION;
    private String OPTA;
    private String OPTB;
    private String OPTC;
    private String OPTD;
    private String ANSWER;

    public Question() {
        ID=0;
        QUESTION = "";
        OPTA = "";
        OPTB = "";
        OPTC = "";
        OPTD = "";
        ANSWER = "";
    }
    public Question(String question, String optA, String optB, String optC, String optD, String answer) {
        QUESTION = question;
        OPTA = optA;
        OPTB = optB;
        OPTC = optC;
        OPTD = optD;
        ANSWER = answer;
    }
    public int getID() {
        return ID;
    }
    public void setID(int id) {
        ID = id;
    }
    public String getQUESTION() {
        return QUESTION;
    }
    public void setQUESTION(String question) {
        QUESTION = question;
    }
    public String getOPTA() {
        return OPTA;
    }
    public void setOPTA(String OPTA) {
        this.OPTA = OPTA;
    }
    public String getOPTB() {
        return OPTB;
    }
    public void setOPTB(String OPTB) {
        this.OPTB = OPTB;
    }
    public String getOPTC() {
        return OPTC;
    }
    public void setOPTC(String OPTC) {
        this.OPTC = OPTC;
    }
    public String getOPTD() {
        return OPTD;
    }
    public void setOPTD(String OPTD) {
        this.OPTD = OPTD;
    }
    public String getANSWER() {
        return ANSWER;
    }
    public void setANSWER(String ANSWER) {
        this.ANSWER = ANSWER;
    }
}
