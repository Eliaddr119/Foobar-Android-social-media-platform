package com.example.foobarpart2.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.foobarpart2.db.entity.User;
import com.example.foobarpart2.repository.UsersRepository;

import java.util.List;

public class UserViewModel extends ViewModel {
    private UsersRepository repository;
    private LiveData<List<User>> users;
    public LiveData<String> errorLiveData;

    public UserViewModel(){
        repository = new UsersRepository();
        users = repository.getAll();
    }
    public LiveData<List<User>> get() {return users;}
    public void add(User user){ repository.add(user);}
    public void delete(User user){
        repository.delete(user);
    }
    public void relode(){
        repository.reload();
    }
}
