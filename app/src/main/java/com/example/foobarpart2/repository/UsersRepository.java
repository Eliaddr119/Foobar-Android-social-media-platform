package com.example.foobarpart2.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.foobarpart2.MyApplication;
import com.example.foobarpart2.db.dao.UserDao;
import com.example.foobarpart2.db.database.AppDB;
import com.example.foobarpart2.db.entity.User;
import com.example.foobarpart2.models.LoggedInUser;
import com.example.foobarpart2.network.api.UserAPI;

public class UsersRepository {
    private UserDao dao;
    private MutableLiveData<Boolean> signUpResult = new MutableLiveData<>();
    private MutableLiveData<Boolean> authenticateResult = new MutableLiveData<>();
    private MutableLiveData<User> userData = new MutableLiveData<>();


    private UserAPI api;

    public UsersRepository() {
        AppDB db = Room.databaseBuilder(MyApplication.context, AppDB.class, "userDB")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        this.dao = db.userDao();
        api = new UserAPI(signUpResult, authenticateResult, userData, dao);
    }

    public void add(User user) {
        api.add(user);
    }

    public void delete(User user) {
        api.delete(user);
        dao.delete(user);
    }
    public LiveData<User> get(String username){
        api.getUser(username);
        return userData;
    }


    public void authenticate(String username, String password) {
        api.authenticate(username, password);
    }

    public LiveData<Boolean> getSignUpResult() {
        return this.signUpResult;
    }

    public LiveData<Boolean> getAuthenticateResult() {
        return this.authenticateResult;
    }

    public LiveData<User> getUserData() {
        return this.userData;
    }


    public void reload() {

    }

    public void logOutCurrUser() {
        LoggedInUser.getInstance().setUser(null);
    }
}



