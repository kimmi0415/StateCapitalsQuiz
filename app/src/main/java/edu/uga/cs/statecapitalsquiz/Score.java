package edu.uga.cs.statecapitalsquiz;

import android.util.Log;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Score {
    /** The numerical score as a percentage (0-100) */
    public double score;

    /** The date and time when this score was achieved */
    public Date date;

    /** Formatted string representation of the score's date and time (format: "yyyy-MM-dd HH:mm:ss") */
    public String dateString;

    /**
     * Creates a new Score object by calculating the percentage of correct answers
     * from a Quiz object. Uses the current date and time as the timestamp.
     *
     * @param q The Quiz object
     */
    public Score(Quiz q) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        dateString = sdf.format(date);

        double total = 0.0;
        for (Question question : q.questions) {
            if (question.isCorrect()) total += 1.0;
        }
        score = (total / 6.0) * 100;
    }

    /**
     * Creates a new Score object with a given score and the current date
     *
     * @param result The score value
     */
    public Score(double result) {
        score = result;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        dateString = sdf.format(date);
    }

    /**
     * Creates a new Score object with a given score and specific date/time.
     * If the provided date string cannot be parsed, defaults to "1970-01-01 00:00:00".
     *
     * @param result The score value
     * @param dateTime The date and time string in "yyyy-MM-dd HH:mm:ss" format
     */
    public Score(double result, String dateTime) {
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

    /**
     * Returns a string representation of this score, including both the
     * numerical value (formatted to 2 decimal places) and the date string.
     *
     * @return A string in the format "score, date"
     */
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(score) + ", " + dateString;
    }

    /**
     * Gets the raw numerical score.
     * @return The score as a percentage (0-100)
     */
    public double getScore() {
        return score;
    }

    /**
     * Gets the score formatted as a string.
     * @return The formatted score string
     */
    public String getScoreAsString() {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(score);
    }

    /**
     * Gets the Date object representing when this score was achieved.
     * @return The score's date and time
     */
    public Date getDate() {
        return date;
    }

    /**
     * Gets the formatted string representation of when this score was achieved.
     * @return The date string in "yyyy-MM-dd HH:mm:ss" format
     */
    public String getDateString() {
        return dateString;
    }

    /**
     * Sets the numerical score value.
     * @param score The new score value as a percentage (0-100)
     */
    public void setScore(double score) {
        this.score = score;
    }

    /**
     * Sets the Date object for this score.
     * @param date The new Date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Sets the formatted date string.
     * @param dateString The new date string in "yyyy-MM-dd HH:mm:ss" format
     */
    public void setDateString(String dateString) {
        this.dateString = dateString;
    }
}