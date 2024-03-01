package com.example.foobarpart2.models;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.foobarpart2.entities.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDB extends RoomDatabase {
    public abstract UserDao userDao();
}
