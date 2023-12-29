package com.example.videoapp.object;

public class Comment {
    Integer id, uid;
    String content, created;
    public Comment(){}
    public Comment(Integer id, Integer uid, String content, String created) {
        this.id = id;
        this.uid = uid;
        this.content = content;
        this.created = created;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
