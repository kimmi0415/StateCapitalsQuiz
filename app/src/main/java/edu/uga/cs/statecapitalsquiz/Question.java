package edu.uga.cs.statecapitalsquiz;
import android.util.Log;

import java.util.*;
import java.io.Serializable;

public class Question implements Serializable {
    /**
     * Represents the possible states of a quiz question's answer status.
     */
    public enum AnswerState {
        /** Question has not been answered yet */
        UNANSWERED,
        /** Question was answered incorrectly */
        INCORRECT,
        /** Question was answered correctly */
        CORRECT;
    }

    /** First answer choice displayed to user */
    public String answer1;
    /** Second answer choice displayed to user */
    public String answer2;
    /** Third answer choice displayed to user */
    public String answer3;
    /** The correct answer */
    public String correctAnswer;
    /** The state for this question */
    public String state;
    /** Current status of this question (unanswered/correct/incorrect) */
    public AnswerState status;

    /**
     * Creates a new question with randomized answer positions.
     *
     * @param prompt The state name to ask about
     * @param correct The correct answer
     * @param incorrect1 First incorrect option
     * @param incorrect2 Second incorrect option
     */
    public Question(String prompt, String correct, String incorrect1, String incorrect2) {
        state = prompt;
        correctAnswer = correct;
        List<String> answers = Arrays.asList(correct, incorrect1, incorrect2);
        Collections.shuffle(answers);
        answer1 = answers.get(0);
        answer2 = answers.get(1);
        answer3 = answers.get(2);
        status = AnswerState.UNANSWERED;
    }

    /**
     * Blank constructor, used in the Quiz class's parse method.
     */
    public Question() {
        //
    }

    /**
     * Creates a deep copy of an existing question with the same answers in new random positions.
     *
     * @param q The question to clone
     * @return A new Question instance with the same content but reshuffled answers
     */
    public static Question cloneQuestion(Question q) {
        HashSet<String> set = new HashSet<>(Arrays.asList(q.correctAnswer, q.answer1, q.answer2, q.answer3));
        set.remove(q.correctAnswer);
        String[] incorrectAnswers = set.toArray(new String[0]);
        return new Question(q.state, q.correctAnswer, incorrectAnswers[0], incorrectAnswers[1]);
    }

    /**
     * Gets the state name for this question.
     * @return The state name
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the state name for this question.
     * @param prompt The new state name
     */
    public void setState(String prompt) {
        this.state = prompt;
    }

    /**
     * Gets the first answer choice.
     * @return The first answer option
     */
    public String getAnswer1() {
        return answer1;
    }

    /**
     * Gets the second answer choice.
     * @return The second answer option
     */
    public String getAnswer2() {
        return answer2;
    }

    /**
     * Gets the third answer choice.
     * @return The third answer option
     */
    public String getAnswer3() {
        return answer3;
    }

    /**
     * Gets the correct capital
     * @return The correct answer
     */
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * Gets the current status of this question.
     * @return The question's answer status
     */
    public AnswerState getStatus() {
        return status;
    }

    /**
     * Sets the first answer choice.
     * @param answer1 The new first answer option
     */
    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    /**
     * Sets the second answer choice.
     * @param answer2 The new second answer option
     */
    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    /**
     * Sets the third answer choice.
     * @param answer3 The new third answer option
     */
    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    /**
     * Sets the correct capital
     * @param correctAnswer The new correct answer
     */
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    /**
     * Sets the answer status of this question.
     * @param status The new answer status
     */
    public void setStatus(AnswerState status) {
        this.status = status;
    }

    /**
     * Marks this question as correctly answered.
     */
    public void setCorrect() {
        this.status = AnswerState.CORRECT;
    }

    /**
     * Marks this question as incorrectly answered.
     */
    public void setIncorrect() {
        this.status = AnswerState.INCORRECT;
    }

    /**
     * Resets this question's status to unanswered.
     */
    public void setUnanswered() {
        this.status = AnswerState.UNANSWERED;
    }

    /**
     * Checks if this question has been answered.
     * @return true if the question has been answered, false otherwise
     */
    public boolean isAnswered() {
        return status != AnswerState.UNANSWERED;
    }

    /**
     * Checks if this question was answered correctly.
     * @return true if the question was answered correctly, false otherwise
     */
    public boolean isCorrect() {
        return status == AnswerState.CORRECT;
    }

    /**
     * Returns a string representation of this question, including the state,
     * all answer choices, the correct answer, and the current status.
     *
     * @return A string representation of the question
     */
    @Override
    public String toString() {
        return "What is the capital of " + state + "? " + answer1 + "|" + answer2 + "|" + answer3
                + ", correct answer is " + correctAnswer + ", status: " + status + "\n";
    }
}