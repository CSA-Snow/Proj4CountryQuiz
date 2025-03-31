package edu.cs.uga.proj4countryquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CSVLoaderTask extends AsyncTask<Void, Void, Void> {

    private static final String TAG = "CSVLoaderTask";
    private Context context;

    public CSVLoaderTask(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Log.d(TAG, "doInBackground: STARTED");

        try {
            QuizDBHelper dbHelper = QuizDBHelper.getInstance(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            // Check if data already exists
            Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + QuizDBHelper.TABLE_QUESTIONS, null);
            boolean dbPopulated = false;
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int count = cursor.getInt(0);
                    dbPopulated = (count > 0);
                    Log.d(TAG, "Questions already in DB: " + count);
                }
                cursor.close();
            }

            if (dbPopulated) {
                Log.d(TAG, "Database already populated. Skipping CSV load.");
                return null;
            }

            Log.d(TAG, "Reading CSV from raw folder...");
            InputStream inputStream = context.getResources().openRawResource(R.raw.country_continent);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            db.beginTransaction();
            int inserted = 0;

            while ((line = reader.readLine()) != null) {
                Log.d(TAG, "CSV Line: " + line);
                String[] tokens = line.split(",");
                if (tokens.length == 2) {
                    String country = tokens[0].trim();
                    String continent = tokens[1].trim();

                    ContentValues values = new ContentValues();
                    values.put(QuizDBHelper.QUESTIONS_COUNTRY, country);
                    values.put(QuizDBHelper.QUESTIONS_CONTINENT, continent);

                    db.insert(QuizDBHelper.TABLE_QUESTIONS, null, values);
                    inserted++;
                } else {
                    Log.w(TAG, "Skipping invalid CSV line: " + line);
                }
            }

            db.setTransactionSuccessful();
            db.endTransaction();
            reader.close();

            Log.d(TAG, "CSV loading complete. Total inserted: " + inserted);

        } catch (Exception e) {
            Log.e(TAG, "Exception during CSV loading", e);
        }

        return null;
    }
}
