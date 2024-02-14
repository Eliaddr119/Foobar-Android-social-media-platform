package com.example.foobarpart2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
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
    FloatingActionButton btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        adapter = new PostListAdapter(this);
        posts = new ArrayList<>();
        btnSettings = findViewById(R.id.settings);
        RecyclerView lstPosts = findViewById(R.id.lstPosts);
        lstPosts.setAdapter(adapter);
        lstPosts.setLayoutManager(new LinearLayoutManager(this));

        UserManager userManager = UserManager.getInstance();
        User user = userManager.getUserByUsername(userManager.getCurrentUser());
        posts.add(new Post("Alice1","Hello world1",15,R.drawable.pic1,
                user.getProfileImage()));
        posts.add(new Post("Alice2","Hello world2",3,R.drawable.pic1,
                user.getProfileImage()));
        posts.add(new Post("Alice3","Hello world3",4,R.drawable.pic1,
                user.getProfileImage()));
        posts.add(new Post("Alice4","Hello world4",R.drawable.pic1,
                user.getProfileImage()));

        adapter.setPosts(posts);

        FloatingActionButton fab = findViewById(R.id.btnAdd);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(this, CreatePostActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_POST);
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(Feed.this, btnSettings);
                popup.getMenuInflater().inflate(R.menu.menu_settings, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.menu_logout) {
                            logout();
                            Toast.makeText(Feed.this, "Logout clicked",
                                    Toast.LENGTH_SHORT).show();
                            return true;
                        } else if (item.getItemId() == R.id.menu_dark_mode) {
                            SharedPreferences sharedPreferences = getSharedPreferences("Mode",
                                    Context.MODE_PRIVATE);
                            boolean nightMode = sharedPreferences.getBoolean("nightMode",
                                    false);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            if (nightMode) {
                                AppCompatDelegate.
                                        setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                                editor.putBoolean("nightMode", false);
                            } else {
                                AppCompatDelegate.
                                        setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                                editor.putBoolean("nightMode", true);
                            }
                            editor.apply(); // Apply changes to SharedPreferences
                            Toast.makeText(Feed.this, "Dark Mode clicked",
                                    Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });

    }
    private void logout() {
        UserManager.getInstance().setCurrentUser(null);
        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);
        finish(); // Close current activity
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