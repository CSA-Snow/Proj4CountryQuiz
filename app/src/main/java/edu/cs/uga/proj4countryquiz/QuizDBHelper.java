package edu.cs.uga.proj4countryquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class QuizDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "QuizDBHelper";
    private static final int DB_Version = 1;

    //Table and column names for quiz questions
    public static final String TABLE_QUESTIONS = "quizquestions";
    public static final String QUESTIONS_ID = "qid";
    public static final String QUESTIONS_COUNTRY = "country";

    public static final String QUESTIONS_CONTINENT = "continent";

    public static final String CREATE_QUESTIONS =
            "create table " + TABLE_QUESTIONS + " ("
            + QUESTIONS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + QUESTIONS_COUNTRY + " TEXT, "
            + QUESTIONS_CONTINENT + " TEXT"
            + ")";

    //Table and column names for quiz history
    public static final String TABLE_HISTORY = "quizhistory";
    public static final String HISTORY_ID = "hid";
    public static final String HISTORY_SCORE = "historyscore";
    public static final String HISTORY_DATE = "historydate";

    //SQLite does not have a date format. Text format is "YYYY-MM-DD HH:MM:SS.SSS"
    public static final String CREATE_HISTORY =
            "create table " + TABLE_HISTORY + " ("
            + HISTORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + HISTORY_SCORE + " INTEGER, "
            + HISTORY_DATE + " INTEGER"
            + ")";

    private static QuizDBHelper helperInstance;

    private QuizDBHelper(Context context) {
        super(context,DB_NAME, null, DB_Version);
    }

    public synchronized static QuizDBHelper getInstance( Context context ) {
        if( helperInstance == null ) {
            helperInstance = new QuizDBHelper( context.getApplicationContext() );
        }
        return helperInstance;
    }

    @Override
    public void onCreate (SQLiteDatabase db) {
        db.execSQL(CREATE_QUESTIONS);
        db.execSQL(CREATE_HISTORY);
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL("drop table if exists " + TABLE_QUESTIONS);
        db.execSQL("drop table if exists " + TABLE_HISTORY);
        onCreate( db );
    }
}