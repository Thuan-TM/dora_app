package com.example.videoapp.object;

import org.json.JSONException;
import org.json.JSONObject;

public class Video {
    private String id, publishedAt, title, description, thumbnails;

    public Video(){}
    public Video(JSONObject o) throws JSONException {
        id = o.getString("id");
        publishedAt = o.getString("publishedAt");
        title = o.getString("title");
        description = o.getString("description");
        thumbnails = o.getString("thumbnails");
    }
    public Video(String id, String publishedAt, String title, String description, String thumbnails) {
        this.id = id;
        this.publishedAt = publishedAt;
        this.title = title;
        this.description = description;
        this.thumbnails = thumbnails;
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
}