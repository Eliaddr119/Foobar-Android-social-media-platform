package com.example.foobarpart2.ui.activity;

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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.foobarpart2.R;
import com.example.foobarpart2.db.entity.Post;
import com.example.foobarpart2.db.entity.User;
import com.example.foobarpart2.models.LoggedInUser;
import com.example.foobarpart2.ui.adapter.PostListAdapter;
import com.example.foobarpart2.ui.viewmodels.PostsViewModel;
import com.example.foobarpart2.ui.viewmodels.UserViewModel;
import com.example.foobarpart2.utilities.ImageUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Feed extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD_POST = 100;
    private static final int REQUEST_CODE_EDIT_POST = 200;

    PostListAdapter adapter;
    LiveData<List<Post>> posts;
    FloatingActionButton btnSettings;
    private PostsViewModel postViewModel;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        postViewModel = new ViewModelProvider(this).get(PostsViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        User user = LoggedInUser.getInstance().getUser();

        adapter = new PostListAdapter(this, this, position -> {
            Intent intent = new Intent(Feed.this, CommentActivity.class);
            intent.putExtra("postId", adapter.getPosts().get(position).getPostId());
            intent.putExtra("author", user.getDisplayName());
            startActivity(intent);
        },this::onDeletePost,this::onLikePost);


        btnSettings = findViewById(R.id.settings);
        RecyclerView lstPosts = findViewById(R.id.lstPosts);
        lstPosts.setAdapter(adapter);
        lstPosts.setLayoutManager(new LinearLayoutManager(this));

        SwipeRefreshLayout refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(() -> {
            postViewModel.reload();
        });

        postViewModel.get().observe(this, posts -> {
            adapter.setPosts(posts);
            refreshLayout.setRefreshing(false);
        });


        //loadPostsFromJson();


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
        userViewModel.logOutCurrUser();
        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);
        finish(); // Close current activity
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_POST && resultCode == RESULT_OK) {
            // Extract post details from data, create a Post object
            String content = data.getStringExtra("content");

            String imageUriString = data.getStringExtra("image");
            Uri imageUri = null;
            if (!"null".equals(imageUriString)) {
                imageUri = Uri.parse(imageUriString);
            }
            String image;
            try {
                image = ImageUtils.convertToBase64(imageUri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            User currentUser = LoggedInUser.getInstance().getUser();

            Calendar calendar = Calendar.getInstance();
            Date currentDate = calendar.getTime();
            Post newPost;

            if (image.equals("null")) {
                assert content != null;
                newPost = new Post(currentUser.getUsername(), currentUser.getDisplayName(),
                        currentUser.getProfilePic(),currentDate,content,0,
                        new ArrayList<>(),0,null,null);
            } else {
                newPost = new Post(currentUser.getUsername(), currentUser.getDisplayName(),
                        currentUser.getProfilePic(),currentDate,content,0,
                        new ArrayList<>(),0,null,image);
            }
            postViewModel.add(newPost);
            adapter.addPost(newPost);
            adapter.notifyDataSetChanged();
        }
        if (requestCode == REQUEST_CODE_EDIT_POST && resultCode == RESULT_OK) {
            // Refresh posts list
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        postViewModel.get().observe(this, posts -> {
            adapter.setPosts(posts);
        });
        adapter.notifyDataSetChanged();
    }
    public void onDeletePost(String postId) {
        postViewModel.delete(postId);
    }
    private void onLikePost(String postId) {
        postViewModel.likePost(postId);
    }
}