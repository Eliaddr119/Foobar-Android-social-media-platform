package com.example.foobarpart2;

import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foobarpart2.entities.Comment;
import com.example.foobarpart2.adapters.CommentAdapter;
import com.example.foobarpart2.entities.Post;
import com.example.foobarpart2.entities.PostsManager;
import com.example.foobarpart2.viewmodels.CommentStorage;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity implements CommentAdapter.CommentActionListener {
    private ArrayList<Comment> commentsList = new ArrayList<>();
    private RecyclerView commentsRecyclerView;
    private EditText newCommentEditText;
    private Button addCommentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        // Initialize your views
        commentsRecyclerView = findViewById(R.id.commentsRecyclerView);
        newCommentEditText = findViewById(R.id.newCommentEditText);
        addCommentButton = findViewById(R.id.addCommentButton);


        // Setup RecyclerView with an adapter
        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        CommentAdapter commentAdapter = new CommentAdapter(commentsList,this);
        commentsRecyclerView.setAdapter(commentAdapter);

        int postId = getIntent().getIntExtra("postId", -1); // Use a default value in case it's not found

        List<Comment> comments = CommentStorage.commentsMap.get(postId); // postId is the ID of the current post
        if (comments != null) {
            // Update your RecyclerView adapter with these comments
            commentAdapter.setComments(comments);
            commentAdapter.notifyDataSetChanged();
        }

        // Assume postId is passed via Intent
        String author = getIntent().getStringExtra("author") + ":";
        addCommentButton.setOnClickListener(view -> {
            String commentContent = newCommentEditText.getText().toString();
            Comment newComment = new Comment(postId, author, commentContent, System.currentTimeMillis());
            commentsList.add(newComment);
            commentAdapter.notifyItemInserted(commentsList.size() - 1);
            newCommentEditText.setText(""); // Clear the input field

            // Add to static storage
            List<Comment> commentsForPost = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                commentsForPost = CommentStorage.commentsMap.getOrDefault(postId, new ArrayList<>());
            }
            commentsForPost.add(newComment);
            CommentStorage.commentsMap.put(postId, commentsForPost);

            Post postToUpdate = PostsManager.getInstance().findPostById(postId);
            postToUpdate.addComment(newComment);
        });


    }
    @Override
    public void onEditComment(int position, Comment comment) {
        showEditCommentDialog(comment, position);
    }

    private void showEditCommentDialog(Comment comment, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Comment");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(comment.getContent());
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String newText = input.getText().toString();
            // Update comment content
            comment.setContent(newText);
            // Update in static storage
            List<Comment> commentsForPost = CommentStorage.commentsMap.get(comment.getPostId());
            if(commentsForPost != null) {
                commentsForPost.set(position, comment);
                CommentStorage.commentsMap.put(comment.getPostId(), commentsForPost);
            }
            // Refresh adapter
            commentsRecyclerView.getAdapter().notifyItemChanged(position);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
