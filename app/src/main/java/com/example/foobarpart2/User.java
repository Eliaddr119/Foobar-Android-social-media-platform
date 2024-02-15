package com.example.foobarpart2;

import android.net.Uri;

public class User {
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

    public User(String username, String displayName, Uri profileImage) {
        this.username = username;
        this.displayName = displayName;
        this.profileImage = profileImage;
        password = null;
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
