package com.example.musictraining;

import android.app.Activity;

public class TriviaQuestion extends Activity {
    private int id;
    private int questType;
    private String question;
    private String opta;
    private String optb;
    private String optc;
    private String optd;
    private String answer;
    private String wavPath;

    public TriviaQuestion(String q, String oa, String ob, String oc, String od, String ans, String wav, int qType) {

        question = q;
        opta = oa;
        optb = ob;
        optc = oc;
        optd = od;
        answer = ans;
        wavPath = wav;
        questType =qType;
    }

    public TriviaQuestion() {
        id = 0;
        question = "";
        opta = "";
        optb = "";
        optc = "";
        optd = "";
        answer = "";
        wavPath = "";
        questType=0;
    }

    public String getQuestion() {
        return question;
    }

    public String getOptA() {
        return opta;
    }

    public String getOptB() {
        return optb;
    }

    public String getOptC() {
        return optc;
    }

    public String getOptD() {
        return optd;
    }

    public String getAnswer() {
        return answer;
    }

    public String getWavPath(){
        return wavPath;
    }

    public int getQuestType(){return questType;}

    public void setId(int i) {
        id = i;
    }

    public void setQuestion(String q1) {
        question = q1;
    }

    public void setOptA(String o1) {
        opta = o1;
    }

    public void setOptB(String o2) {
        optb = o2;
    }

    public void setOptC(String o3) {
        optc = o3;
    }

    public void setOptD(String o4) {
        optd = o4;
    }

    public void setAnswer(String ans) {
        answer = ans;
    }

    public void setWavPath(String wav){
        wavPath = wav;
    }

    public void setQuestType(int qType) {
        questType = qType;
    }
}
