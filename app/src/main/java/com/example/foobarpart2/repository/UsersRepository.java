package com.example.foobarpart2.repository;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.foobarpart2.MyApplication;
import com.example.foobarpart2.db.dao.UserDao;
import com.example.foobarpart2.db.database.AppDB;
import com.example.foobarpart2.db.entity.User;
import com.example.foobarpart2.network.api.UserAPI;

import java.util.LinkedList;
import java.util.List;

public class UsersRepository {
    private UserDao dao;
    private UserListData userListData;
    private UserAPI api;

    public UsersRepository() {
        AppDB db = Room.databaseBuilder(MyApplication.context, AppDB.class, "userDB")
                .allowMainThreadQueries().build();
        this.dao = db.userDao();
        api = new UserAPI(userListData, dao);
    }

    public void add(User user) {
        api.add(user);
    }

    public void delete(User user) {
        api.delete(user);
        dao.delete(user);
    }

    public void getUser(User user) {

    }

    public boolean authenticate(String username, String password) {
         api.authenticate(username, password);
         return true;
    }

    public void reload() {

    }

    class UserListData extends MutableLiveData<List<User>> {
        public UserListData() {
            super();
            setValue(new LinkedList<User>());
        }

        @Override
        protected void onActive() {
            super.onActive();
            //new Thread(() -> userListData.postValue(dao.index())).start();
        }
    }


    /*public void reload() {
        new GetUsersTask(userListData, dao).execute();
    }

    public class GetUsersTask extends AsyncTask<Void, Void, Void> {
        private MutableLiveData<List<User>> userListData;
        private UserDao dao;

        public GetUsersTask(MutableLiveData<List<User>> userListData, UserDao dao) {
            this.userListData = userListData;
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... x) {
            // connect to web-service
            // retrieve posts
            // convert json response to objects
            // update objects in LiveData
            return null;
        }
    }*/
}
