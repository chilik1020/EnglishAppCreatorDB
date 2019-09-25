package com.chilik1020.creatordb.models;

public class LessonTest {
    private Long _id;

    private Long chapterId;

    private String topic;

    public LessonTest(Long _id, Long chapterId, String topic) {
        this._id = _id;
        this.chapterId = chapterId;
        this.topic = topic;
    }

    public Long getId() {
        return _id;
    }

    public void setId(Long _id) {
        this._id = _id;
    }

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "LessonTest{" +
                "_id=" + _id +
                ", chapterId=" + chapterId +
                ", topic='" + topic + '\'' +
                '}';
    }
}
