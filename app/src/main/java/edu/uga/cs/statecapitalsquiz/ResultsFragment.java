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

/**
 * ResultsFragment displays the list of past quiz scores using a RecyclerView.
 * It loads data from the database and updates the UI asynchronously.
 */
public class ResultsFragment extends Fragment {

    private RecyclerView resultsRecyclerView;
    private QuizData quizData;
    private ScoreRecyclerAdapter scoreAdapter;
    private List<Score> scoreList;
    private static final String TAG = "ResultsFragment";

    /**
     * Default constructor for ResultsFragment.
     */
    public ResultsFragment() {
        // Required empty public constructor
    }

    /**
     * Factory method to create a new instance of ResultsFragment.
     *
     * @return A new instance of ResultsFragment.
     */
    public static ResultsFragment newInstance() {
        ResultsFragment fragment = new ResultsFragment();
        return fragment;
    }


    /**
     * Called to create the view hierarchy associated with this fragment.
     *
     * @param inflater  The LayoutInflater object to inflate the fragment's view.
     * @param container The ViewGroup container where the fragment's UI will be attached.
     * @param savedInstanceState A Bundle containing the saved state of the fragment (if any).
     * @return The root view for the fragment's UI.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results, container, false);

        // Set up RecyclerView
        resultsRecyclerView = view.findViewById(R.id.resultsRecyclerView);
        resultsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        scoreList = new ArrayList<>();
        scoreAdapter = new ScoreRecyclerAdapter(scoreList);
        resultsRecyclerView.setAdapter(scoreAdapter);

        // Initialize QuizData for database access
        quizData = new QuizData(getActivity());

        return view;
    }

    /**
     * Called when the fragment is visible to the user.
     * Opens the database connection and starts loading scores asynchronously.
     */
    @Override
    public void onResume() {
        super.onResume();

        quizData.open();
        Log.d(TAG, "ResultsFragment: Database opened");

        // Load scores asynchronously
        new LoadScoresTask().execute();
    }

    /**
     * Called when the fragment is no longer visible to the user.
     * Closes the database connection to release resources.
     */
    @Override
    public void onPause() {
        super.onPause();

        quizData.close();
        Log.d(TAG, "ResultsFragment: Database closed");
    }

    /**
     * AsyncTask for loading quiz scores from the database.
     * This operation is performed in the background to avoid blocking the UI thread.
     */
    private class LoadScoresTask extends AsyncTask<Void, List<Score>> {

        /**
         * Performs the database query to retrieve all quiz scores in the background.
         *
         * @param voids No parameters are used for this task.
         * @return A list of Score objects retrieved from the database.
         */
        @Override
        protected List<Score> doInBackground(Void... voids) {
            // Retrieve all scores in descending order
            return quizData.getAllScores();
        }

        /**
         * Called after the background task is completed.
         * Updates the UI with the retrieved list of scores.
         *
         * @param scores The list of Score objects retrieved from the database.
         */
        @Override
        protected void onPostExecute(List<Score> scores) {
            // Update the RecyclerView with the retrieved scores
            scoreList.clear();
            scoreList.addAll(scores);
            scoreAdapter.notifyDataSetChanged();
        }
    }
}
