package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class QuestionFragment extends Fragment {

    private static final String ARG_QUESTION = "question";
    private static final String ARG_POSITION = "position";
    private static final String ARG_TOTAL_QUESTIONS = "total_questions";

    private Question question;
    private int position, totalQuestions;

    public static QuestionFragment newInstance(Question question, int position, int totalQuestions) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_QUESTION, question);
        args.putInt(ARG_POSITION, position);
        args.putInt(ARG_TOTAL_QUESTIONS, totalQuestions);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            question = (Question) getArguments().getSerializable(ARG_QUESTION);
            position = getArguments().getInt(ARG_POSITION);
            totalQuestions = getArguments().getInt(ARG_TOTAL_QUESTIONS);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        TextView questionTextView = view.findViewById(R.id.questionTextView);
        TextView progressTextView = view.findViewById(R.id.questionProgressTextView);
        RadioButton answer1Button = view.findViewById(R.id.answer1);
        RadioButton answer2Button = view.findViewById(R.id.answer2);
        RadioButton answer3Button = view.findViewById(R.id.answer3);

        // Display question and answers
        questionTextView.setText("What is the capital of " + question.getState() + "?");
        answer1Button.setText(question.getAnswer1());
        answer2Button.setText(question.getAnswer2());
        answer3Button.setText(question.getAnswer3());

        progressTextView.setText("Question " + (position + 1) + " of " + totalQuestions);

        // Listener for answer selection
        RadioGroup answerGroup = view.findViewById(R.id.answerGroup);
        answerGroup.setOnCheckedChangeListener((group, checkedId) -> {
            String selectedAnswer = ((RadioButton) view.findViewById(checkedId)).getText().toString();
            if (selectedAnswer.equals(question.getCorrectAnswer())) {
                question.setCorrect();
            } else {
                question.setIncorrect();
            }
        });

        return view;
    }
}