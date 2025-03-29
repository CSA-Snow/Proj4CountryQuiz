package edu.cs.uga.proj4countryquiz;

import java.util.Date;

public class QuizHistory {
    private int hid;
    private int score;

    //Stored in epoch time (ms since Jan 1, 1970)
    private long date;

    public QuizHistory() {
        this.hid = -1;
        this.score = -1;
        this.date = 0;
    }

    public QuizHistory (int hid, int score, long date) {
        this.hid = -1;
        this.score = score;
        this.date = date;
    }

    public int getHid() {
        return hid;
    }

    public int getScore() {
        return score;
    }

    public long getDate() {
        return date;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String toString() {
        return hid + " " + score + " " + date;
    }

    public Date getJavaDate() {
        Date jDate = new Date(date);
        return jDate;
    }

    public void setJavaDate(Date jDate) {
        date = jDate.getTime();
    }
}
