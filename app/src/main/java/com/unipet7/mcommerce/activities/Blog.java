package com.unipet7.mcommerce.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.Gravity;
import android.widget.TextView;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.adapters.BlogAdapter;
import com.unipet7.mcommerce.databinding.ActivityBlogBinding;
import com.unipet7.mcommerce.models.Blogs;

import java.util.ArrayList;

public class Blog extends AppCompatActivity {
    ActivityBlogBinding binding;
    private RecyclerView.Adapter adapter, adapter2;
    private RecyclerView recyclerViewBlog, recyclerViewBlog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBlogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setActionBar(binding.toolbar);
        recyclerViewBlog();
        recyclerViewBlog2();
    }

    private void recyclerViewBlog2() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewBlog2 = findViewById(R.id.rclBlogs2);
        recyclerViewBlog2.setLayoutManager(linearLayoutManager);

        ArrayList<Blogs> blogs = new ArrayList<>();
        blogs.add(new Blogs("5 tiêu chuẩn thức ăn cho mèo mà một Sen chính hiệu cần biết", "blog_4", "14.02.2024", "1. Giảm lượng tinh bột trong khẩu phần ăn mỗi ngày. Đúng rằng con người không thể sống thiếu ..."));
        blogs.add(new Blogs("Những lưu ý khi triệt sản chó cái", "blog_5", "17.02.2024", "1. Triệt sản là gì?Triệt sản (hay thiến) đây là một phẫu thuật loại bỏ cơ quan sinh dục của động vật. Việc này nhằm..."));
        blogs.add(new Blogs("Cách xử lý vết thương khi bị chó cắn", "blog_6", "20.3.2024", "Hiện nay bệnh dại chưa có thuốc điều trị đặc hiệu. Xử lý vết thương khi bị chó cắn đúng cách và được tiêm vắc-xin..."));

        adapter2 = new BlogAdapter(blogs, 2);
        recyclerViewBlog2.setAdapter(adapter2);
    }


    private void recyclerViewBlog() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewBlog = findViewById(R.id.rclBlogs);
        recyclerViewBlog.setLayoutManager(linearLayoutManager);
        ArrayList<Blogs> blogs = new ArrayList<>();
        blogs.add(new Blogs("a", "blog_1", "a", "a"));
        blogs.add(new Blogs("a", "blog_2", "a", "a"));
        blogs.add(new Blogs("a", "blog_3", "a", "a"));

        adapter = new BlogAdapter(blogs, 1);
        recyclerViewBlog.setAdapter(adapter);

    }

    public void setActionBar(@Nullable Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        // Set icon arrow
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back_profile);
        actionBar.setDisplayShowTitleEnabled(false);
    }
}