package com.example.foobarpart2.db.entity;

import java.util.ArrayList;
import java.util.List;

public class PostsManager {
    private static PostsManager instance;
    public List<Post> posts;

    private PostsManager() {
        posts = new ArrayList<>();
    }

    public static synchronized PostsManager getInstance() {
        if (instance == null) {
            instance = new PostsManager();
        }
        return instance;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void addPost(Post post) {
        this.posts.add(0,post);
    }

    public Post findPostById(int id) {
        for (Post post : posts) {
            if (post.getId() == id) {
                return post;
            }
        }
        return null;
    }
}
