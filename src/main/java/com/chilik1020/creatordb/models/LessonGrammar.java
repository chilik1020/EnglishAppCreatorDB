package com.chilik1020.creatordb.models;

public class LessonGrammar {

    private Long _id;

    private Long chapterId;

    private String topic;

    private String grammar;

    public LessonGrammar(Long _id, Long chapterId, String topic, String grammar) {
        this._id = _id;
        this.chapterId = chapterId;
        this.topic = topic;
        this.grammar = grammar;
    }

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }


    public Long getId() {
        return _id;
    }

    public void setId(Long _id) {
        this._id = _id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getGrammar() {
        return grammar;
    }

    public void setGrammar(String grammar) {
        this.grammar = grammar;
    }

    @Override
    public String toString() {
        return "LessonGrammar{" +
                "_id=" + _id +
                ", chapterId=" + chapterId +
                ", topic='" + topic + '\'' +
                ", grammar='" + grammar + '\'' +
                '}';
    }
}
