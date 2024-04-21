package com.unipet7.mcommerce.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.adapters.BlogAdapter;
import com.unipet7.mcommerce.databinding.FragmentBlogBinding;
import com.unipet7.mcommerce.databinding.FragmentContactListBinding;
import com.unipet7.mcommerce.models.Blogs;

import java.util.ArrayList;


public class FragmentBlog extends Fragment {
    FragmentBlogBinding binding;
    private RecyclerView.Adapter adapter, adapter2;
    private RecyclerView recyclerViewBlog, recyclerViewBlog2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBlogBinding.inflate(inflater, container, false);
        setActionBar(binding.toolbar);
        recyclerViewBlog();
        recyclerViewBlog2();
        return binding.getRoot();    }
    public void setActionBar(@Nullable Toolbar toolbar) {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back_profile);
        actionBar.setDisplayShowTitleEnabled(false);
    }
    private void recyclerViewBlog2() {
        binding.rclBlogs2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        ArrayList<Blogs> blogs = new ArrayList<>();
        blogs.add(new Blogs("5 tiêu chuẩn thức ăn cho mèo mà một Sen chính hiệu cần biết", R.drawable.blog2_image_4, "14.02.2024", "1. Giảm lượng tinh bột trong khẩu phần ăn mỗi ngày. Đúng rằng con người không thể sống thiếu ...",2));
        blogs.add(new Blogs("Những lưu ý khi triệt sản chó cái", R.drawable.blog2_image_5, "17.02.2024", "1. Triệt sản là gì?Triệt sản (hay thiến) đây là một phẫu thuật loại bỏ cơ quan sinh dục của động vật. Việc này nhằm...",2));
        blogs.add(new Blogs("Cách xử lý vết thương khi bị chó cắn", R.drawable.blog2_image_6, "20.3.2024", "Hiện nay bệnh dại chưa có thuốc điều trị đặc hiệu. Xử lý vết thương khi bị chó cắn đúng cách và được tiêm vắc-xin...",2));
        adapter2 = new BlogAdapter(blogs);
        binding.rclBlogs2.setAdapter(adapter2);
    }


    private void recyclerViewBlog() {
        binding.rclBlogs.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        ArrayList<Blogs> blogs = new ArrayList<>();
        blogs.add(new Blogs("a", R.drawable.blog_image, "a", "a",1));
        blogs.add(new Blogs("a", R.drawable.blog_image_1, "a", "a",1));
        blogs.add(new Blogs("a", R.drawable.blog_image_2, "a", "a",1));
        adapter = new BlogAdapter(blogs);
        binding.rclBlogs.setAdapter(adapter);

    }

}