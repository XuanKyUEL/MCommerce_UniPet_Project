package com.unipet7.mcommerce.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.activities.BlogDetails;
import com.unipet7.mcommerce.activities.Notification;
import com.unipet7.mcommerce.models.Blogs;

import java.util.ArrayList;

public class BlogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Blogs>blogsList;
    private static final int VIEW_TYPE_IMAGE = 1;
    private static final int VIEW_TYPE_IMAGE_AND_DESCRIPTION = 2;

    public BlogAdapter(ArrayList<Blogs> blogsList) {
        this.blogsList = blogsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        if (viewType == VIEW_TYPE_IMAGE) {
            // Inflate layout for view type with image only
            view = inflater.inflate(R.layout.blog_item_layout, parent, false);
            return new ImageViewHolder(view);
        } else if (viewType == VIEW_TYPE_IMAGE_AND_DESCRIPTION) {
            // Inflate layout for view type with image and description
            view = inflater.inflate(R.layout.blog_item_2, parent, false);
            return new ImageWithDescriptionViewHolder(view);
        }
        throw new IllegalArgumentException("Invalid view type: " + viewType);
    }
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Blogs blogs = blogsList.get(position);
        if (holder instanceof ImageViewHolder) {
            ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
            imageViewHolder.imageView.setImageResource(blogs.getPic());
            imageViewHolder.textViewName1.setText(blogs.getTitle());
        } else if (holder instanceof ImageWithDescriptionViewHolder) {
            ImageWithDescriptionViewHolder imageWithDescriptionViewHolder = (ImageWithDescriptionViewHolder) holder;
            imageWithDescriptionViewHolder.imageView.setImageResource(blogs.getPic());
            imageWithDescriptionViewHolder.textViewName.setText(blogs.getTitle());
            imageWithDescriptionViewHolder.textViewDescription.setText(blogs.getDescription());
            imageWithDescriptionViewHolder.textViewDate.setText(blogs.getPubDate());
        }

    }

    @Override
    public int getItemCount() {
        return blogsList.size();
    }
    @Override
    public int getItemViewType(int position) {
        return blogsList.get(position).getViewType();
    }
    private static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewName1;
        ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imvblogImage);
            textViewName1 = itemView.findViewById(R.id.txtTitle1);

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), BlogDetails.class);
                v.getContext().startActivity(intent);
            });
        }

    }

    private static class ImageWithDescriptionViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewName, textViewDescription, textViewDate ;

        ImageWithDescriptionViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imvblogImage2);
            textViewName = itemView.findViewById(R.id.txtBlog2Title);
            textViewDescription = itemView.findViewById(R.id.txtBlog2Des);
            textViewDate = itemView.findViewById(R.id.txtBlog2PubDate);
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), BlogDetails.class);
                v.getContext().startActivity(intent);
            });
        }
    }

}
