package edu.uga.cs.statecapitalsquiz;
import android.util.Log;

import java.util.*;
import java.io.Serializable;

public class Question implements Serializable {
    /** enum to hold possible states of the question */
    public enum AnswerState {
        UNANSWERED,
        INCORRECT,
        CORRECT;
    }

    public String answer1;
    public String answer2;
    public String answer3;
    public String correctAnswer;
    public String state;
    public AnswerState status;

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

    public static Question cloneQuestion(Question q) {
        HashSet<String> set = new HashSet<>(Arrays.asList(q.correctAnswer, q.answer1, q.answer2, q.answer3));
        set.remove(q.correctAnswer);
        String[] incorrectAnswers = set.toArray(new String[0]);
        return new Question(q.state, q.correctAnswer, incorrectAnswers[0], incorrectAnswers[1]);
    }

    public String getState() {
        return state;
    }

    public void setState(String prompt) {
        this.state = prompt;
    }

    public String getAnswer1() {
        return answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public AnswerState getStatus() {
        return status;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setStatus(AnswerState status) {
        this.status = status;
    }

    public void setCorrect() {
        this.status = AnswerState.CORRECT;
    }

    public void setIncorrect() {
        this.status = AnswerState.INCORRECT;
    }

    public void setUnanswered() {
        this.status = AnswerState.UNANSWERED;
    }

    public boolean isAnswered() {
        return status != AnswerState.UNANSWERED;
    }

    public boolean isCorrect() {
        return status == AnswerState.CORRECT;
    }

    public String toString() {
        return "What is the capital of " + state + "? " + answer1 + "|" + answer2 + "|" + answer3
                + ", correct answer is " + correctAnswer + ", status: " + status + "\n";
    }
}
