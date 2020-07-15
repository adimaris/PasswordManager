package com.alexanderdimaris.passwordmanager.model;

import java.io.Serializable;

public class PassObj implements Serializable {

    private int id;
    private String title;
    private String username;
    private String password;
    private String comments;

    public PassObj (int id, String title, String username, String password, String comments) {
        this.id = id;
        this.title = title;
        this.username = username;
        this.password = password;
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String prettyPrint() {
        return "Title=" + this.getTitle() + " Username=" + this.getUsername() + " Password=" + this.getPassword() + " Comments=" + this.getComments();
    }

}
