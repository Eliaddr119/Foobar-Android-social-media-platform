package com.example.foobarpart2.db.entity;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey @NonNull
    private final String username;
    private final String password;
    private final String displayName;
    @ColumnInfo(name = "is_logged_in")
    private boolean isLoggedIn;

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    private final Uri profileImage;

    public User(@NonNull String username, String password, String displayName, Uri profileImage) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.profileImage = profileImage;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Uri getProfileImage() {
        return profileImage;
    }
}
