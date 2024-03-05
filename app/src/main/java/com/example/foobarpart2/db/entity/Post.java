package com.example.foobarpart2.db.entity;

import android.net.Uri;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.foobarpart2.ui.viewmodels.CommentStorage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Post {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String author;
    private String content;
    private Date postTime;
    private int likes = 0;
    private int commentsCount = 0;
    private boolean isLiked = false;
    private Uri picUri;
    private Uri profile;
    private List<Comment> comments = new ArrayList<>();

    public Post(String author, String content, Uri profile, Uri picUri, Date postTime) {
        this.author = author;
        this.content = content;
        this.profile = profile;
        this.picUri = picUri;
        this.postTime = postTime;
    }


    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public void setProfile(Uri profile) {
        this.profile = profile;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void toggleLikeStatus() {
        isLiked = !isLiked; // Toggle the like status
        if (isLiked) {
            likes++; // If liked, increment the like count
        } else {
            likes--; // If unliked, decrement the like count
        }
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

    public Uri getProfile() {
        return profile;
    }

    public Uri getPicUri() {
        return picUri;
    }

    public void setPicUri(Uri picUri) {
        this.picUri = picUri;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    // Method to add a comment to the post
    public void addComment(Comment comment) {
        this.comments.add(comment);
        this.commentsCount++;
        CommentStorage.commentsMap.put(id, comments);
    }

    public void removeComment(Comment comment) {
        this.comments.remove(comment);
        this.commentsCount--;
    }

    // Getter for the comments list
    public List<Comment> getComments() {
        return comments;
    }

}
