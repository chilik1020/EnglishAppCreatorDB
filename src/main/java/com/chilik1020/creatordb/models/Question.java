package com.chilik1020.creatordb.models;


public class Question{
//    private Long chapterId;

    private Long lessonId;

    private Long testId;

    private String question;

    private String answer0;
    private String answer1;
    private String answer2;
    private String answer3;

    private Long rightAnswer;

    public Question(Long lessonId, Long testId, String question, String answer0, String answer1, String answer2, String answer3, Long rightAnswer) {
        this.lessonId = lessonId;
        this.testId = testId;
        this.question = question;
        this.answer0 = answer0;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.rightAnswer = rightAnswer;
    }
    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer0() {
        return answer0;
    }

    public void setAnswer0(String answer0) {
        this.answer0 = answer0;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public Long getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(Long rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "lessonId=" + lessonId +
                ", testId=" + testId +
                ", question='" + question + '\'' +
                ", answer0='" + answer0 + '\'' +
                ", answer1='" + answer1 + '\'' +
                ", answer2='" + answer2 + '\'' +
                ", answer3='" + answer3 + '\'' +
                ", rightAnswer=" + rightAnswer +
                '}';
    }
}
