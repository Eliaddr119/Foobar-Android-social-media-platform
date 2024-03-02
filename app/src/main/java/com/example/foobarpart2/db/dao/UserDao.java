package com.example.foobarpart2.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.foobarpart2.db.entity.User;

import java.util.List;


@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> index();

    @Query("SELECT * FROM user WHERE username = :username")
    User get(String username);

    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User... users);

    @Query("UPDATE user SET is_logged_in = 1 WHERE username = :username")
    void logInUser(String username);

    @Query("UPDATE user SET is_logged_in = 0 WHERE username = :username")
    void logOutUser(String username);

    @Query("SELECT * FROM user WHERE is_logged_in = 1 LIMIT 1")
    User getLoggedInUser();
}
