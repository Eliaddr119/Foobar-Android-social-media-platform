package com.example.foobarpart2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foobarpart2.adapters.PostListAdapter;
import com.example.foobarpart2.entities.Comment;
import com.example.foobarpart2.entities.Post;
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

    private static final int REQUEST_CODE_ADD_POST = 1;
    PostListAdapter adapter;
    List<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        UserManager userManager = UserManager.getInstance();
        User user = userManager.getUserByUsername(userManager.getCurrentUser());

        adapter = new PostListAdapter(this, position -> {
            Intent intent = new Intent(Feed.this, CommentActivity.class);
            intent.putExtra("postId", adapter.getPosts().get(position).getId());
            intent.putExtra("author", user.getDisplayName());
            startActivity(intent);
        });

        posts = new ArrayList<>();

        RecyclerView lstPosts = findViewById(R.id.lstPosts);

        lstPosts.setAdapter(adapter);
        lstPosts.setLayoutManager(new LinearLayoutManager(this));


//        Post post1 = new Post("Alice1", "Hello world1", 15, R.drawable.pic1,
//                user.getProfileImage());
//        Post post2 = new Post("Alice2", "Hello world2", 3, R.drawable.pic1,
//                user.getProfileImage());
//        Post post3 = new Post("Alice3", "Hello world3", 4, R.drawable.pic1,
//                user.getProfileImage());
//        Post post4 = new Post("Alice4", "Hello world4", R.drawable.pic1,
//                user.getProfileImage());
//        post1.setId(1);
//        post2.setId(2);
//        post3.setId(3);
//        post4.setId(4);
//        posts.add(post1);
//        posts.add(post2);
//        posts.add(post3);
//        posts.add(post4);
//
//        adapter.setPosts(posts);
        loadPostsFromJson();

        FloatingActionButton fab = findViewById(R.id.btnAdd);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(this, CreatePostActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_POST);
        });


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
            List<Post> posts = new ArrayList<>();
            List<Comment> comments = new ArrayList<>();

            // Iterate through the JSON array
            for (JsonElement jsonElement : jsonArray) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                // Extract post information
                int id = jsonObject.get("id").getAsInt();
                String author = jsonObject.getAsJsonObject("user").get("username")
                        .getAsString();
                String content = jsonObject.get("content").getAsString();
                String postTime = jsonObject.get("postTime").getAsString();
                int likes = jsonObject.get("likes").getAsInt();
                int commentsCount = jsonObject.get("commentsCount").getAsInt();
                String profileImage = jsonObject.getAsJsonObject("user").get("image")
                        .getAsString();
                Uri profileUri = Uri.parse(profileImage);

                // Create a new Post object with extracted information
                Post post = new Post(author, content,postTime, likes,commentsCount,
                        R.drawable.pic1, profileUri);
                post.setId(id);
                JsonArray commentsArray = jsonObject.getAsJsonArray("comments");
                for (JsonElement commentElement : commentsArray) {
                    JsonObject commentObject = commentElement.getAsJsonObject();
                    String commenterDisplayName = commentObject.getAsJsonObject("user")
                            .get("displayName").getAsString();
                    String commentContent = commentObject.get("content").getAsString();
                    long commentTime = commentObject.get("commentTime").getAsLong();
                    Comment comment = new Comment(id, commenterDisplayName, commentContent, commentTime);
                    comments.add(comment);
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

            int nextId = adapter.getItemCount();
            Post newPost = new Post(author, content,photoUri, profile);
            newPost.setId(nextId);
            adapter.addPost(newPost);
            adapter.notifyDataSetChanged();
        }
    }
}