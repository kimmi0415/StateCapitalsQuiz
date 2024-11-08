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

    public QuizFragment() {
        // Required empty public constructor
    }


    public static QuizFragment newInstance() {
        QuizFragment fragment = new QuizFragment();
        return fragment;
    }

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

    private void resetQuiz() {
        quiz.reset();
        ViewPager2 viewPager = requireView().findViewById(R.id.viewpager);
        viewPager.setCurrentItem(0, false); // Go back to the first question
    }

    private void newQuiz() {
        quiz = new Quiz();
        ViewPager2 viewPager = requireView().findViewById(R.id.viewpager);
        QuizPagerAdapter adapter = new QuizPagerAdapter(getChildFragmentManager(), getLifecycle(), quiz, this::resetQuiz, this::newQuiz);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0, false); // Go back to the first question
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    // AsyncTask for saving the score to the database
    private class SaveScoreTask extends AsyncTask<Score, Void> {
        @Override
        protected Void doInBackground(Score... scores) {
            quizData.open();
            quizData.addScore(scores[0]); // Add final score to the database
            quizData.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Optionally, show a toast confirming that the score was saved
        }
    }
}