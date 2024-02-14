package com.example.foobarpart2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foobarpart2.entities.Post;

public class CreatePostActivity extends AppCompatActivity {

    private EditText editTextPostContent;
    private Button buttonSubmitPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        editTextPostContent = findViewById(R.id.editTextPostContent);
        buttonSubmitPost = findViewById(R.id.buttonSubmitPost);

        buttonSubmitPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });
    }

    private void submitPost() {
        String content = editTextPostContent.getText().toString().trim();
        if (!content.isEmpty()) {
            UserManager userManager = UserManager.getInstance();
            User user = userManager.getUserByUsername(userManager.getCurrentUser());
            Uri profileImage = user != null ? user.getProfileImage() : null;

            Intent returnIntent = new Intent();
            returnIntent.putExtra("author", user.getUsername());
            returnIntent.putExtra("content", content);
            returnIntent.putExtra("picResource", R.drawable.pic1); // Assuming a default image for now
            returnIntent.putExtra("profileUri", profileImage.toString());
            setResult(RESULT_OK, returnIntent);
            finish();
        }
    }

}
