package com.example.foobarpart2.entities;

public class Comment {
    private int postId;
    private String author;
    private String content;

    private long timestamp;

    public Comment(int postId, String author, String content, long timestamp) {
        this.postId = postId;
        this.author = author;
        this.content = content;
        this.timestamp = timestamp;
    }

    // Getters and Setters
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
