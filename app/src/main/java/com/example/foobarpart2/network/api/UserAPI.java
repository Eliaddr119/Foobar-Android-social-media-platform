package com.example.foobarpart2.network.api;

import androidx.lifecycle.MutableLiveData;

import com.example.foobarpart2.MyApplication;
import com.example.foobarpart2.R;
import com.example.foobarpart2.db.dao.UserDao;
import com.example.foobarpart2.db.entity.Token;
import com.example.foobarpart2.db.entity.User;
import com.example.foobarpart2.repository.TokenRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAPI {
    private MutableLiveData<List<User>> userListData;
    private UserDao dao;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    public UserAPI(MutableLiveData<List<User>> userListData, UserDao dao) {
        this.userListData = userListData;
        this.dao = dao;


        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void add(User user) {
        Call<Void> call = webServiceAPI.createUser(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                new Thread(() -> {
                    dao.insert(user);
                    userListData.postValue(dao.index());
                }).start();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void delete(User user) {
        Call<Void> call = webServiceAPI.deleteUser(user.getUsername());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void authenticate(String username, String password) {
        Call<Token> call = webServiceAPI.authenticateUser(username, password);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()) {
                    TokenRepository tokenRepository = new TokenRepository();
                    tokenRepository.add(response.body());
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {

            }
        });
    }

    /*public void get() {
        Call<List<User>> call = webServiceAPI.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                new Thread(() -> {
                    dao.clear();
                    dao.insertList(response.body());
                    userListData.userValue(dao.get());
                }).start();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
            }
        });
    }*/
}
