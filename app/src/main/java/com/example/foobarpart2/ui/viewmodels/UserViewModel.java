package com.example.foobarpart2.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.foobarpart2.db.entity.User;
import com.example.foobarpart2.repository.UsersRepository;

public class UserViewModel extends ViewModel {
    private UsersRepository repository;
    private LiveData<Boolean> signUpResult;
    private LiveData<Boolean> authenticateResult;
    private LiveData<User> userLiveData;

    public UserViewModel() {
        repository = new UsersRepository();
        signUpResult = repository.getSignUpResult();
        authenticateResult = repository.getAuthenticateResult();
        userLiveData = repository.getUserData();
    }

    public LiveData<User> getUser(String username) {
        repository.get(username);
        return userLiveData;
    }
    public LiveData<User> getLoggedInUser(String username) {
        repository.get(username);
        return userLiveData;
    }

    public void add(User user) {
        repository.add(user);
    }

    public LiveData<Boolean> getSignUpResult() {
        return signUpResult;
    }
    public LiveData<Boolean> getAuthenticateResult() {
        return authenticateResult;
    }


    public void delete(User user) {
        repository.delete(user);
    }

    public void authenticate(String username, String password) {
        repository.authenticate(username, password);
    }

    public void logOutCurrUser() {
        repository.logOutCurrUser();
    }

    public void reload() {
        repository.reload();
    }


}
