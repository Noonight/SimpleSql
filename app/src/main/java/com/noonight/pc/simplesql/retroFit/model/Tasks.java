package com.noonight.pc.simplesql.retroFit.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by PC on 5/29/2017.
 */

public class Tasks {

    @SerializedName("_id")
    private String _id;

    @SerializedName("title")
    private String title;

    @SerializedName("data")
    private String data;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
