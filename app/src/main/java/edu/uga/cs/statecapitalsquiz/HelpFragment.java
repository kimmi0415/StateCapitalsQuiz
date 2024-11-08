package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * HelpFragment class displays the help text for the State Capitals Quiz app.
 * It shows information about how to use the app and provides guidance to the user.
 */
public class HelpFragment extends Fragment {

    /**
     * Default constructor for HelpFragment.
     * This is required for proper fragment initialization.
     */
    public HelpFragment() {
        // Required empty public constructor
    }

    /**
     * Creates a new instance of HelpFragment.
     *
     * @return A new instance of HelpFragment.
     */
    public static HelpFragment newInstance() {
        return new HelpFragment();
    }

    /**
     * Called to create the view hierarchy associated with this fragment.
     *
     * @param inflater           LayoutInflater used to inflate the fragment's view.
     * @param container          ViewGroup container that the fragment's UI will be attached to.
     * @param savedInstanceState Bundle containing the saved state of the fragment.
     * @return The root view for the fragment's UI.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_help, container, false);

        // Find the TextView and set the HTML formatted text
        TextView helpTextView = view.findViewById(R.id.helpTextView);

        // Set the help text using HTML formatting from the strings.xml resource
        helpTextView.setText(Html.fromHtml(getString(R.string.help_text), Html.FROM_HTML_MODE_LEGACY));

        // Return the inflated view for this fragment
        return view;
    }

}
