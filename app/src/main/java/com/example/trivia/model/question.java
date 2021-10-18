package com.example.trivia.model;

public class question {
    private String answer;
    private boolean answerTrue;

    public question(){

    }

    public question(String q, boolean at){
        answer = q;
        answerTrue = at;
    }

    public String getAnswer(){
        return answer;
    }

    public boolean getAnswerTrue(){
        return answerTrue;
    }

    public void setAnswer(String answer){
        this.answer = answer;
    }
}
