package com.example.foobarpart2.ui.viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.foobarpart2.db.entity.User;
import com.example.foobarpart2.repository.UsersRepository;

public class UserViewModel extends ViewModel {
    private UsersRepository repository;


    public UserViewModel(){
        repository = new UsersRepository();
    }

    public User getLoggedInUser(String username) {return repository.getLoggedInUser(username);}
    public User getLoggedInUser() {return repository.getLoggedInUser();}
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

    public void logOutCurrUser() {
        repository.logOutCurrUser();
    }
}
