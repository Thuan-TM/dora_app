package com.example.videoapp.object;

public class User {
    Integer id;
    String username, avatar, fullname, password, token;

    public static Integer current_id = 0;

    public User(){}

    public User(Integer id, String avatar, String fullname, String username, String password, String token) {
        this.id = id;
        this.username = username;
        this.avatar = avatar;
        this.fullname = fullname;
        this.password = password;
        this.token = token;

        this.current_id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public static Integer getCurrent_id() {
        return current_id;
    }

    public static void setCurrent_id(Integer current_id) {
        User.current_id = current_id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
