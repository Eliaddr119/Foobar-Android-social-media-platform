package com.example.foobarpart2.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.foobarpart2.db.entity.User;
import com.example.foobarpart2.repository.UsersRepository;

public class UserViewModel extends ViewModel {
    private UsersRepository repository;
    private LiveData<Boolean> signUpResult;
    private LiveData<Boolean> authenticateResult;

    public UserViewModel() {
        repository = new UsersRepository();
        signUpResult = repository.getSignUpResult();
        authenticateResult = repository.getAuthenticateResult();
    }


    public User getLoggedInUser(String username) {
        return repository.getLoggedInUser(username);
    }

    public User getLoggedInUser() {
        return repository.getLoggedInUser();
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
