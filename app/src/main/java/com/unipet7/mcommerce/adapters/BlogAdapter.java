package com.unipet7.mcommerce.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.fragments.FragmentAllProduct;
import com.unipet7.mcommerce.fragments.FragmentBlogDetails;
import com.unipet7.mcommerce.models.Blogs;
import com.unipet7.mcommerce.models.Product;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment details = new FragmentBlogDetails();
                    FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();

                    // Thực hiện giao diện chuyển đổi Fragment
                    fragmentManager.beginTransaction()
                            .replace(((ViewGroup) v.getRootView().findViewById(android.R.id.content)).getId(), details)
                            .addToBackStack(null)
                            .commit();
                }
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment details = new FragmentBlogDetails();
                    FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();

                    // Thực hiện giao diện chuyển đổi Fragment
                    fragmentManager.beginTransaction()
                            .replace(((ViewGroup) v.getRootView().findViewById(android.R.id.content)).getId(), details)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }
    }
}
