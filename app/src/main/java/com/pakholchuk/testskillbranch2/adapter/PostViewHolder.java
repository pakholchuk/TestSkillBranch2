package com.pakholchuk.testskillbranch2.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pakholchuk.testskillbranch2.databinding.RecyclerItemBinding;
import com.pakholchuk.testskillbranch2.pojo.Post;

class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private RecyclerItemBinding binding;
    private ItemViewClickListener listener;
    private Post post;

    PostViewHolder(@NonNull RecyclerItemBinding b, ItemViewClickListener viewClickListener) {
        super(b.getRoot());
        binding = RecyclerItemBinding.bind(itemView);
        listener = viewClickListener;
        binding.getRoot().setOnClickListener(this);
    }

    void bind(Post post) {
        binding.tvItemId.setText(String.valueOf(post.getId()));
        binding.tvItemTitle.setText(post.getTitle());
        this.post = post;
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v, post);
    }
}
