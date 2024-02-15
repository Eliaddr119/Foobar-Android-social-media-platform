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
import com.example.foobarpart2.entities.Comment;
import com.example.foobarpart2.entities.Post;
import com.example.foobarpart2.entities.PostsManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Feed extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD_POST = 100;
    private static final int REQUEST_CODE_EDIT_POST = 200;

    PostListAdapter adapter;
    List<Post> posts;
    FloatingActionButton btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        UserManager userManager = UserManager.getInstance();
        User user = userManager.getUserByUsername(userManager.getCurrentUser());

        adapter = new PostListAdapter(this, this, position -> {
            Intent intent = new Intent(Feed.this, CommentActivity.class);
            intent.putExtra("postId", adapter.getPosts().get(position).getId());
            // Assuming 'user' is a valid object in your context with a method getDisplayName()
            intent.putExtra("author", user.getDisplayName());
            startActivity(intent);
        });
        PostsManager postsManager = PostsManager.getInstance();
        posts = postsManager.getPosts();
        btnSettings = findViewById(R.id.settings);
        RecyclerView lstPosts = findViewById(R.id.lstPosts);
        lstPosts.setAdapter(adapter);
        lstPosts.setLayoutManager(new LinearLayoutManager(this));


        loadPostsFromJson();


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

    private void loadPostsFromJson() {
        try {
            // Open the JSON file from the res/raw directory
            InputStream inputStream = getResources().openRawResource(R.raw.posts);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            // Parse the JSON data using Gson
            Gson gson = new Gson();
            JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);

            // Create a list to hold all the Post objects
            List<Post> posts = PostsManager.getInstance().getPosts();

            // Iterate through the JSON array
            for (JsonElement jsonElement : jsonArray) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                // Extract post information
                int id = jsonObject.get("id").getAsInt();
                String author = jsonObject.getAsJsonObject("user").get("displayName")
                        .getAsString();
                String content = jsonObject.get("content").getAsString();
                String postTime = jsonObject.get("postTime").getAsString();
                int likes = jsonObject.get("likes").getAsInt();
                String profileImage = jsonObject.getAsJsonObject("user").get("image").getAsString();
                int lastIndex = profileImage.lastIndexOf('/');
                profileImage = profileImage.substring(lastIndex + 1).split("\\.")[0];
                int profileImageResourceId = getResources().getIdentifier(profileImage, "raw", getPackageName());
                Uri profileUri = Uri.parse("android.resource://" + getPackageName() + "/raw/" + profileImageResourceId);
                String postIm = jsonObject.get("image").getAsString();
                lastIndex = postIm.lastIndexOf('/');
                postIm = postIm.substring(lastIndex + 1).split("\\.")[0];
                profileImageResourceId = getResources().getIdentifier(postIm, "raw", getPackageName());
                Uri postImUri = Uri.parse("android.resource://" + getPackageName() + "/raw/" + profileImageResourceId);

                // Create a new Post object with extracted information
                Post post = new Post(author, content,postTime, likes,
                        postImUri, profileUri);
                post.setId(id);
                JsonArray commentsArray = jsonObject.getAsJsonArray("comments");
                for (JsonElement commentElement : commentsArray) {
                    JsonObject commentObject = commentElement.getAsJsonObject();
                    String commenterDisplayName = commentObject.getAsJsonObject("user")
                            .get("displayName").getAsString();
                    String commentContent = commentObject.get("content").getAsString();
                    Comment comment = new Comment(id, commenterDisplayName, commentContent, 0);
                    post.addComment(comment);
                }
                // Add the Post object to the list
                posts.add(post);
            }

            // Update your RecyclerView adapter with the list of posts
            adapter.setPosts(posts);
            adapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_POST && resultCode == RESULT_OK) {
            // Extract post details from data, create a Post object
            String author = data.getStringExtra("author");
            String content = data.getStringExtra("content");
            String p = data.getStringExtra("profileUri");
            String photo = data.getStringExtra("picResource");
            Uri profile = Uri.parse(p);
            Uri photoUri = Uri.parse(photo);

            int nextId = adapter.getItemCount() + 1;
            Post newPost = new Post(author, content,photoUri, profile);
            newPost.setId(nextId);
            adapter.addPost(newPost);
            adapter.notifyDataSetChanged();
        }
        if (requestCode == REQUEST_CODE_EDIT_POST && resultCode == RESULT_OK) {
            // Refresh posts list
            adapter.notifyDataSetChanged(); // Ensure your adapter has the latest posts list
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh your posts list here, for example:
        posts = PostsManager.getInstance().getPosts();
        adapter.setPosts(posts);
        adapter.notifyDataSetChanged();
    }


}