package edu.cs.uga.proj4countryquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

//THIS IS WHAT IS SUPPOSED TO BE USED
//https://www.geeksforgeeks.org/android-recyclerview/

public class HistoryView extends AppCompatActivity {
    private QuizData quizData = null;
    private List<QuizHistory> quizHistoryList;
    private RecyclerView recyclerView;
    private HistoryAdapter historyAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_history);

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        quizHistoryList = new ArrayList<QuizHistory>();
        quizData = new QuizData(this);
        quizData.open();
        new HistoryDBReader().execute();

    }

    private class HistoryDBReader extends AsyncTask<Void, List<QuizHistory>, Object> {
        @Override
        protected List<QuizHistory> doInBackground(Void... params) {
            List<QuizHistory> quizHistoryList = quizData.retrieveAllHistory();
            Log.d("HistView", "Reached last question");
            onPostExecute(quizHistoryList);
            return quizHistoryList;
        }

        protected void onPostExecute(List<QuizHistory> quizHistory) {
            quizHistoryList.addAll(quizHistory);
            historyAdapter = new HistoryAdapter(quizHistoryList);
            recyclerView.setAdapter(historyAdapter);
            Log.d("HistView", "Doing it");
        }
    }

}
