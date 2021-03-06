package com.chilik1020.creatordb.models;


public class Test{

    private Long _id;

    private Long chapterId;

    private Long lessonId;

    private String topic;

    private long price;

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

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public Test(Long _id, Long chapterId, Long lessonId, String topic, long price) {
        this._id = _id;
        this.chapterId = chapterId;
        this.lessonId = lessonId;
        this.topic = topic;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Test{" +
                "_id=" + _id +
                ", chapterId=" + chapterId +
                ", lessonId=" + lessonId +
                ", topic='" + topic + '\'' +
                ", price=" + price +
                '}';
    }
}
