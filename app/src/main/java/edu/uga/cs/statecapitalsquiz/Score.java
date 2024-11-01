package edu.uga.cs.statecapitalsquiz;

import android.util.Log;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Score {
    public double score;
    public Date date;
    public String dateString;

    // Creates a new Score object from a Quiz with the current date
    public Score(Quiz q) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        dateString = sdf.format(date);

        double total = 0.0;
        for (Question question : q.questions) {
            if (question.isCorrect()) total += 1.0;
        }
        score = total / 6.0;
    }

    // Creates a new Score object with the current date
    public Score(double result) {
        score = result;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        dateString = sdf.format(date);
    }

    public Score (double result, String dateTime) {
        score = result;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            date = sdf.parse(dateTime);
            dateString = dateTime;
        } catch (ParseException pe) {
            // if, for some reason, whatever was stored in the database cannot be parsed
            dateString = "1970-01-01 00:00:00";
            Log.e("Project4", dateString + ": " + pe.toString());
        }
    }

    public String toString() {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(score) + ", " + dateString;
    }

    public double getScore() {
        return score;
    }

    public String getScoreAsString() {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(score);
    }

    public Date getDate() {
        return date;
    }

    public String getDateString() {
        return dateString;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }
}
