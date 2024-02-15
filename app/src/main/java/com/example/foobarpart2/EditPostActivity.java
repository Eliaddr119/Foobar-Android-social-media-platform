package com.example.foobarpart2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foobarpart2.entities.Post;
import com.example.foobarpart2.entities.PostsManager;

public class EditPostActivity extends AppCompatActivity {
    private EditText editTextContent;
    private Button btnSave;
    private int postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        editTextContent = findViewById(R.id.editTextContent);
        btnSave = findViewById(R.id.btnSave);

        // Get the post ID and current content from the intent
        postId = getIntent().getIntExtra("postId", -1);
        String content = getIntent().getStringExtra("content");

        editTextContent.setText(content);

        btnSave.setOnClickListener(v -> {
            String updatedContent = editTextContent.getText().toString();
            // Update the post in PostsManager
            Post post = PostsManager.getInstance().findPostById(postId);
            if (post != null) {
                post.setContent(updatedContent);
            }

            // Set result and finish
            setResult(RESULT_OK);
            finish();
        });
    }
}

