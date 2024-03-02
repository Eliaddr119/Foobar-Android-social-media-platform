package com.example.foobarpart2.db.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.foobarpart2.db.dao.PostDao;
import com.example.foobarpart2.db.dao.TokenDao;
import com.example.foobarpart2.db.entity.Post;
import com.example.foobarpart2.db.entity.Token;
import com.example.foobarpart2.db.entity.User;
import com.example.foobarpart2.db.dao.UserDao;

@Database(entities = {User.class, Token.class, Post.class}, version = 3)
public abstract class AppDB extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract TokenDao tokenDao();
    public abstract PostDao postDao();
}
