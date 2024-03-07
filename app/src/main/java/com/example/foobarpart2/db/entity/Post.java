package com.example.foobarpart2.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Post {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String username;
    private String displayName;
    private String profilePic;
    @NonNull
    private Date date;
    @NonNull
    private String content;
    private int numlikes = 0;

    private int commentsCount = 0;


    private boolean isLiked = false;
    private String image;

    private List<Comment> comments = new ArrayList<>();

    public Post(@NonNull String username, String displayName, @NonNull Date date, @NonNull String content, String profilePic, String image) {
        this.username = username;
        this.displayName = displayName;
        this.profilePic = profilePic;
        this.date = date;
        this.content = content;
        this.image = image;

    }


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getUsername() {
        return this.username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfile(String profilePic) {
        this.profilePic = profilePic;
    }

    @NonNull
    public Date getDate() {
        return this.date;
    }

    public void setDate(@NonNull Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNumlikes() {
        return this.numlikes;
    }

    public void setNumlikes(int numlikes) {
        this.numlikes = numlikes;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
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
            numlikes++; // If liked, increment the like count
        } else {
            numlikes--; // If unliked, decrement the like count
        }
    }

    // Method to add a comment to the post
    public void addComment(Comment comment) {
        this.comments.add(comment);
        this.commentsCount++;
        // CommentStorage.commentsMap.put(id, comments);
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
