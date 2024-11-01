package edu.uga.cs.statecapitalsquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.*;

public class QuizData {
    private SQLiteDatabase db;
    private SQLiteOpenHelper helper;
    public static List<Question> questionMasterList;

    public QuizData(Context context) {
        this.helper = DBHelper.getInstance(context);
    }
    public void open() {
        db = helper.getWritableDatabase();
        if (questionMasterList == null) {
            questionMasterList = new ArrayList<>();
            getListOfQuestions();
        }
    }
    public void close() {
        if (helper != null) helper.close();
    }

    public void getListOfQuestions() {
        Cursor cursor = db.query(DBHelper.TABLE_QUESTIONS,
                new String[] {DBHelper.QUESTIONS_COLUMN_ID,
                        DBHelper.QUESTIONS_COLUMN_STATE,
                        DBHelper.QUESTIONS_COLUMN_CAPITAL,
                        DBHelper.QUESTIONS_COLUMN_CITY2,
                        DBHelper.QUESTIONS_COLUMN_CITY3}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int stateCid = cursor.getColumnIndex(DBHelper.QUESTIONS_COLUMN_STATE);
            int capitalCid = cursor.getColumnIndex(DBHelper.QUESTIONS_COLUMN_CAPITAL);
            int city1Cid = cursor.getColumnIndex(DBHelper.QUESTIONS_COLUMN_CITY2);
            int city2Cid = cursor.getColumnIndex(DBHelper.QUESTIONS_COLUMN_CITY3);
            String state = cursor.getString(stateCid);
            String capital = cursor.getString(capitalCid);
            String city1 = cursor.getString(city1Cid);
            String city2 = cursor.getString(city2Cid);
            questionMasterList.add(new Question(state, capital, city1, city2));
        } // while
        // Log.d("Project4", "" + questionMasterList);
    }

    public List<Question> getQuestionList() {
        return questionMasterList;
    }

    public void addScore(Score s) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.SCORES_COLUMN_RESULT, s.score);
        values.put(DBHelper.SCORES_COLUMN_DATE, s.dateString);
        db.insert(DBHelper.TABLE_SCORES, null, values);
    }

    public List<Score> getAllScores() {
        List<Score> scores = new ArrayList<>();
        Cursor cursor = db.query(DBHelper.TABLE_SCORES,
                new String[] {DBHelper.SCORES_COLUMN_ID,
                        DBHelper.SCORES_COLUMN_RESULT,
                        DBHelper.SCORES_COLUMN_DATE}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int resultCid = cursor.getColumnIndex(DBHelper.SCORES_COLUMN_RESULT);
            int dateCid = cursor.getColumnIndex(DBHelper.SCORES_COLUMN_DATE);
            double result = cursor.getDouble(resultCid);
            String date = cursor.getString(dateCid);
            scores.add(new Score(result, date));
        } // while
        scores.sort((a, b) -> b.date.compareTo(a.date));
        return scores;
    }

    public void storeCurrentQuiz(Quiz q) {
        // TODO
    }

    public Quiz getCurrentQuiz() {
        // TODO
        return null;
    }
}

