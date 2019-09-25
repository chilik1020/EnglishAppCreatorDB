package com.chilik1020.creatordb.models;

public class Chapter{

    private Long _id;

    private String chapter;

    public Chapter(Long id, String chapter) {
        this._id = id;
        this.chapter = chapter;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "_id=" + _id +
                ", chapter='" + chapter + '\'' +
                '}';
    }
}
