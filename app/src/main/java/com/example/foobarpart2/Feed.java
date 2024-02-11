package com.example.foobarpart2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foobarpart2.adapters.PostListAdapter;
import com.example.foobarpart2.entities.Post;

import java.util.ArrayList;
import java.util.List;

public class Feed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        RecyclerView lstPosts = findViewById(R.id.lstPosts);
        final PostListAdapter adapter = new PostListAdapter(this);
        lstPosts.setAdapter(adapter);
        lstPosts.setLayoutManager(new LinearLayoutManager(this));

        List<Post> posts = new ArrayList<>();
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
}