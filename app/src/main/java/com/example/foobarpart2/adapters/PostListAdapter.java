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

import java.util.ArrayList;
import java.util.List;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostViewHolder> {
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

        }


    }
    private final LayoutInflater mInflater;
    private List<Post> posts;
    public PostListAdapter(Context context) { mInflater = LayoutInflater.from(context);}

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.post_layout,parent,false);
        return new PostViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(PostViewHolder holder, int position){
        if (posts != null){
            final Post current = posts.get(position);
            holder.tvAuthor.setText(current.getAuthor());
            holder.tvContent.setText(current.getContent());
            holder.ivPic.setImageResource(current.getPic());
            holder.profile.setImageURI(current.getProfile());
        }
    }
    public void setPosts(List<Post> s){
        posts = s;
        notifyDataSetChanged();
    }
    public void addPost(Post post) {
        if (posts == null) {
            posts = new ArrayList<>();
        }
        posts.add(0,post);
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
