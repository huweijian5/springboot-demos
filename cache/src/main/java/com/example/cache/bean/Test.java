package com.example.cache.bean;

import java.io.Serializable;

public class Test implements Serializable{
    private int id;
    private String content;

    public Test(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public Test() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
