package com.chilik1020.creatordb.models;

public class Lesson {

    private int _id;

    private int chapterId;

    private String topic;

    private String grammar;

    public Lesson(int _id, int chapterId, String topic, String grammar) {
        this._id = _id;
        this.chapterId = chapterId;
        this.topic = topic;
        this.grammar = grammar;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }


    public int getId() {
        return _id;
    }

    public void setId(int _id) {
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
        return "Lesson{" +
                "_id=" + _id +
                ", chapterId=" + chapterId +
                ", topic='" + topic + '\'' +
                ", grammar='" + grammar + '\'' +
                '}';
    }
}
