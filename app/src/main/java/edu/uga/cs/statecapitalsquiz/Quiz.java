package edu.uga.cs.statecapitalsquiz;
import android.content.Context;

import java.util.*;

public class Quiz {
    /** The list of questions used in the quiz */
    public List<Question> questions;

    /** The current question, used for restoring instance state */
    public Question currentQuestion;

    /** Field to track the current score */
    private int currentScore;

    public Quiz() {
        questions = new ArrayList<>();
        List<Question> questionList = QuizData.questionMasterList;
        Collections.shuffle(questionList);
        for (int i = 0; i < 6; i++) questions.add(Question.cloneQuestion(questionList.get(i)));
        currentScore = 0; // Initialize score
    }

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

    // Method to reset the quiz state
    public void reset() {
        currentScore = 0; // Reset the score
        for (Question question : questions) {
            question.setUnanswered(); // Reset each question's answer state
        }
    }

    // Method to update the score based on the current status of each question
    public void updateScore() {
        currentScore = 0; // Reset score and recalculate
        for (Question question : questions) {
            if (question.isCorrect()) {
                currentScore++;
            }
        }
    }

    // Method to get the current score
    public int getCurrentScore() {return currentScore;
    }
    public int getNumberOfQuestions() {
        return questions.size();
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public Question getQuestion(int questionNumber) {
        return questions.get(questionNumber);
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(Question q) {
        currentQuestion = q;
    }
}