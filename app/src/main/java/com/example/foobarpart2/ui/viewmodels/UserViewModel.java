package com.example.foobarpart2.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.foobarpart2.db.entity.Token;
import com.example.foobarpart2.db.entity.User;
import com.example.foobarpart2.repository.UsersRepository;

public class UserViewModel extends ViewModel {
    private UsersRepository repository;
    private LiveData<User> user;

    public UserViewModel(){
        repository = new UsersRepository();
    }

    public LiveData<User> get() {return this.user;}
    public void add(User user){ repository.add(user);}
    public void delete(User user){
        repository.delete(user);
    }

    public boolean authenticate(String username, String password) {
        return repository.authenticate(username,password);
    }
    public void reload(){
        repository.reload();
    }
}
