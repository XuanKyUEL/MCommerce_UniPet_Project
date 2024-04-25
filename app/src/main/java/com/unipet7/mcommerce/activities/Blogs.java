package com.unipet7.mcommerce.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.adapters.BlogAdapter;
import com.unipet7.mcommerce.databinding.ActivityBlogsBinding;

import java.util.ArrayList;

public class Blogs extends AppCompatActivity {
    ActivityBlogsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBlogsBinding.inflate(getLayoutInflater());
        setActionBar(binding.toolbarblog);
        addEvents();
        recyclerViewBlog();
        recyclerViewBlog2();
        setContentView(binding.getRoot());
    }
    public void setActionBar(@Nullable Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_profile);
            actionBar.setDisplayShowTitleEnabled(false);
        }

    }
    private void addEvents() {
        binding.toolbarblog.setNavigationOnClickListener(v -> finish());
    }

    private void recyclerViewBlog2() {
        binding.rclBlogs2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ArrayList<com.unipet7.mcommerce.models.Blogs> blogs = new ArrayList<>();
        blogs.add(new com.unipet7.mcommerce.models.Blogs("5 tiêu chuẩn thức ăn cho mèo mà một Sen chính hiệu cần biết", R.drawable.blog2_image_4, "14.02.2024", "1. Giảm lượng tinh bột trong khẩu phần ăn mỗi ngày. Đúng rằng con người không thể sống thiếu ...",2));
        blogs.add(new com.unipet7.mcommerce.models.Blogs("Những lưu ý khi triệt sản chó cái", R.drawable.blog2_image_5, "17.02.2024", "1. Triệt sản là gì?Triệt sản (hay thiến) đây là một phẫu thuật loại bỏ cơ quan sinh dục của động vật. Việc này nhằm...",2));
        blogs.add(new com.unipet7.mcommerce.models.Blogs("Cách xử lý vết thương khi bị chó cắn", R.drawable.blog2_image_6, "20.3.2024", "Hiện nay bệnh dại chưa có thuốc điều trị đặc hiệu. Xử lý vết thương khi bị chó cắn đúng cách và được tiêm vắc-xin...",2));
        blogs.add(new com.unipet7.mcommerce.models.Blogs("Top 7 giống chó dễ nuôi nhất", R.drawable.blog2_image_7, "20.3.2024", "1. Chó ChihuahuaChihuahua là giống chó đang được nuôi khá nhiều tại Việt Nam. Lý do dòng chó này được yêu chuộng một cách rộng...",2));
        blogs.add(new com.unipet7.mcommerce.models.Blogs("Những bài học cuộc sống mà thú cưng dạy cho bạn", R.drawable.blog2_image_8, "20.3.2024", "Thú cưng không chỉ là một liều thuốc bổ cho tinh thần mà chúng còn dạy bạn rất nhiều bài học quý báu về cuộc...",2));
        RecyclerView.Adapter adapter2 = new BlogAdapter(blogs);
        binding.rclBlogs2.setAdapter(adapter2);
    }


    private void recyclerViewBlog() {
        binding.rclBlogs.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ArrayList<com.unipet7.mcommerce.models.Blogs> blogs = new ArrayList<>();
        blogs.add(new com.unipet7.mcommerce.models.Blogs("Top 7 giống chó dễ nuôi nhất", R.drawable.blog_image, "a", "a",1));
        blogs.add(new com.unipet7.mcommerce.models.Blogs("Mách bạn 8 lý do khiến mèo bỏ ăn", R.drawable.blog_image_1, "a", "a",1));
        blogs.add(new com.unipet7.mcommerce.models.Blogs("Những bài học cuộc sống mà thú cưng dạy cho bạn", R.drawable.blog_image_2, "a", "a",1));
        blogs.add(new com.unipet7.mcommerce.models.Blogs("Cách xử lý vết thương khi bị chó cắn", R.drawable.blog2_image_6, "20.3.2024", "Hiện nay bệnh dại chưa có thuốc điều trị đặc hiệu. Xử lý vết thương khi bị chó cắn đúng cách và được tiêm vắc-xin...",1));
        RecyclerView.Adapter<RecyclerView.ViewHolder> adapter = new BlogAdapter(blogs);
        binding.rclBlogs.setAdapter(adapter);

    }

}