package com.example.foobarpart2.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.foobarpart2.MyApplication;
import com.example.foobarpart2.db.dao.PostDao;
import com.example.foobarpart2.db.database.AppDB;
import com.example.foobarpart2.db.entity.Post;
import com.example.foobarpart2.network.api.PostAPI;
import com.example.foobarpart2.repository.tasks.GetPostsTask;

import java.util.LinkedList;
import java.util.List;


public class PostsRepository {
    private PostDao dao;
    private PostListData postListData;
    private PostAPI api;

    public PostsRepository() {
        AppDB db = Room.databaseBuilder(MyApplication.context, AppDB.class, "postDB")
                .allowMainThreadQueries()
                .build();
        this.dao = db.postDao();
        api = new PostAPI(postListData, dao);
    }

    class PostListData extends MutableLiveData<List<Post>> {

        public PostListData() {
            super();
            setValue(new LinkedList<Post>());

        }

        @Override
        protected void onActive() {
            super.onActive();

            new Thread(() -> {
                postListData.postValue(dao.get());
            }).start();

        }

    }

    public LiveData<List<Post>> getAll() {
        return postListData;

    }

    public void reload() {
        new GetPostsTask(postListData, dao,api).execute();
    }
}




