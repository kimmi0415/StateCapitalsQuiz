package edu.uga.cs.statecapitalsquiz;
import android.content.Context;

import java.util.*;

public class Quiz {
    /** The list of questions used in the quiz */
    public List<Question> questions;

    /** The currently active question, used for restoring instance state */
    public Question currentQuestion;

    /** Field to track the current score */
    private int currentScore;

    /**
     * Creates a new quiz by randomly selecting 6 questions from the master list.
     * Requires that QuizData.questionMasterList has already been initialized.
     */
    public Quiz() {
        questions = new ArrayList<>();
        List<Question> questionList = QuizData.questionMasterList;
        Collections.shuffle(questionList);
        for (int i = 0; i < 6; i++) questions.add(Question.cloneQuestion(questionList.get(i)));
        currentScore = 0; // Initialize score
    }

    /**
     * Creates a new quiz using a Context to initialize the question database if needed.
     *
     * @param context The Android Context needed for database access
     */
    public Quiz(Context context) {
        // will need to be wrapped in an async task if used
        if (QuizData.questionMasterList == null) {
            QuizData qd = new QuizData(context);
            qd.open();
            qd.close();
        }
        questions = new ArrayList<>();
        List<Question> questionList = QuizData.questionMasterList;
        Collections.shuffle(questionList);
        for (int i = 0; i < 6; i++) questions.add(Question.cloneQuestion(questionList.get(i)));
        currentScore = 0; // Initialize score
    }

    /**
     * Resets the quiz to its initial state.
     */
    public void reset() {
        currentScore = 0; // Reset the score
        for (Question question : questions) {
            question.setUnanswered(); // Reset each question's answer state
        }
    }

    /**
     * Updates the current score based on the status of each question.
     */
    public void updateScore() {
        currentScore = 0; // Reset score and recalculate
        for (Question question : questions) {
            if (question.isCorrect()) {
                currentScore++;
            }
        }
    }

    /**
     * Gets the current score.
     * @return The current score
     */
    public int getCurrentScore() {
        return currentScore;
    }

    /**
     * Gets the total number of questions in this quiz.
     * @return The number of questions
     */
    public int getNumberOfQuestions() {
        return questions.size();
    }

    /**
     * Gets the list of all questions in this quiz.
     * @return List of Question objects
     */
    public List<Question> getQuestions() {
        return questions;
    }

    /**
     * Gets a specific question from the quiz by its index.
     *
     * @param questionNumber The index of the question to retrieve
     * @return The requested Question object
     */
    public Question getQuestion(int questionNumber) {
        return questions.get(questionNumber);
    }

    /**
     * Gets the current question.
     * @return The current Question object
     */
    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    /**
     * Sets the current question.
     * @param q The Question to set as current
     */
    public void setCurrentQuestion(Question q) {
        currentQuestion = q;
    }
}