package com.example.videoapp.object;

import org.json.JSONException;
import org.json.JSONObject;

public class Comment {
    Integer id, uid;
    String content, created, userName, avatarUser;
    public Comment(){}
    public Comment(JSONObject o) throws JSONException {
        id = Integer.valueOf(o.getString("id"));
        uid = Integer.valueOf(o.getString("uid"));
        content = o.getString("content");
        created = o.getString("created");
        userName = o.getString("username");
        avatarUser = o.getString("avatarUser");
    }
    public Comment(Integer id, Integer uid, String content, String created) {
        this.id = id;
        this.uid = uid;
        this.content = content;
        this.created = created;
    }

    public String getAvatarUser() {
        return avatarUser;
    }

    public void setAvatarUser(String avatarUser) {
        this.avatarUser = avatarUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
