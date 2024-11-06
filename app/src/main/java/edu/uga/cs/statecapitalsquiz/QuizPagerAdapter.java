package edu.uga.cs.statecapitalsquiz;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class QuizPagerAdapter extends FragmentStateAdapter {

    private final Quiz quiz;
    private final Runnable resetCallback;

    public QuizPagerAdapter(FragmentManager fragmentManager, Lifecycle lifecycle, Quiz quiz, Runnable resetCallback) {
        super(fragmentManager, lifecycle);
        this.quiz = quiz;
        this.resetCallback = resetCallback;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position < quiz.getNumberOfQuestions()) {
            // Return a question fragment for each question
            return QuestionFragment.newInstance(quiz.getQuestion(position), position, quiz.getNumberOfQuestions());
        } else {
            // Return the EndScreenFragment as the final screen
            Score finalScore = new Score(quiz);
            return EndScreenFragment.newInstance(finalScore.getScoreAsString(), finalScore.getDateString(), resetCallback);
        }
    }

    @Override
    public int getItemCount() {
        // Add 1 to include the EndScreenFragment
        return quiz.getNumberOfQuestions() + 1;
    }
}
