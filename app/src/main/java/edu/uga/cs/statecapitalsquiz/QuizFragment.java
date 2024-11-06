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

import androidx.fragment.app.Fragment;

public class QuizFragment extends Fragment {

    private Quiz quiz;
    private int currentQuestionIndex = 0;
    private RadioButton answer1Button, answer2Button, answer3Button;
    private TextView questionTextView, questionProgressTextView;
    private Button retakeQuizButton;
    private QuizData quizData;
    // private RadioGroup answerGroup;

    public QuizFragment() {
        // Required empty public constructor
    }

    public static QuizFragment newInstance() {
        QuizFragment fragment = new QuizFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        // Initialize UI elements
        questionTextView = view.findViewById(R.id.questionTextView);
        questionProgressTextView = view.findViewById(R.id.questionProgressTextView);
        answer1Button = view.findViewById(R.id.answer1);
        answer2Button = view.findViewById(R.id.answer2);
        answer3Button = view.findViewById(R.id.answer3);
        retakeQuizButton = view.findViewById(R.id.retakeQuizButton);

        // Initialize QuizData for database access
        quizData = new QuizData(getActivity());

        // Load quiz questions
        loadQuiz();

        // Set up answer button listeners
        setAnswerListeners();

        // Set up retake button listener
        retakeQuizButton.setOnClickListener(v -> resetQuiz());

        return view;
    }

    private void loadQuiz() {
        quizData.open();
        quiz = new Quiz(getActivity()); // Assuming this initializes with 6 questions
        quizData.close();

        displayQuestion();
    }

    private void displayQuestion() {
        Question question = quiz.getQuestion(currentQuestionIndex);

        // Set question text and answer options
        questionTextView.setText("What is the capital of " + question.getState() + "?");
        answer1Button.setText(question.getAnswer1());
        answer2Button.setText(question.getAnswer2());
        answer3Button.setText(question.getAnswer3());

        // Update question progress
        questionProgressTextView.setText("Question " + (currentQuestionIndex + 1) + " of " + quiz.getNumberOfQuestions());

        // Clear any previous selection
        answer1Button.setChecked(false);
        answer2Button.setChecked(false);
        answer3Button.setChecked(false);
    }

    private void setAnswerListeners() {
        View.OnClickListener listener = view -> {
            Question question = quiz.getQuestion(currentQuestionIndex);
            RadioButton selectedButton = (RadioButton) view;
            String selectedAnswer = selectedButton.getText().toString();

            // Check if selected answer is correct and update question status
            if (selectedAnswer.equals(question.getCorrectAnswer())) {
                question.setCorrect();
            } else {
                question.setIncorrect();
            }

            // Move to next question or complete the quiz
            if (currentQuestionIndex < quiz.getNumberOfQuestions() - 1) {
                currentQuestionIndex++;
                displayQuestion();
            } else {
                completeQuiz();
            }
        };

        // Attach listener to each answer button
        answer1Button.setOnClickListener(listener);
        answer2Button.setOnClickListener(listener);
        answer3Button.setOnClickListener(listener);
    }

    private void completeQuiz() {
        // Calculate and save score
        new SaveScoreTask().execute(new Score(quiz));
        // Display final score
        displayScore();
        // Show the retake button
        retakeQuizButton.setVisibility(View.VISIBLE);
    }

    private void displayScore() {
        Score score = new Score(quiz);
        Toast.makeText(getActivity(), "Quiz completed! Your score: " + score.getScoreAsString(), Toast.LENGTH_LONG).show();
    }

    private void resetQuiz() {
        // Reset the quiz for retaking
        currentQuestionIndex = 0;
        loadQuiz();
    }

    public class SaveScoreTask extends AsyncTask<Score, Score> {
        @Override
        protected Score doInBackground(Score... scores) {
            quizData.open();
            quizData.addScore(scores[0]);
            quizData.close();
            return scores[0];
        }

        @Override
        protected void onPostExecute(Score score) {
            Toast.makeText(getActivity(), "Score saved: " + score.getScoreAsString(), Toast.LENGTH_SHORT).show();
        }
    }
}
