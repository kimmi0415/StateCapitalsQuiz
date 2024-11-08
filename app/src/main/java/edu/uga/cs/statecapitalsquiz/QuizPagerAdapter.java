package edu.uga.cs.statecapitalsquiz;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * QuizPagerAdapter manages the fragments for each question in the quiz.
 * It extends FragmentStateAdapter to work with ViewPager2 for swiping between fragments.
 */
public class QuizPagerAdapter extends FragmentStateAdapter {

    private final Quiz quiz;
    private final Runnable resetCallback;
    private final Runnable newCallback;

    /**
     * Constructor for QuizPagerAdapter.
     *
     * @param fragmentManager The FragmentManager for managing fragments.
     * @param lifecycle       The lifecycle of the fragments.
     * @param quiz            The Quiz object containing the questions.
     * @param resetCallback   Runnable callback to reset the quiz.
     * @param newCallback     Runnable callback to start a new quiz.
     */
    public QuizPagerAdapter(FragmentManager fragmentManager, Lifecycle lifecycle, Quiz quiz, Runnable resetCallback, Runnable newCallback) {
        super(fragmentManager, lifecycle);
        this.quiz = quiz;
        this.resetCallback = resetCallback;
        this.newCallback = newCallback;
    }

    /**
     * Creates a new fragment for the given position.
     *
     * @param position The current position in the ViewPager.
     * @return A new instance of the appropriate fragment (QuestionFragment or EndScreenFragment).
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position < quiz.getNumberOfQuestions()) {
            // Return a question fragment for each question
            return QuestionFragment.newInstance(quiz.getQuestion(position), position, quiz.getNumberOfQuestions());
        } else {
            // Return the EndScreenFragment as the final screen
            Score finalScore = new Score(quiz);
            return EndScreenFragment.newInstance(finalScore.getScoreAsString(), finalScore.getDateString(), resetCallback, newCallback);
        }
    }

    /**
     * Returns the total number of fragments to be displayed.
     *
     * @return The number of questions plus one for the EndScreenFragment.
     */
    @Override
    public int getItemCount() {
        // Add 1 to include the EndScreenFragment
        return quiz.getNumberOfQuestions() + 1;
    }
}
