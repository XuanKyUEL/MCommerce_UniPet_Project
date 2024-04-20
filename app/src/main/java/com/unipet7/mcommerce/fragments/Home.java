package com.unipet7.mcommerce.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.adapters.BlogAdapter;
import com.unipet7.mcommerce.adapters.HistoryOrderAdapter;
import com.unipet7.mcommerce.adapters.ProductAdapter;
import com.unipet7.mcommerce.databinding.FragmentConfirmationOrderBinding;
import com.unipet7.mcommerce.databinding.FragmentHomeBinding;
import com.unipet7.mcommerce.firebase.FireStoreClass;
import com.unipet7.mcommerce.models.Blogs;
import com.unipet7.mcommerce.models.HistoryOrders;
import com.unipet7.mcommerce.models.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentHomeBinding binding;
    ProductAdapter adapter;
    ArrayList<Product> products;
    BlogAdapter blogAdapter;

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false);
        initData();
        loadData();
        loadBlog();
        gretting();
        addEvents();

        return binding.getRoot();
    }

    private void gretting() {
        TextView v = binding.txtUserName;
        FireStoreClass fireStoreClass = new FireStoreClass();
        fireStoreClass.checkLoggedUser(user -> {
            v.setText("Hi " + user.getName());
        });
    }
    private void addEvents() {
        binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment notyfragment = new Fragment_Empty_Notification();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                // Thực hiện giao diện chuyển đổi Fragment
                fragmentManager.beginTransaction()
                        .replace(((ViewGroup) requireView().getParent()).getId(), notyfragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }


    private void loadBlog() {
        binding.homeblog.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        ArrayList<Blogs> blogs = new ArrayList<>();
        blogs.add(new Blogs("5 tiêu chuẩn thức ăn cho mèo mà một Sen chính hiệu cần biết", "blog_4", "14.02.2024", "1. Giảm lượng tinh bột trong khẩu phần ăn mỗi ngày. Đúng rằng con người không thể sống thiếu ..."));
        blogs.add(new Blogs("Những lưu ý khi triệt sản chó cái", "blog_5", "17.02.2024", "1. Triệt sản là gì?Triệt sản (hay thiến) đây là một phẫu thuật loại bỏ cơ quan sinh dục của động vật. Việc này nhằm..."));
        blogs.add(new Blogs("Cách xử lý vết thương khi bị chó cắn", "blog_6", "20.3.2024", "Hiện nay bệnh dại chưa có thuốc điều trị đặc hiệu. Xử lý vết thương khi bị chó cắn đúng cách và được tiêm vắc-xin..."));
        blogAdapter = new BlogAdapter(blogs,2);
        binding.homeblog.setAdapter(blogAdapter);
    }

    private void initData() {
        products = new ArrayList<>();
        products.add(new Product(R.drawable.pate1, "Thức ăn mèo gâu gâu", 20000,3,3,32,20000));
        products.add(new Product(R.drawable.pate1, "Thức ăn mèo gâu gâu", 20000,3,3, 32, 20000));
        products.add(new Product(R.drawable.pate1, "Thức ăn mèo gâu gâu", 20000,3,3,20,30000));

        adapter = new ProductAdapter(products);
    }

    private void loadData() {
        binding.lvlHomeSale.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.lvlHomeSale.setAdapter(adapter);
        binding.lvlHomeSale.setHasFixedSize(true);
        binding.lvlHomeProduct1.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.lvlHomeProduct1.setAdapter(adapter);
        binding.lvlHomeProduct1.setHasFixedSize(true);
        binding.lvlHomeProduct2.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.lvlHomeProduct2.setAdapter(adapter);
        binding.lvlHomeProduct2.setHasFixedSize(true);
    }
}