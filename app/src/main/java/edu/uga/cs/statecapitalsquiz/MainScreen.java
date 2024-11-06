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


public class MainScreen extends Fragment {

    public MainScreen() {
        // Required empty public constructor
    }

    public static MainScreen newInstance() {
        MainScreen fragment = new MainScreen();
        return fragment;
    }

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

        return view;
    }

    @Override
    public void onViewCreated( @NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle( getResources().getString( R.string.app_name ) );
    }

  }