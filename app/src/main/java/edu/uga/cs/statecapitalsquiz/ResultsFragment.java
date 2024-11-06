package edu.uga.cs.statecapitalsquiz;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ResultsFragment extends Fragment {

    private RecyclerView resultsRecyclerView;
    private QuizData quizData;
    private ScoreRecyclerAdapter scoreAdapter;
    private List<Score> scoreList;
    private static final String TAG = "ResultsFragment";

    public ResultsFragment() {
        // Required empty public constructor
    }

    public static ResultsFragment newInstance() {
        ResultsFragment fragment = new ResultsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results, container, false);

        // Initialize RecyclerView and adapter
        resultsRecyclerView = view.findViewById(R.id.resultsRecyclerView);
        resultsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        scoreList = new ArrayList<>();
        scoreAdapter = new ScoreRecyclerAdapter(scoreList);
        resultsRecyclerView.setAdapter(scoreAdapter);

        // Initialize QuizData for database access
        quizData = new QuizData(getActivity());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Open the database
        if (quizData != null) {
            quizData.open();
            Log.d(TAG, "QuizResultsFragment: Database opened");
        }

        // Load scores asynchronously
        new LoadScoresTask().execute();
    }

    @Override
    public void onPause() {
        super.onPause();

        // Close the database
        if (quizData != null) {
            quizData.close();
            Log.d(TAG, "QuizResultsFragment: Database closed");
        }
    }

    // AsyncTask to load scores from the database
    private class LoadScoresTask extends AsyncTask<Void, List<Score>> {

        @Override
        protected List<Score> doInBackground(Void... voids) {
            // Retrieve all scores
            return quizData.getAllScores();
        }

        @Override
        protected void onPostExecute(List<Score> scores) {
            // Update the RecyclerView with the retrieved scores
            scoreList.clear();
            scoreList.addAll(scores);
            scoreAdapter.notifyDataSetChanged();
        }
    }
}
