package edu.cs.uga.proj4countryquiz;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class StartQuizActivity extends AppCompatActivity {

    private static final String TAG = "StartQuizActivity";
    private static final int NUM_QUESTIONS = 6;

    private ViewPager2 viewPager;
    private List<QuestionFragment> questionFragments;
    private List<Question> selectedQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_quiz);

        viewPager = findViewById(R.id.questionPager);
        questionFragments = new ArrayList<>();

        loadQuestionsFromDatabase();
    }

    private void loadQuestionsFromDatabase() {
        QuizDBHelper dbHelper = QuizDBHelper.getInstance(this);
        List<Question> allQuestions = new ArrayList<>();
        HashSet<String> allContinents = new HashSet<>();

        try (var db = dbHelper.getReadableDatabase();
             var cursor = db.rawQuery("SELECT * FROM " + QuizDBHelper.TABLE_QUESTIONS, null)) {

            while (cursor.moveToNext()) {
                String country = cursor.getString(cursor.getColumnIndexOrThrow(QuizDBHelper.QUESTIONS_COUNTRY));
                String continent = cursor.getString(cursor.getColumnIndexOrThrow(QuizDBHelper.QUESTIONS_CONTINENT));
                allQuestions.add(new Question(country, continent));
                allContinents.add(continent);
            }
        }

        Collections.shuffle(allQuestions);
        selectedQuestions = allQuestions.subList(0, NUM_QUESTIONS);

        for (Question q : selectedQuestions) {
            List<String> choices = new ArrayList<>();
            choices.add(q.getCorrectAnswer());

            List<String> otherContinents = new ArrayList<>(allContinents);
            otherContinents.remove(q.getCorrectAnswer());
            Collections.shuffle(otherContinents);

            choices.add(otherContinents.get(0));
            choices.add(otherContinents.get(1));
            Collections.shuffle(choices);

            QuestionFragment fragment = QuestionFragment.newInstance(q.getCountry(), choices);
            questionFragments.add(fragment);
        }

        QuizPagerAdapter adapter = new QuizPagerAdapter(this, questionFragments);
        viewPager.setAdapter(adapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == NUM_QUESTIONS - 1) {
                    Log.d(TAG, "Reached last question");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager2.SCROLL_STATE_IDLE && viewPager.getCurrentItem() == NUM_QUESTIONS - 1) {
                    if (allQuestionsAnswered()) {
                        showResults();
                    } else {
                        Log.d(TAG, "Not all questions answered yet.");
                    }
                }
            }
        });
    }

    private boolean allQuestionsAnswered() {
        for (QuestionFragment qf : questionFragments) {
            if (qf.getSelectedAnswer() == null) {
                return false;
            }
        }
        return true;
    }

    private void showResults() {
        final int score;
        int tempScore = 0;

        for (int i = 0; i < NUM_QUESTIONS; i++) {
            QuestionFragment qf = questionFragments.get(i);
            String selected = qf.getSelectedAnswer();
            String correct = selectedQuestions.get(i).getCorrectAnswer();
            if (correct.equals(selected)) {
                tempScore++;
            }
        }
        score = tempScore;

        Log.d(TAG, "Final Score: " + score + "/" + NUM_QUESTIONS);

        // Save score using QuizData class in a background thread
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                QuizData quizData = new QuizData(StartQuizActivity.this);
                quizData.open();
                quizData.submitQuiz(score);
                quizData.close();
                Log.d(TAG, "Quiz score submitted via QuizData.");
                return null;
            }
        }.execute();

        runOnUiThread(() -> {
            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Quiz Complete")
                    .setMessage("You scored " + score + " out of " + NUM_QUESTIONS)
                    .setPositiveButton("OK", (dialog, which) -> finish())
                    .setCancelable(false)
                    .show();
        });
    }
}
