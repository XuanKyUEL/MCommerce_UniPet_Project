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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.unipet7.mcommerce.R;
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
//        Blogs blogs = blogsList.get(position);
//        holder.blogTitle.setText(blogs.getTitle());
//        holder.blogDesciption.setText(blogs.getDescription());
//        holder.blogPubDate.setText(blogs.getPubDate());
//        holder.blogPic.setImageResource(blogs.getPic());
//        holder.blogPic2.setImageResource(blogs.getPic());
        Blogs blogs = blogsList.get(position);
        if (holder instanceof ImageViewHolder) {
            ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
            imageViewHolder.imageView.setImageResource(blogs.getPic());
        } else if (holder instanceof ImageWithDescriptionViewHolder) {
            ImageWithDescriptionViewHolder imageWithDescriptionViewHolder = (ImageWithDescriptionViewHolder) holder;
            imageWithDescriptionViewHolder.imageView.setImageResource(blogs.getPic());
            imageWithDescriptionViewHolder.textViewName.setText(blogs.getTitle());
            imageWithDescriptionViewHolder.textViewDescription.setText(blogs.getDescription());
            imageWithDescriptionViewHolder.textViewDate.setText(blogs.getPubDate());
        }
    }
//    @Override
//    public void onBindViewHolder(@NonNull  BlogAdapter.ViewHolder holder, int position) {
//            String picList = "";
//            int drawableResourceId;
//
//            if (layoutType == 1) {
//                // Gán ảnh cho layout loại 1
//                switch (position) {
//                    case 0:
//                        picList = "blog_1";
//                        holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.blog_image));
//                        break;
//                    case 1:
//                        picList = "blog_2";
//                        holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.blog_image_1));
//                        break;
//                    case 2:
//                        picList = "blog_3";
//                        holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.blog_image_2));
//                        break;
//                    default:
//                        break;
//                }
//                drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(picList, "drawable", holder.itemView.getContext().getPackageName());
//                Glide.with(holder.itemView.getContext())
//                        .load(drawableResourceId)
//                        .fitCenter()
//                        .transform(new RoundedCornersTransformation(16 , 0))
//                        .into(holder.blogPic);
//            } else {
//                // Gán ảnh cho layout loại 2
//                holder.blogTitle.setText(blogsList.get(position).getTitle());
//                holder.blogPubDate.setText(blogsList.get(position).getPubDate());
//                holder.blogDesciption.setText(blogsList.get(position).getDescription());
//
//                switch (position) {
//                    case 0:
//                        picList = "blog_4";
//                        holder.mainLayout2.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.blog2_image_4));
//                        break;
//                    case 1:
//                        picList = "blog_5";
//                        holder.mainLayout2.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.blog2_image_5));
//                        break;
//                    case 2:
//                        picList = "blog_6";
//                        holder.mainLayout2.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.blog2_image_6));
//                        break;
//                    default:
//                        break;
//                }
//                drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(picList, "drawable", holder.itemView.getContext().getPackageName());
//                Glide.with(holder.itemView.getContext())
//                        .load(drawableResourceId)
//                        .fitCenter()
//                        .transform(new RoundedCornersTransformation(16 , 0))
//                        .into(holder.blogPic2);
//            }
//        }

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

        ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imvblogImage);
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

        }
    }
}
