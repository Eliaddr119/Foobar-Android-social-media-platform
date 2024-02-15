package com.example.foobarpart2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.foobarpart2.R;
import com.example.foobarpart2.entities.Post;
import com.example.foobarpart2.entities.PostsManager;

import java.util.ArrayList;
import java.util.List;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostViewHolder> {
    public interface OnCommentButtonClickListener {
        void onCommentButtonClicked(int position);
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvAuthor;
        private final TextView tvContent;
        private final ImageView ivPic;
        private final ImageView profile;
        private final ImageButton btnLike;
        private final ImageButton btnComment;
        private final ImageButton btnShare;
        private final TextView numOfLikes;
        private final TextView numOfComments;
        private final ImageButton deletePostButton;



        private PostViewHolder(View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvContent = itemView.findViewById(R.id.tvContent);
            ivPic = itemView.findViewById(R.id.ivPic);
            profile = itemView.findViewById(R.id.profile_pic);
            btnLike = itemView.findViewById(R.id.btnLike);
            btnComment = itemView.findViewById(R.id.btnComment);
            btnShare = itemView.findViewById(R.id.btnShare);
            numOfLikes = itemView.findViewById(R.id.num_of_likes);
            numOfComments = itemView.findViewById(R.id.num_of_comments);
            deletePostButton = itemView.findViewById(R.id.deletePostButton);

            btnLike.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Post post = posts.get(position);
                    post.toggleLikeStatus();
                    int likeCount = post.getLikes();
                    numOfLikes.setText(String.valueOf(likeCount));
                    if (post.isLiked()) {
                        btnLike.setImageResource(R.drawable.ic_liked);
                    } else {
                        btnLike.setImageResource(R.drawable.ic_like);
                    }
                }
            });

            deletePostButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        removeAt(position);
                    }
                }
            });

            btnComment.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    commentButtonClickListener.onCommentButtonClicked(position);
                }
            });
        }


    }
    private final LayoutInflater mInflater;
    private List<Post> posts;

    private OnCommentButtonClickListener commentButtonClickListener;
    public PostListAdapter(Context context,OnCommentButtonClickListener listener) {
        mInflater = LayoutInflater.from(context);
        this.commentButtonClickListener = listener;
        this.posts = PostsManager.getInstance().getPosts();
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.post_layout,parent,false);
        return new PostViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(PostViewHolder holder, int position){
        if (posts != null){
            final Post current = PostsManager.getInstance().getPosts().get(position);
            holder.tvAuthor.setText(current.getAuthor());
            holder.tvContent.setText(current.getContent());
            if (current.getPic() == -1){
                holder.ivPic.setImageURI(current.getPicUri());
            }else {
                holder.ivPic.setImageResource(current.getPic());
            }
            holder.profile.setImageURI(current.getProfile());
            holder.numOfLikes.setText(String.valueOf(current.getLikes()));

            holder.numOfComments.setText(String.valueOf(current.getCommentsCount()));


            holder.btnLike.setOnClickListener(v -> {
                // Toggle like status for the current post
                current.toggleLikeStatus();

                // Update like count in the UI
                holder.numOfLikes.setText(String.valueOf(current.getLikes()));

                // Update like button icon based on like status
                if (current.isLiked()) {
                    holder.btnLike.setImageResource(R.drawable.ic_liked);
                } else {
                    holder.btnLike.setImageResource(R.drawable.ic_like);
                }
            });

        }
    }
    public void setPosts(List<Post> s){
        posts = PostsManager.getInstance().getPosts();
        notifyDataSetChanged();
    }
    public void addPost(Post post) {
        PostsManager.getInstance().addPost(post);
        notifyItemInserted(posts.size() - 1);
    }

    @Override
    public int getItemCount(){
        if (posts != null){
            return posts.size();
        }else return 0;
    }
    public List<Post> getPosts() { return posts;}

    public void removeAt(int position) {
        posts.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, posts.size());
    }


}
