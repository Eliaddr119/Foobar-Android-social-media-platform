package com.example.foobarpart2.entities;

import android.net.Uri;

import com.example.foobarpart2.R;

public class Post {
    private int id;
    private String author;
    private String content;
    private int likes;
    private int pic;
    private Uri profile;
    public Post(){
        this.pic = R.drawable.pic1;
    }
    public Post(String author, String content, int pic , Uri profile){
        this.author = author;
        this.content = content;
        this.pic = pic;
        this.profile = profile;
    }

    public Post(String author, String content,Uri profile){
        this.author = author;
        this.content = content;
        this.profile = profile;
        this.pic = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public Uri getProfile() {
        return profile;
    }
}
