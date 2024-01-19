package com.example.videoapp.object;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Video implements Serializable {
    private String id, publishedAt, title, description, thumbnails, iframe;
    private int is_love;

    public Video(){}
    public Video(JSONObject o) throws JSONException {
        id = o.getString("id");
        publishedAt = o.getString("publishedAt");
        title = o.getString("title");
        description = o.getString("description");
        thumbnails = o.getString("thumbnails");
        iframe = o.getString("iframe");
        is_love = Integer.parseInt(o.getString("is_love"));
    }
    public Video(String id, String publishedAt, String title, String description, String thumbnails, String iframe,int  is_love) {
        this.id = id;
        this.publishedAt = publishedAt;
        this.title = title;
        this.description = description;
        this.thumbnails = thumbnails;
        this.iframe = iframe;
        this.is_love = is_love;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(String thumbnails) {
        this.thumbnails = thumbnails;
    }
    public String getIframe() {
        return iframe;
    }

    public void setIframe(String iframe) {
        this.iframe = iframe;
    }

    public int getIs_love() {
        return is_love;
    }

    public void setIs_love(int is_love) {
        this.is_love = is_love;
    }
}
