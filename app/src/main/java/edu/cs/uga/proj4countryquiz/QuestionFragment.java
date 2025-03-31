package edu.cs.uga.proj4countryquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;

public class QuestionFragment extends Fragment {

    private static final String ARG_COUNTRY = "arg_country";
    private static final String ARG_CHOICES = "arg_choices";

    private String countryName;
    private List<String> choices;

    private int selectedAnswerIndex = -1;

    public interface OnAnswerSelectedListener {
        void onAnswerSelected(int position, int selectedIndex);
    }

    public static QuestionFragment newInstance(String countryName, List<String> choices) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_COUNTRY, countryName);
        args.putStringArrayList(ARG_CHOICES, new ArrayList<>(choices));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            countryName = getArguments().getString(ARG_COUNTRY);
            choices = getArguments().getStringArrayList(ARG_CHOICES);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_question, container, false);

        TextView questionText = view.findViewById(R.id.questionText);
        RadioGroup answerGroup = view.findViewById(R.id.answerGroup);
        RadioButton option1 = view.findViewById(R.id.option1);
        RadioButton option2 = view.findViewById(R.id.option2);
        RadioButton option3 = view.findViewById(R.id.option3);

        questionText.setText("Which continent is " + countryName + " located on?");

        option1.setText(choices.get(0));
        option2.setText(choices.get(1));
        option3.setText(choices.get(2));

        answerGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.option1) selectedAnswerIndex = 0;
            else if (checkedId == R.id.option2) selectedAnswerIndex = 1;
            else if (checkedId == R.id.option3) selectedAnswerIndex = 2;
            // Later we can pass this to the activity
        });

        return view;
    }

    public int getSelectedAnswerIndex() {
        return selectedAnswerIndex;
    }

    public String getSelectedAnswer() {
        if (selectedAnswerIndex >= 0 && selectedAnswerIndex < choices.size()) {
            return choices.get(selectedAnswerIndex);
        }
        return null;
    }
}
