package edu.cs.uga.proj4countryquiz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class QuizData {
    private SQLiteDatabase db;
    private SQLiteOpenHelper dbHelper;
    private static final String[] questionsColumns = {
            QuizDBHelper.QUESTIONS_ID,
            QuizDBHelper.QUESTIONS_COUNTRY,
            QuizDBHelper.QUESTIONS_CONTINENT
    };
    private static final String[] historyColumns = {
            QuizDBHelper.HISTORY_DATE,
            QuizDBHelper.HISTORY_SCORE,
            QuizDBHelper.HISTORY_DATE
    };

    public QuizData(Context context) {
        dbHelper = QuizDBHelper.getInstance(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    public boolean isDBOpen() {
        return db.isOpen();
    }

    public List<QuizHistory> retrieveAllHistory() {
        ArrayList<QuizHistory> quizHist = new ArrayList<>();
        Cursor cursor = null;
        int columnIndex;
        try {
            cursor = db.query(QuizDBHelper.TABLE_HISTORY, historyColumns,
                    null, null, null, null, QuizDBHelper.HISTORY_DATE);

            // collect all job leads into a List
            if (cursor != null && cursor.getCount() > 0) {

                while (cursor.moveToNext()) {

                    if (cursor.getColumnCount() >= 3) {
                        columnIndex = cursor.getColumnIndex(QuizDBHelper.HISTORY_ID);
                        int hid = cursor.getInt(columnIndex);
                        columnIndex = cursor.getColumnIndex(QuizDBHelper.HISTORY_SCORE);
                        int score = cursor.getInt(columnIndex);
                        columnIndex = cursor.getColumnIndex(QuizDBHelper.HISTORY_DATE);
                        long date = cursor.getLong(columnIndex);

                        QuizHistory quizHistory = new QuizHistory(hid, score, date);
                        quizHist.add(quizHistory);
                    }
                }
            }
        } catch( Exception e ){
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return quizHist;
    }

    public Question getQuestion(int requestedQid) {
        Cursor cursor = null;
        Question question = null;
        try {
            cursor = db.rawQuery("SELECT * FROM "+QuizDBHelper.TABLE_QUESTIONS+" WHERE qid="+requestedQid, null);
            if (cursor != null && cursor.getCount() > 0 && cursor.getColumnCount() >= 3) {
                int columnIndex = cursor.getColumnIndex(QuizDBHelper.QUESTIONS_ID);
                int qid = cursor.getInt(columnIndex);
                columnIndex = cursor.getColumnIndex(QuizDBHelper.QUESTIONS_COUNTRY);
                String country = cursor.getString(columnIndex);
                columnIndex = cursor.getColumnIndex(QuizDBHelper.QUESTIONS_CONTINENT);
                String continent = cursor.getString(columnIndex);
                question = new Question(qid, country, continent);
            }
        } catch (Exception e) {
        } finally {
        if (cursor != null) {
            cursor.close();
        }
    }
        return question;
    }

}
