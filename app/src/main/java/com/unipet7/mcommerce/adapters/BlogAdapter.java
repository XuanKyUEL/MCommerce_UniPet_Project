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

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.ViewHolder> {
    ArrayList<Blogs>blogsList;
    private int layoutType;

    public BlogAdapter(ArrayList<Blogs> blogsList, int layoutType) {
        this.blogsList = blogsList;
        this.layoutType = layoutType;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        int layoutRes;
        if (layoutType == 1) {
            layoutRes = R.layout.blog_item_layout;
        } else {
            layoutRes = R.layout.blog_item_2;
        }

        View inflate = LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull  BlogAdapter.ViewHolder holder, int position) {
            String picList = "";
            int drawableResourceId;

            if (layoutType == 1) {
                // Gán ảnh cho layout loại 1
                switch (position) {
                    case 0:
                        picList = "blog_1";
                        holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.blog_image));
                        break;
                    case 1:
                        picList = "blog_2";
                        holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.blog_image_1));
                        break;
                    case 2:
                        picList = "blog_3";
                        holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.blog_image_2));
                        break;
                    default:
                        break;
                }
                drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(picList, "drawable", holder.itemView.getContext().getPackageName());
                Glide.with(holder.itemView.getContext())
                        .load(drawableResourceId)
                        .fitCenter()
                        .transform(new RoundedCornersTransformation(16 , 0))
                        .into(holder.blogPic);
            } else {
                // Gán ảnh cho layout loại 2
                holder.blogTitle.setText(blogsList.get(position).getTitle());
                holder.blogPubDate.setText(blogsList.get(position).getPubDate());
                holder.blogDesciption.setText(blogsList.get(position).getDescription());

                switch (position) {
                    case 0:
                        picList = "blog_4";
                        holder.mainLayout2.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.blog2_image_4));
                        break;
                    case 1:
                        picList = "blog_5";
                        holder.mainLayout2.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.blog2_image_5));
                        break;
                    case 2:
                        picList = "blog_6";
                        holder.mainLayout2.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.blog2_image_6));
                        break;
                    default:
                        break;
                }
                drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(picList, "drawable", holder.itemView.getContext().getPackageName());
                Glide.with(holder.itemView.getContext())
                        .load(drawableResourceId)
                        .fitCenter()
                        .transform(new RoundedCornersTransformation(16 , 0))
                        .into(holder.blogPic2);
            }
        }

    @Override
    public int getItemCount() {
        return blogsList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView blogTitle,blogPubDate,blogDesciption;
        ImageView blogPic,blogPic2;
        ConstraintLayout mainLayout2;

        LinearLayout mainLayout;


        public ViewHolder(@NonNull  View itemView){
            super(itemView);
            blogPic=itemView.findViewById(R.id.imvblogImage);
            blogPic2=itemView.findViewById(R.id.imvblogImage2);
            blogTitle=itemView.findViewById(R.id.txtBlog2Title);
            blogPubDate=itemView.findViewById(R.id.txtBlog2PubDate);
            blogDesciption=itemView.findViewById(R.id.txtBlog2Des);
            mainLayout=itemView.findViewById(R.id.mainLayout);
            mainLayout2=itemView.findViewById(R.id.mainLayout2);
        }

    }
}
