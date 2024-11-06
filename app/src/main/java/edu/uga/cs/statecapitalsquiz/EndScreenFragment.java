package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class EndScreenFragment extends Fragment {

    private static final String ARG_SCORE = "score";
    private static final String ARG_DATE = "date";
    private String finalScore;
    private String finalDate;
    private Runnable resetCallback;

    public static EndScreenFragment newInstance(String score, String date, Runnable resetCallback) {
        EndScreenFragment fragment = new EndScreenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SCORE, score); // Pass score as a string
        args.putString(ARG_DATE, date); // Pass date as a string
        fragment.setArguments(args);
        fragment.resetCallback = resetCallback; // Set the reset callback
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            finalScore = getArguments().getString(ARG_SCORE);
            finalDate = getArguments().getString(ARG_DATE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_end_screen, container, false);

        TextView scoreTextView = view.findViewById(R.id.finalScoreTextView);
        TextView dateTextView = view.findViewById(R.id.dateTextView);
        Button retakeQuizButton = view.findViewById(R.id.retakeQuizButton);

        // Display the final score and date
        scoreTextView.setText("Your final score: " + finalScore);
        dateTextView.setText("Date: " + finalDate);

        // Set up the retake quiz button
        retakeQuizButton.setOnClickListener(v -> {
            if (resetCallback != null) {
                resetCallback.run(); // Execute the reset callback
            }
        });

        return view;
    }
}
