package com.chilik1020.creatordb.models;


public class Test{

    private int _id;

    private int chapterId;

    private int lessonId;

    public Test(int id, int chapterId, int lessonId) {
        this._id = id;
        this.chapterId = chapterId;
        this.lessonId = lessonId;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }
}
