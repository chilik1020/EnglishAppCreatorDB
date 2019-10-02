package com.chilik1020.creatordb.models;

public class UserStat {
    private Long _id;

    private long numberOfAppStarts;

    private long statusRate;

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public long getNumberOfAppStarts() {
        return numberOfAppStarts;
    }

    public void setNumberOfAppStarts(long numberOfAppStarts) {
        this.numberOfAppStarts = numberOfAppStarts;
    }

    public long getStatusRate() {
        return statusRate;
    }

    public void setStatusRate(long statusRate) {
        this.statusRate = statusRate;
    }

    public UserStat(Long _id, long numberOfAppStarts, long statusRate) {
        this._id = _id;
        this.numberOfAppStarts = numberOfAppStarts;
        this.statusRate = statusRate;
    }

    @Override
    public String toString() {
        return "UserStat{" +
                "_id=" + _id +
                ", numberOfAppStarts=" + numberOfAppStarts +
                ", statusRate=" + statusRate +
                '}';
    }
}
