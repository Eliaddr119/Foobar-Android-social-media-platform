package com.example.foobarpart2.repository.tasks;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.example.foobarpart2.db.dao.PostDao;
import com.example.foobarpart2.db.entity.Post;

import java.util.List;

public class GetPostsTask extends AsyncTask<Void, Void, Void> {
    private MutableLiveData<List<Post>> postListData;
    private PostDao dao;

    public GetPostsTask(MutableLiveData<List<Post>> postListData, PostDao dao) {
        this.postListData = postListData;
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
}