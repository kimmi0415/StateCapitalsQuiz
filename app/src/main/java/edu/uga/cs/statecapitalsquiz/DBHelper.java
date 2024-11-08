package edu.uga.cs.statecapitalsquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper  {
    /** The name of the database */
    private static final String DB_NAME = "project4.db";
    /** The version of the database */
    private static final int DB_VERSION = 1;
    /** The currently active helper instance of the database */
    private static DBHelper helperInstance;
    /** The context the app is running in */
    private Context context;

    /** SQL constants for scores table */
    public static final String TABLE_SCORES = "scores";
    public static final String SCORES_COLUMN_ID = "sid";
    public static final String SCORES_COLUMN_DATE = "date";
    public static final String SCORES_COLUMN_RESULT = "result";

    /** SQL constants for questions table */
    public static final String TABLE_QUESTIONS = "questions";
    public static final String QUESTIONS_COLUMN_ID = "qid";
    public static final String QUESTIONS_COLUMN_STATE = "state";
    public static final String QUESTIONS_COLUMN_CAPITAL = "capital";
    public static final String QUESTIONS_COLUMN_CITY2 = "city2";
    public static final String QUESTIONS_COLUMN_CITY3 = "city3";

    /** SQL constants for current quiz table */
    public static final String TABLE_CURRENT_QUIZ = "currentquiz";
    public static final String CURRENT_QUIZ_COLUMN_ID = "cqid";
    public static final String CURRENT_QUIZ_COLUMN_QUIZINFO = "quizInfo";


    /**
     * Private constructor to create a new singleton instance.
     * @param c the context to look for the database
     */
    private DBHelper(Context c) {
        super(c, DB_NAME, null, DB_VERSION);
        context = c;
    }

    /**
     * Gets the current singleton instance.
     * @param context The Android context
     * @return the current singleton instance
     */
    public static synchronized DBHelper getInstance(Context context) {
        if (helperInstance == null) helperInstance = new DBHelper(context.getApplicationContext());
        return helperInstance;
    }

    /**
     * Initializes tables upon creation of the database
     * @param db The database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create all necessary tables here
        String createScores = "create table scores (sid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "result REAL, date TEXT)";
        String createQuestions = "create table questions (qid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "state TEXT, capital TEXT, city2 TEXT, city3 TEXT)";
        String createCurrentQuiz = "create table currentquiz (cqid INTEGER PRIMARY KEY AUTOINCREMENT," +
                "quizInfo TEXT)"; // TODO
        db.execSQL(createScores);
        db.execSQL(createQuestions);
        db.execSQL(createCurrentQuiz);
        fillQuestionBank(db);
    }

    /**
     * Reads the CSV file into the "questions" table.
     * @param db the reference to the database
     */
    public void fillQuestionBank(SQLiteDatabase db) {
        try {
            InputStream is = context.getAssets().open("state_capitals.csv");
            CSVReader reader = new CSVReader(new InputStreamReader(is));
            String[] nextRow;
            reader.readNext();
            while ((nextRow = reader.readNext()) != null) {
                // Log.d("Project4", Arrays.toString(nextRow));
                ContentValues values = new ContentValues();
                values.put(QUESTIONS_COLUMN_STATE, nextRow[0].trim());
                values.put(QUESTIONS_COLUMN_CAPITAL, nextRow[1].trim());
                values.put(QUESTIONS_COLUMN_CITY2, nextRow[2].trim());
                values.put(QUESTIONS_COLUMN_CITY3, nextRow[3].trim());
                db.insert(TABLE_QUESTIONS, null, values);
            }
        } catch (Exception e) {
            Log.e("Project4", e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not used in this project.
    }
}
