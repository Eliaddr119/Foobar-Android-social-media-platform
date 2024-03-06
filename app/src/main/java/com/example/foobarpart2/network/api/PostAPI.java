package com.example.foobarpart2.network.api;

import androidx.lifecycle.MutableLiveData;

import com.example.foobarpart2.MyApplication;
import com.example.foobarpart2.R;
import com.example.foobarpart2.db.dao.PostDao;
import com.example.foobarpart2.db.entity.Post;
import com.example.foobarpart2.db.entity.User;
import com.example.foobarpart2.models.LoggedInUser;
import com.example.foobarpart2.repository.TokenRepository;
import com.example.foobarpart2.repository.UsersRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostAPI {
    private MutableLiveData<List<Post>> postListData;
    private PostDao dao;
    private Retrofit retrofit;
    private WebServiceAPI webServiceAPI;
    private TokenRepository tokenRepository;
    private UsersRepository usersRepository;

    public PostAPI(MutableLiveData<List<Post>> postListData, PostDao dao) {
        this.postListData = postListData;
        this.dao = dao;

        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
        tokenRepository = new TokenRepository();
        usersRepository = new UsersRepository();
    }
    public void get() {


        Call<List<Post>> call = webServiceAPI.getPosts(tokenRepository.get());
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                new Thread(() -> {
                    dao.clear();
                    dao.insert(response.body().toArray(new Post[0]));
                    postListData.postValue(dao.index());
                }).start();
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
            }
        });
    }

    public void add(Post post) {
        User user = LoggedInUser.getInstance().getUser();
        Call<Void> call = webServiceAPI.createPost(user.getUsername(),tokenRepository.get(),post);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void delete(Post post) {
        User user = LoggedInUser.getInstance().getUser();
        Call<Void> call = webServiceAPI.deletePost(user.getUsername(),post.getId(),tokenRepository.get());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
