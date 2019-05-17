package com.chilik1020.creatordb.models;

public class Chapter{

    private int _id;

    private String chapter;

    public Chapter(int id, String chapter) {
        this._id = id;
        this.chapter = chapter;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }
}
