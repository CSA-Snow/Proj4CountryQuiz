package edu.cs.uga.proj4countryquiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder> {
    //private final Context context;
    private List<QuizHistory> values;
    private List<QuizHistory> ogValues;

    public HistoryAdapter(List<QuizHistory> quizHistory) {
        //this.context = context;
        this.values = quizHistory;
        this.ogValues = new ArrayList<QuizHistory>(quizHistory);
    }

    public void sync() {
        ogValues = new ArrayList<QuizHistory>(values);
    }

    public static class HistoryHolder extends RecyclerView.ViewHolder {
        TextView score;
        TextView date;

        public HistoryHolder(View itemView) {
            super(itemView);
            score = itemView.findViewById(R.id.score);
            date = itemView.findViewById(R.id.date);
        }
    }

    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_history, parent, false);
        return new HistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryHolder holder, int position) {
        QuizHistory quizHistory = values.get(position);

        holder.date.setText(quizHistory.getJavaDate().toString());
        holder.score.setText(""+quizHistory.getScore());
    }

    @Override
    public int getItemCount() {
        if (values != null) {
            return values.size();
        } else {
            return 0;
        }
    }
}
