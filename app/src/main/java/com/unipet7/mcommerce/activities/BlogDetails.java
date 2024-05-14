package com.unipet7.mcommerce.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.adapters.BlogAdapter;
import com.unipet7.mcommerce.adapters.ProductAdapter;
import com.unipet7.mcommerce.databinding.ActivityBlogDetailsBinding;
import com.unipet7.mcommerce.databinding.ActivityNotificationBinding;
import com.unipet7.mcommerce.firebase.FireStoreClass;
import com.unipet7.mcommerce.fragments.Home;
import com.unipet7.mcommerce.models.Blogs;
import com.unipet7.mcommerce.models.Product;
import com.unipet7.mcommerce.utils.LoadingDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BlogDetails extends AppCompatActivity {
    ActivityBlogDetailsBinding binding;
    BlogAdapter blogAdapter;
    ProductAdapter adapter;
    ArrayList<Product> products;

    List<Integer> favList = new ArrayList<>();

    FireStoreClass fireStoreClass = new FireStoreClass();
    public ArrayList<Product> blogProducts = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBlogDetailsBinding.inflate(getLayoutInflater());
        loadProduct();
        addEvents();
        initBlog();
        setContentView(binding.getRoot());
    }
    private void loadProduct() {
        LoadingDialog ldDialog1 = new LoadingDialog();
        ldDialog1.showLoadingDialog(this);
        fireStoreClass.getRandomProductsBlog(this, blogProducts, 10);
        ldDialog1.dissmis();
    }
    public void configAdaptersBlog() {
        adapter = new ProductAdapter(blogProducts, fireStoreClass, false);
        binding.rclBlogDetails1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rclBlogDetails1.setAdapter(adapter);
        binding.rclBlogDetails1.setHasFixedSize(true);
    }

    private void addEvents() {
        setSupportActionBar(binding.toolbardetail);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_profile);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        binding.toolbardetail.setNavigationOnClickListener(v -> finish());
    }
    private void initBlog() {
        binding.rclBlogDetails2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ArrayList<Blogs> blogs = new ArrayList<>();
        blogs.add(new Blogs("5 tiêu chuẩn thức ăn cho mèo mà một Sen chính hiệu cần biết", R.drawable.blog2_image_4, "14.02.2024", "1. Giảm lượng tinh bột trong khẩu phần ăn mỗi ngày. Đúng rằng con người không thể sống thiếu ...",2));
        blogs.add(new Blogs("Những lưu ý khi triệt sản chó cái", R.drawable.blog2_image_5, "17.02.2024", "1. Triệt sản là gì?Triệt sản (hay thiến) đây là một phẫu thuật loại bỏ cơ quan sinh dục của động vật. Việc này nhằm...",2));
        blogs.add(new Blogs("Cách xử lý vết thương khi bị chó cắn", R.drawable.blog2_image_6, "20.3.2024", "Hiện nay bệnh dại chưa có thuốc điều trị đặc hiệu. Xử lý vết thương khi bị chó cắn đúng cách và được tiêm vắc-xin...",2));
        blogs.add(new Blogs("Top 7 giống chó dễ nuôi nhất", R.drawable.blog_image, "20.3.2024", "1. Chó ChihuahuaChihuahua là giống chó đang được nuôi khá nhiều tại Việt Nam. Lý do dòng chó này được yêu chuộng một cách rộng...",2));
        blogAdapter = new BlogAdapter(blogs);
        binding.rclBlogDetails2.setAdapter(blogAdapter);
    }


}