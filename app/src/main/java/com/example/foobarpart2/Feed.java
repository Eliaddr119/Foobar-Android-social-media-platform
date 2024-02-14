package com.example.foobarpart2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foobarpart2.adapters.PostListAdapter;
import com.example.foobarpart2.entities.Post;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Feed extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD_POST = 1;
    PostListAdapter adapter;
    List<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        adapter = new PostListAdapter(this);
        posts = new ArrayList<>();

        RecyclerView lstPosts = findViewById(R.id.lstPosts);

        lstPosts.setAdapter(adapter);
        lstPosts.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.btnAdd);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(this, CreatePostActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_POST);
        });

        UserManager userManager = UserManager.getInstance();
        User user = userManager.getUserByUsername(userManager.getCurrentUser());
        posts.add(new Post("Alice1","Hello world1",R.drawable.pic1,
                user.getProfileImage()));
        posts.add(new Post("Alice2","Hello world2",R.drawable.pic1,
                user.getProfileImage()));
        posts.add(new Post("Alice3","Hello world3",R.drawable.pic1,
                user.getProfileImage()));
        posts.add(new Post("Alice4","Hello world4",R.drawable.pic1,
                user.getProfileImage()));

        adapter.setPosts(posts);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_POST && resultCode == RESULT_OK) {
            // Extract post details from data, create a Post object
            String author = data.getStringExtra("author");
            String content = data.getStringExtra("content");
            String p  = data.getStringExtra("profileUri");
            Uri profile = Uri.parse(p);


            Post newPost = new Post(author, content,profile);
            adapter.addPost(newPost);
            adapter.notifyDataSetChanged();
        }
    }
}