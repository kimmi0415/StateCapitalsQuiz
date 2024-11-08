package edu.uga.cs.statecapitalsquiz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * ScoreRecyclerAdapter is a RecyclerView adapter that binds a list of Score objects
 * to the corresponding views for display in a RecyclerView.
 */
public class ScoreRecyclerAdapter extends RecyclerView.Adapter<ScoreRecyclerAdapter.ScoreViewHolder> {

    private final List<Score> scoreList;

    /**
     * Constructor for ScoreRecyclerAdapter.
     *
     * @param scoreList The list of Score objects to be displayed.
     */
    public ScoreRecyclerAdapter(List<Score> scoreList) {
        this.scoreList = scoreList;
    }

    /**
     * Called when RecyclerView needs a new ViewHolder to represent an item.
     *
     * @param parent The parent ViewGroup into which the new view will be added.
     * @param viewType The view type of the new view.
     * @return A new instance of ScoreViewHolder.
     */
    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_score, parent, false);
        return new ScoreViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder The ScoreViewHolder that should be updated with new data.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
        Score score = scoreList.get(position);
        holder.scoreTextView.setText("Score: " + score.getScoreAsString());
        holder.dateTextView.setText("Date: " + score.getDateString());
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The size of the score list.
     */
    @Override
    public int getItemCount() {
        return scoreList.size();
    }

    /**
     * Holds the views for each item in the RecyclerView.
     */
    static class ScoreViewHolder extends RecyclerView.ViewHolder {
        TextView scoreTextView, dateTextView;

        public ScoreViewHolder(View itemView) {
            super(itemView);
            scoreTextView = itemView.findViewById(R.id.scoreTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
        }
    }
}
