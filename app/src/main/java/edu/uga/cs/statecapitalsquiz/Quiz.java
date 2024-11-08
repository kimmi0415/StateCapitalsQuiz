package edu.uga.cs.statecapitalsquiz;
import android.content.Context;

import java.util.*;

public class Quiz {
    /** The list of questions used in the quiz */
    public List<Question> questions;

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
     * Gets the current quiz as a parseable string for saving/restoring
     * the instance state efficiently.
     * @return the converted quiz
     */
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(currentScore);
        for (Question q : questions) {
            str.append(":");
            str.append(q.state).append("&");
            str.append(q.answer1).append("&");
            str.append(q.answer2).append("&");
            str.append(q.answer3).append("&");
            str.append(q.correctAnswer).append("&");
            if (q.status == Question.AnswerState.INCORRECT) str.append("i");
            else if (q.status == Question.AnswerState.CORRECT) str.append("c");
            else str.append("u");
        }
        return str.toString();
    }

    /**
     * Parses a given string, intended to be the output from another
     * Quiz's toString() call, into a new quiz object.
     * @param s the info string
     */
    public Quiz(String s) {
        questions = new ArrayList<>();
        String[] tokens = s.split(":");
        for (String token : tokens) {
            if (token.length() == 1) currentScore = Integer.parseInt(token);
            else {
                String[] questionData = token.split("&");
                String state = questionData[0];
                String answer1 = questionData[1];
                String answer2 = questionData[2];
                String answer3 = questionData[3];
                String correct = questionData[4];
                Question q = new Question();
                q.state = state;
                q.answer1 = answer1;
                q.answer2 = answer2;
                q.answer3 = answer3;
                q.correctAnswer = correct;
                if (questionData[5].equals("i")) q.setIncorrect();
                else if (questionData[5].equals("c")) q.setCorrect();
                else q.setUnanswered();
                questions.add(q);
            }
        }
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
}