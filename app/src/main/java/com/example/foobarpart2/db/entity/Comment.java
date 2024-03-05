package com.example.foobarpart2.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Comment {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int postId;
    private String author;
    private String content;
    private Date timestamp;

    public Comment(int postId, String author, String content, Date timestamp) {
        this.postId = postId;
        this.author = author;
        this.content = content;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }
    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
