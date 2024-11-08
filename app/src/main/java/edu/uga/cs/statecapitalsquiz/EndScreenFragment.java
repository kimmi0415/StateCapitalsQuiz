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

/**
 * EndScreenFragment shows the final quiz score and date, offering options to retake the quiz or start a new one.
 */
public class EndScreenFragment extends Fragment {

    private static final String ARG_SCORE = "score";
    private static final String ARG_DATE = "date";
    private String finalScore;
    private String finalDate;
    private Runnable resetCallback;
    private Runnable newCallback;

    /**
     * Creates a new instance of EndScreenFragment with the given arguments.
     *
     * @param score         The final score of the quiz as a string.
     * @param date          The date when the quiz was completed.
     * @param resetCallback Callback to reset the current quiz.
     * @param newCallback   Callback to start a new quiz.
     * @return A new instance of EndScreenFragment.
     */
    public static EndScreenFragment newInstance(String score, String date, Runnable resetCallback, Runnable newCallback) {
        EndScreenFragment fragment = new EndScreenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SCORE, score); // Pass score as a string
        args.putString(ARG_DATE, date); // Pass date as a string
        fragment.setArguments(args);
        fragment.resetCallback = resetCallback; // Set the reset callback
        fragment.newCallback = newCallback;
        return fragment;
    }

    /**
     * Called when the fragment is first created. Retrieves the score and date arguments.
     *
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            finalScore = getArguments().getString(ARG_SCORE);
            finalDate = getArguments().getString(ARG_DATE);
        }
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate views in the fragment.
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return The root View of the fragment's layout.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_end_screen, container, false);

        // Initialize UI elements
        TextView scoreTextView = view.findViewById(R.id.finalScoreTextView);
        TextView dateTextView = view.findViewById(R.id.dateTextView);
        TextView noteTextView = view.findViewById(R.id.noteTextView);
        Button retakeQuizButton = view.findViewById(R.id.retakeQuizButton);
        Button startNewQuizButton = view.findViewById(R.id.startNewQuizButton); // Initialize startNewQuizButton

        // Display the final score and date
        scoreTextView.setText("Final Score: " + finalScore);
        dateTextView.setText("Date: " + finalDate);

        // Set up the retake quiz button to reload the same quiz
        retakeQuizButton.setOnClickListener(v -> {
            if (resetCallback != null) {
                resetCallback.run(); // Execute the reset callback to retake the same quiz
            }
        });

        // Set up the start new quiz button to load a fresh set of questions
        startNewQuizButton.setOnClickListener(v -> {
            if (newCallback != null) {
                newCallback.run();
            }
        });
        return view;
    }
}
