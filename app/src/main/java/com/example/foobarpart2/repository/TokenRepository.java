package com.example.foobarpart2.repository;

import androidx.room.Room;

import com.example.foobarpart2.MyApplication;
import com.example.foobarpart2.db.dao.TokenDao;
import com.example.foobarpart2.db.database.AppDB;
import com.example.foobarpart2.db.entity.Token;

public class TokenRepository {
    private TokenDao dao;

    public TokenRepository() {
        AppDB db = Room.databaseBuilder(MyApplication.context, AppDB.class, "tokenDB")
                .allowMainThreadQueries().build();
        this.dao = db.tokenDao();
    }

    public void add(Token token){
        dao.insertToken(token);
    }

    public Token get(){
        return dao.getToken();
    }
}
