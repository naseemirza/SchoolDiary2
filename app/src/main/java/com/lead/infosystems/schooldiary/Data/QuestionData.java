package com.lead.infosystems.schooldiary.Data;

import android.graphics.drawable.Drawable;

public class QuestionData {

    private String name,date,question;
    private Drawable drawable;

    public QuestionData(String name, String date, String question, Drawable drawable) {
        this.name = name;
        this.date = date;
        this.question = question;
        this.drawable =drawable;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
