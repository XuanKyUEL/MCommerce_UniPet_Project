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
import android.widget.ImageView;
import android.widget.TextView;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.adapters.BlogAdapter;
import com.unipet7.mcommerce.adapters.ProductAdapter;
import com.unipet7.mcommerce.databinding.FragmentBlogBinding;
import com.unipet7.mcommerce.databinding.FragmentBlogDetailsBinding;
import com.unipet7.mcommerce.models.Blogs;
import com.unipet7.mcommerce.models.Product;

import java.util.ArrayList;

public class FragmentBlogDetails extends Fragment {
    FragmentBlogDetailsBinding binding;
    BlogAdapter blogAdapter;
    ProductAdapter adapter;
    ArrayList<Product> products;
    public FragmentBlogDetails() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBlogDetailsBinding.inflate(inflater, container, false);
        addEvents();
        setActionBar(binding.toolbardetail);
        initBlog();
        initProduct();
        return binding.getRoot();     }



    private void addEvents() {
        binding.toolbardetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });
    }
    public void setActionBar(@Nullable Toolbar toolbar) {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back_profile);
        actionBar.setDisplayShowTitleEnabled(false);
    }
    private void initProduct() {
        binding.rclBlogDetails1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        products = new ArrayList<>();
        products.add(new Product(R.drawable.pate1, "Thức ăn mèo gâu gâu", 20000,3,3,32,20000));
        products.add(new Product(R.drawable.pate1, "Thức ăn mèo gâu gâu", 20000,3,3, 32, 20000));
        products.add(new Product(R.drawable.pate1, "Thức ăn mèo gâu gâu", 20000,3,3,20,30000));
        adapter = new ProductAdapter(products);
        binding.rclBlogDetails1.setAdapter(adapter);
    }
    private void initBlog() {
        binding.rclBlogDetails2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        ArrayList<Blogs> blogs = new ArrayList<>();
        blogs.add(new Blogs("5 tiêu chuẩn thức ăn cho mèo mà một Sen chính hiệu cần biết", R.drawable.blog2_image_4, "14.02.2024", "1. Giảm lượng tinh bột trong khẩu phần ăn mỗi ngày. Đúng rằng con người không thể sống thiếu ...",2));
        blogs.add(new Blogs("Những lưu ý khi triệt sản chó cái", R.drawable.blog2_image_5, "17.02.2024", "1. Triệt sản là gì?Triệt sản (hay thiến) đây là một phẫu thuật loại bỏ cơ quan sinh dục của động vật. Việc này nhằm...",2));
        blogs.add(new Blogs("Cách xử lý vết thương khi bị chó cắn", R.drawable.blog2_image_6, "20.3.2024", "Hiện nay bệnh dại chưa có thuốc điều trị đặc hiệu. Xử lý vết thương khi bị chó cắn đúng cách và được tiêm vắc-xin...",2));
        blogs.add(new Blogs("Top 7 giống chó dễ nuôi nhất", R.drawable.blog_image, "20.3.2024", "1. Chó ChihuahuaChihuahua là giống chó đang được nuôi khá nhiều tại Việt Nam. Lý do dòng chó này được yêu chuộng một cách rộng...",2));
        blogAdapter = new BlogAdapter(blogs);
        binding.rclBlogDetails2.setAdapter(blogAdapter);
    }
}
