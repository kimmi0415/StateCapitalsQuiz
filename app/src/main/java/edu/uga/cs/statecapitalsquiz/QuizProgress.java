package edu.uga.cs.statecapitalsquiz;

public class QuizProgress {
    private int questionIndex;
    private int score;

    public QuizProgress(int questionIndex, int score) {
        this.questionIndex = questionIndex;
        this.score = score;
    }

    public int getQuestionIndex() {
        return questionIndex;
    }

    public int getScore() {
        return score;
    }
}