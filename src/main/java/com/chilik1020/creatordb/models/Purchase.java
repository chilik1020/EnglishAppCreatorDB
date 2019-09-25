package com.chilik1020.creatordb.models;

public class Purchase {
    private Long _id;

    private String name;

    private long status;

    public Purchase(Long _id, String name, long status) {
        this._id = _id;
        this.name = name;
        this.status = status;
    }

    public Long getId() {
        return _id;
    }

    public void setId(Long _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
