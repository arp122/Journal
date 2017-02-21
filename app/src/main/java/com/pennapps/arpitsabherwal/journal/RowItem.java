package com.pennapps.arpitsabherwal.journal;

/**
 * Created by arpitsabherwal on 24/01/16.
 */


public class RowItem {
    private String content;
    private int type;

    public RowItem(String content, int type) {
        this.content = content;
        this.type = type;

    }
    public String getContent() {
        return content;
    }public int getType() {
        return type;
    }
    public void setContent(String content) {
        this.content= content;
    }
    public void setType(int type) {
        this.type= type;
    }

    @Override
    public String toString() {
        return "";
    }
}

