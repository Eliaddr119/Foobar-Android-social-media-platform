package com.example.foobarpart2.db.entity;

import android.net.Uri;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.foobarpart2.R;
import com.example.foobarpart2.ui.viewmodels.CommentStorage;

import java.util.ArrayList;
import java.util.List;
@Entity
public class Post {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String author;
    private String content;

    private String postTime;
    private int likes = 0;
    private int commentsCount = 0;
    private boolean isLiked = false;
    private int pic = -1;

    private Uri picUri;
    private Uri profile;
    private List<Comment> comments = new ArrayList<>();

    public Post(){
        this.pic = R.drawable.pic1;
    }
    public Post(String author, String content, int pic , Uri profile){
        this.author = author;
        this.content = content;
        this.pic = pic;
        this.profile = profile;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public Post(String author, String content, int likes, int pic , Uri profile){
        this.author = author;
        this.content = content;
        this.pic = pic;
        this.profile = profile;
        this.likes = likes;
    }
    public Post(String author, String content, Uri pic , Uri profile){
        this.author = author;
        this.content = content;
        this.picUri = pic;
        this.profile = profile;
    }

    public Post(String author, String content,Uri profile){
        this.author = author;
        this.content = content;
        this.profile = profile;
        this.pic = 0;
    }

    public Post(String author, String content,String postTime, int likes, Uri picUri, Uri profile ){
        this.author = author;
        this.content = content;
        this.postTime = postTime;
        this.likes = likes;
        this.picUri = picUri;
        this.profile = profile;
    }

    public Post(String author, String content,String postTime, int likes, int pic, Uri profile ){
        this.author = author;
        this.content = content;
        this.postTime = postTime;
        this.likes = likes;
        this.pic = pic;
        this.profile = profile;
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

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
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

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
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
        CommentStorage.commentsMap.put(id,comments);
    }
    public void removeComment(Comment comment){
        this.comments.remove(comment);
        this.commentsCount--;
    }
    // Getter for the comments list
    public List<Comment> getComments() {
        return comments;
    }

}
