package com.example.foobarpart2.ui.activity;

import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foobarpart2.R;
import com.example.foobarpart2.db.entity.Comment;
import com.example.foobarpart2.ui.adapter.CommentAdapter;
import com.example.foobarpart2.db.entity.Post;
import com.example.foobarpart2.db.entity.PostsManager;
import com.example.foobarpart2.ui.viewmodels.CommentStorage;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity implements CommentAdapter.CommentActionListener {
    private ArrayList<Comment> commentsList = new ArrayList<>();
    private RecyclerView commentsRecyclerView;
    private EditText newCommentEditText;
    private Button addCommentButton;
    //check
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
        CommentAdapter commentAdapter = new CommentAdapter(commentsList, this);
        commentsRecyclerView.setAdapter(commentAdapter);

        int postId = getIntent().getIntExtra("postId", -1); // Use a default value in case it's not found

        List<Comment> comments = CommentStorage.commentsMap.get(postId); // postId is the ID of the current post
        commentsList.clear();

        if (comments != null) {
            commentsList.addAll(comments);
        }
        if (comments != null) {
            // Update your RecyclerView adapter with these comments
            commentAdapter.setComments(comments);
            commentAdapter.notifyDataSetChanged();
        }

        // Assume postId is passed via Intent
        String author = getIntent().getStringExtra("author") + ":";
        addCommentButton.setOnClickListener(view -> {
            String commentContent = newCommentEditText.getText().toString();

            if (commentContent.isEmpty()) {
                // Show a Toast or alert to indicate that the comment cannot be empty
                Toast.makeText(CommentActivity.this, "Write a Comment First", Toast.LENGTH_SHORT).show();
                return; // Exit the listener, preventing further execution
            }

            Comment newComment = new Comment(postId, author, commentContent, System.currentTimeMillis());

            // Update the Post object
            Post postToUpdate = PostsManager.getInstance().findPostById(postId);
            if (postToUpdate != null) {
                postToUpdate.addComment(newComment);
            }

            // Refresh commentsList from CommentStorage
            commentsList.clear();
            List<Comment> updatedComments = CommentStorage.commentsMap.get(postId);
            if (updatedComments != null) {
                commentsList.addAll(updatedComments);
                commentAdapter.setComments(updatedComments);
            }
            commentAdapter.notifyDataSetChanged(); // Notify the adapter of the data change

            newCommentEditText.setText(""); // Clear the input field
        });


    }

    @Override
    public void onEditComment(int position, Comment comment) {
        showEditCommentDialog(comment, position);
    }

    @Override
    public void onDeleteComment(int postId, Comment comment) {
        // Retrieve the list of comments for the post
        List<Comment> commentsForPost = CommentStorage.commentsMap.get(postId);
        Post postToUpdate = PostsManager.getInstance().findPostById(postId);
        if (commentsForPost != null) {
            // Remove the comment from the list
            commentsForPost.remove(comment);
            // Update the map with the modified list
            CommentStorage.commentsMap.put(postId, commentsForPost);
            // Optionally, refresh the comments list in the UI if needed
            commentsList.clear();
            commentsList.addAll(commentsForPost);
            postToUpdate.removeComment(comment);
            commentsRecyclerView.getAdapter().notifyDataSetChanged();
        }
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
            if (commentsForPost != null) {
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
