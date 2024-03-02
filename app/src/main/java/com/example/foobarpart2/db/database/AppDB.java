package com.example.foobarpart2.db.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.foobarpart2.db.dao.TokenDao;
import com.example.foobarpart2.db.entity.Token;
import com.example.foobarpart2.db.entity.User;
import com.example.foobarpart2.db.dao.UserDao;

@Database(entities = {User.class, Token.class}, version = 2)
public abstract class AppDB extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract TokenDao tokenDao();
}
