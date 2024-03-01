package com.example.foobarpart2.entities;

import android.net.Uri;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey()
    private final String username;
    private final String password;
    private final String displayName;
    private final Uri profileImage;

    public User(String username, String password, String displayName, Uri profileImage) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.profileImage = profileImage;
    }

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
