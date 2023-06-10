package com.example.chatappthree.modelclass;

public class UserModel {
    String name,email,password,imageUrl,id;

    public UserModel() {
    }

    public UserModel(String name, String email, String password, String imageUrl, String id) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.imageUrl = imageUrl;
        this.id = id;
    }

    public UserModel(String name, String email, String imageUrl) {
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}