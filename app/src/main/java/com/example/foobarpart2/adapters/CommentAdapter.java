package com.example.foobarpart2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import android.widget.TextView;

import com.example.foobarpart2.R;
import com.example.foobarpart2.entities.Comment;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private List<Comment> comments;

    private CommentActionListener actionListener;

    public CommentAdapter(List<Comment> comments, CommentActionListener actionListener) {
        this.comments = comments;
        this.actionListener = actionListener;
    }


    public interface CommentActionListener {
        void onEditComment(int position, Comment comment);
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item_layout, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.authorTextView.setText(comment.getAuthor());
        holder.contentTextView.setText(comment.getContent());
    }

    @Override
    public int getItemCount() {
        return comments != null ? comments.size() : 0;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
        notifyDataSetChanged(); // Notify any registered observers that the data set has changed.
    }


    public class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView authorTextView;
        TextView contentTextView;
        TextView editCommentButton, deleteCommentButton;

        public CommentViewHolder(View itemView) {
            super(itemView);
            authorTextView = itemView.findViewById(R.id.commentAuthor);
            contentTextView = itemView.findViewById(R.id.commentContent);
            editCommentButton = itemView.findViewById(R.id.editComment);
            deleteCommentButton = itemView.findViewById(R.id.deleteComment);

            editCommentButton.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    actionListener.onEditComment(position, comments.get(position));
                }
            });

            deleteCommentButton.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                comments.remove(position);
                notifyItemRemoved(position);
                // Additionally, update your data source to reflect this change
            });

        }
    }
}
