package edu.uga.cs.statecapitalsquiz;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * MainScreen fragment serves as the landing screen of the app.
 */
public class MainScreen extends Fragment {

    /**
     * Default constructor for MainScreen.
     */
    public MainScreen() {
        // Required empty public constructor
    }

    /**
     * Creates a new instance of MainScreen.
     *
     * @return A new instance of MainScreen fragment.
     */
    public static MainScreen newInstance() {
        MainScreen fragment = new MainScreen();
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
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_screen, container, false);

        // Find the Start Quiz button and set up the click listener
        Button startQuizButton = view.findViewById(R.id.startQuizButton);
        startQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Begin a fragment transaction to replace MainScreen with QuizFragment
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerView, QuizFragment.newInstance());
                transaction.addToBackStack(null); // Adds the transaction to the back stack
                transaction.commit();
            }
        });

        // Return the inflated view for this fragment
        return view;
    }

    /**
     * Called after the fragment's view has been created.
     *
     * @param view The root view of the fragment.
     * @param savedInstanceState The saved state of the fragment.
     */
    @Override
    public void onViewCreated( @NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );
    }

    /**
     * Called when the fragment becomes visible to the user.
     * This method sets the title of the ActionBar to the app name.
     */
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle( getResources().getString( R.string.app_name ) );
    }

  }