package edu.uga.cs.statecapitalsquiz;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

/**
 * QuizFragment class handles the main quiz flow.
 * It uses ViewPager2 for swiping between questions and manages the quiz state.
 */
public class QuizFragment extends Fragment {


    private Quiz quiz;
    // private int currentQuestionIndex = 0;
    // private RadioButton answer1Button, answer2Button, answer3Button;
    // private TextView questionTextView, questionProgressTextView;
    private Button retakeQuizButton;
    private QuizData quizData;
    // private RadioGroup answerGroup;
    private boolean isFinalSwipe = false; // Track if the final swipe has occurred
    private Score finalScore;

    /**
     * Default constructor for QuizFragment.
     */
    public QuizFragment() {
        // Required empty public constructor
    }


    /**
     * Creates a new instance of QuizFragment.
     *
     * @return A new instance of QuizFragment.
     */
    public static QuizFragment newInstance() {
        QuizFragment fragment = new QuizFragment();
        return fragment;
    }

    /**
     * Called to create the view hierarchy associated with this fragment.
     *
     * @param inflater  The LayoutInflater object to inflate the fragment's view.
     * @param container The ViewGroup container where the fragment's UI will be attached.
     * @param savedInstanceState A Bundle containing the saved state of the fragment.
     * @return The root view for the fragment's UI.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        // Initialize quiz and database
        quiz = new Quiz(getActivity());
        quizData = new QuizData(getActivity());

        // Set up ViewPager2 for swiping between questions
        ViewPager2 viewPager = view.findViewById(R.id.viewpager);
        QuizPagerAdapter adapter = new QuizPagerAdapter(getChildFragmentManager(), getLifecycle(), quiz, this::resetQuiz, this::newQuiz);
        viewPager.setAdapter(adapter);

        // Register callback for detecting the end screen swipe
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                // Check if the user has reached the EndScreenFragment
                if (position == quiz.getNumberOfQuestions()) {
                    // Save the score asynchronously
                    new SaveScoreTask().execute(new Score(quiz));
                }
            }
        });

        return view;
    }

    /**
     * Resets the current quiz and navigates back to the first question.
     */
    private void resetQuiz() {
        quiz.reset();
        ViewPager2 viewPager = requireView().findViewById(R.id.viewpager);
        viewPager.setCurrentItem(0, false); // Go back to the first question
    }

    /**
     * Starts a new quiz by creating a new Quiz object and updating the ViewPager2 adapter.
     */
    private void newQuiz() {
        quiz = new Quiz();
        ViewPager2 viewPager = requireView().findViewById(R.id.viewpager);
        QuizPagerAdapter adapter = new QuizPagerAdapter(getChildFragmentManager(), getLifecycle(), quiz, this::resetQuiz, this::newQuiz);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0, false); // Go back to the first question
    }

    /**
     * Called when the fragment is paused.
     * This can be used for saving the state or releasing resources.
     */
    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * Called when the fragment is resumed.
     * This can be used for restoring the state or updating the UI.
     */
    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * AsyncTask for saving the final score to the database.
     * This operation is done in the background to avoid blocking the UI thread.
     */
    private class SaveScoreTask extends AsyncTask<Score, Void> {

        /**
         * Performs the database operation to save the score in the background.
         *
         * @param scores The scores to be saved (only one score is expected).
         * @return null (no result needed).
         */
        @Override
        protected Void doInBackground(Score... scores) {
            quizData.open();
            quizData.addScore(scores[0]);
            quizData.close();
            return null;
        }

        /**
         * Called after the background operation is completed.
         *
         * @param result The result of the background computation.
         */
        @Override
        protected void onPostExecute(Void result) {
        }
    }
}