package edu.uga.cs.statecapitalsquiz;
import java.util.*;

public class Quiz {
    /** The list of questions used in the quiz */
    public List<Question> questions;

    /** The current question, used for restoring instance state */
    public Question currentQuestion;

    public Quiz() {
        questions = new ArrayList<>();
        List<Question> questionList = QuizData.questionMasterList;
        Collections.shuffle(questionList);
        for (int i = 0; i < 6; i++) questions.add(Question.cloneQuestion(questionList.get(i)));
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