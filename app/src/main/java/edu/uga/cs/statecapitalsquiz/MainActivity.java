package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    // private QuizData quizData; <-- up to you how you want to implement this
    private final String DEBUG_TAG = "Project4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** temp testing for database
        quizData = new QuizData(getApplicationContext());
        quizData.open();
        testDatabase(quizData);
        quizData.close(); */

        // Set up toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize DrawerLayout and toggle for navigation drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        // Initialize NavigationView and set item selection listener
        navigationView = findViewById(R.id.nvView);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            selectDrawerItem(menuItem);
            return true;
        });

        // Load MainScreen as the default fragment if there is no saved instance state
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, new MainScreen())
                    .commit();
        }
}

    public void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;

        // Create a new fragment based on the selected item in the navigation drawer
        /**
        switch (menuItem.getItemId()) {
            case R.id.menu_start_quiz:
                fragment = new QuizFragment();
                break;
            case R.id.menu_view_results:
                fragment = new ResultsFragment();
                break;
            case R.id.menu_help:
                fragment = new HelpFragment();
                break;
            case R.id.menu_close:
                finish();
                return;
        } */

        // The above block of code was giving me an error so I temporarily rewrote it -
        // it was saying that you can't have a variable in a case declaration, not
        // sure what that's about since I've definitely done that before. ¯\_(ツ)_/¯
        if (menuItem.getItemId() == R.id.menu_start_quiz) fragment = new QuizFragment();
        else if (menuItem.getItemId() == R.id.menu_view_results) fragment = new ResultsFragment();
        else if (menuItem.getItemId() == R.id.menu_help) fragment = new HelpFragment();
        else if (menuItem.getItemId() == R.id.menu_close) {
            finish();
            return;
        }

        // Replace the fragment in the main container
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .addToBackStack("main screen")
                .commit();

        // Close the navigation drawer
        drawerLayout.closeDrawers();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // Set up the ActionBarDrawerToggle with the toolbar and DrawerLayout
        return new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void testDatabase(QuizData quizData) {
        // test quizzes
        Quiz testQuiz1 = new Quiz(getApplicationContext());
        Log.d(DEBUG_TAG, "" + testQuiz1.questions);
        Quiz testQuiz2 = new Quiz(getApplicationContext());
        Log.d(DEBUG_TAG, "" + testQuiz2.questions);

        quizData.open();

        Quiz testQuiz3 = new Quiz();
        Log.d(DEBUG_TAG, "" + testQuiz3.questions);

        // simulate different progress scenarios
        testQuiz1.getQuestion(0).setIncorrect();
        testQuiz1.getQuestion(1).setIncorrect();
        testQuiz1.getQuestion(2).setCorrect();
        testQuiz1.getQuestion(3).setCorrect();
        testQuiz1.getQuestion(4).setIncorrect();
        testQuiz1.getQuestion(5).setCorrect();

        testQuiz2.getQuestion(0).setIncorrect();
        testQuiz2.getQuestion(1).setIncorrect();
        testQuiz2.getQuestion(2).setCorrect();
        testQuiz2.getQuestion(3).setIncorrect();
        testQuiz2.getQuestion(4).setIncorrect();
        testQuiz2.getQuestion(5).setCorrect();

        testQuiz3.getQuestion(0).setCorrect();
        testQuiz3.getQuestion(1).setCorrect();
        testQuiz3.getQuestion(2).setIncorrect();
        testQuiz3.getQuestion(3).setCorrect();
        testQuiz3.getQuestion(4).setCorrect();
        testQuiz3.getQuestion(5).setCorrect();

        // test scores
        Score score1 = new Score(testQuiz1);
        Log.d(DEBUG_TAG, "" + score1);
        Score score2 = new Score(testQuiz2);
        Log.d(DEBUG_TAG, "" + score2);
        Score score3 = new Score(testQuiz3);
        Log.d(DEBUG_TAG, "" + score3);

        // inserting scores
        quizData.addScore(score1);
        quizData.addScore(score2);
        quizData.addScore(score3);

        // retrieving scores
        List<Score> scores = quizData.getAllScores();
        Log.d(DEBUG_TAG, "" + scores);
    }
}