package com.pakholchuk.testskillbranch2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pakholchuk.testskillbranch2.databinding.RecyclerItemBinding;
import com.pakholchuk.testskillbranch2.pojo.Post;

import java.util.ArrayList;
import java.util.Collection;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {
    RecyclerItemBinding binding;
    ArrayList<Post> posts = new ArrayList<>();
    ItemViewClickListener itemViewClickListener;

    public PostAdapter(ItemViewClickListener itemViewClickListener) {
        this.itemViewClickListener = itemViewClickListener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RecyclerItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PostViewHolder(binding, itemViewClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.bind(posts.get(position));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setPosts(Collection<Post> postCollection) {
        posts.addAll(postCollection);
        notifyDataSetChanged();
    }

}
