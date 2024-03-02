package com.example.foobarpart2.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.foobarpart2.db.entity.Post;
import com.example.foobarpart2.repository.PostsRepository;

import java.util.List;

public class PostsViewModel extends ViewModel {
    private PostsRepository repository;
    private LiveData<List<Post>> posts;

    public PostsViewModel() {
        repository = new PostsRepository();
        posts = repository.getAll();
    }
    public LiveData<List<Post>> get() { return posts; }
    public void add(Post post) { repository.add(post); }
    public void delete(Post post) { repository.delete(post); }
    public void reload() { repository.reload(); }

}
