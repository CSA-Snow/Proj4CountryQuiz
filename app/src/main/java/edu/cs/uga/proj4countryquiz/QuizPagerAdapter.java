package edu.cs.uga.proj4countryquiz;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class QuizPagerAdapter extends FragmentStateAdapter {

    private final List<QuestionFragment> fragments;

    public QuizPagerAdapter(@NonNull FragmentActivity fa, List<QuestionFragment> fragments) {
        super(fa);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
