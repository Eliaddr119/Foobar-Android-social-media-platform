package com.example.foobarpart2.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.foobarpart2.db.entity.Post;
import com.example.foobarpart2.repository.PostsRepository;

import java.util.List;

public class PostsViewModel extends ViewModel {
    private PostsRepository repository;
    private LiveData<List<Post>> posts;
    private LiveData<Post> post;


    public PostsViewModel() {
        repository = new PostsRepository();
        posts = repository.getAll();
        post = repository.getPostData();
    }
    public LiveData<List<Post>> get() { return posts; }
    public void add(Post post) { repository.add(post); }
    public void delete(int postId) { repository.delete(postId); }
    public void reload() { repository.reload(); }

    public void getPostFromDao(int postId) {
        repository.getPostFromDao(postId);
    }

    public LiveData<Post> getPost() {
        return post;
    }

    public void edit(int postId, String updatedContent, String image) {
        repository.edit(postId,updatedContent,image);
    }
}
