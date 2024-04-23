package com.unipet7.mcommerce.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.activities.ProfileFunction;
import com.unipet7.mcommerce.adapters.BlogAdapter;
import com.unipet7.mcommerce.adapters.ProductAdapter;
import com.unipet7.mcommerce.adapters.SliderAdapter;
import com.unipet7.mcommerce.databinding.FragmentHomeBinding;
import com.unipet7.mcommerce.firebase.FireStoreClass;
import com.unipet7.mcommerce.models.Blogs;
import com.unipet7.mcommerce.models.Product;
import com.unipet7.mcommerce.models.SliderItems;
import com.unipet7.mcommerce.models.User;
import com.unipet7.mcommerce.utils.LoadingDialog;

import java.util.ArrayList;
import java.util.Objects;

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
    public ProductAdapter adapter;

    public ProductAdapter saleAdapter;
    public ProductAdapter productDog;
    public ProductAdapter productCat;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentHomeBinding binding = null;
    BlogAdapter blogAdapter;

    LoadingDialog loadingDialog;

    private static final String KEY_FLAG = "isLoadUser";
    boolean isLoadUser = false;

    public ArrayList<Product> products = new ArrayList<>();

    public ArrayList<Product> productsSale = new ArrayList<>();
    public ArrayList<Product> productsDog = new ArrayList<>();
    public ArrayList<Product> productsCat = new ArrayList<>();

    public ProductAdapter adapterSale;
    public ProductAdapter adapterDog;
    public ProductAdapter adapterCat;

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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_FLAG, isLoadUser);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        if (savedInstanceState != null) {
            isLoadUser = savedInstanceState.getBoolean(KEY_FLAG);
            Log.i("HomeFragment", "onCreate: " + isLoadUser);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (binding == null) {
            binding = FragmentHomeBinding.inflate(inflater, container, false);
        }
        loadBlog();
        addEvents();
        loadHomeUserAndProduct();
        initBanner();

        return binding.getRoot();
    }

    private void initBanner() {
        DatabaseReference myRef= FirebaseDatabase.getInstance().getReference("Banner");
        ArrayList<SliderItems> items = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot issue:snapshot.getChildren()){
                        items.add(issue.getValue(SliderItems.class));
                    }
                    banners(items);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void banners(ArrayList<SliderItems> items) {

        binding.viewpageSlider.setAdapter(new SliderAdapter(items, binding.viewpageSlider));
        binding.viewpageSlider.setClipToPadding(false);
        binding.viewpageSlider.setClipChildren(false);
        binding.viewpageSlider.setOffscreenPageLimit(2);
        binding.viewpageSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer= new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));

        binding.viewpageSlider.setPageTransformer(compositePageTransformer);
    }

    public void configAdapters() {

        saleAdapter = new ProductAdapter(productsSale);
        binding.lvlHomeSale.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.lvlHomeSale.setAdapter(saleAdapter);
        binding.lvlHomeSale.setHasFixedSize(true);

        productDog = new ProductAdapter(productsDog);
        binding.lvlHomeProduct1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.lvlHomeProduct1.setAdapter(productDog);
        binding.lvlHomeProduct1.setHasFixedSize(true);

        productCat = new ProductAdapter(productsCat);
        binding.lvlHomeProduct2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.lvlHomeProduct2.setAdapter(productCat);
        binding.lvlHomeProduct2.setHasFixedSize(true);
    }

    private void loadHomeUserAndProduct() {
        loadingDialog = new LoadingDialog();
        loadingDialog.showLoadingDialog(getContext());
        FireStoreClass fireStoreClass = new FireStoreClass();
        fireStoreClass.getProductList(this, productsSale, productsDog, productsCat);
        fireStoreClass.loadLoggedUserUI(this);
    }

    private void addEvents() {
        binding.txtXemThem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment allproduct = new FragmentAllProduct();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                // Thực hiện giao diện chuyển đổi Fragment
                fragmentManager.beginTransaction()
                        .replace(((ViewGroup) requireView().getParent()).getId(), allproduct)
                        .addToBackStack(null)
                        .commit();
            }
        });
        binding.txtXemThem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment allproduct = new FragmentAllProduct();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                // Thực hiện giao diện chuyển đổi Fragment
                fragmentManager.beginTransaction()
                        .replace(((ViewGroup) requireView().getParent()).getId(), allproduct)
                        .addToBackStack(null)
                        .commit();
            }
        });
        binding.txtXemthem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment allproduct = new FragmentAllProduct();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                // Thực hiện giao diện chuyển đổi Fragment
                fragmentManager.beginTransaction()
                        .replace(((ViewGroup) requireView().getParent()).getId(), allproduct)
                        .addToBackStack(null)
                        .commit();
            }
        });
        binding.Xemthem4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragmentBlog = new FragmentBlog();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                // Thực hiện giao diện chuyển đổi Fragment
                fragmentManager.beginTransaction()
                        .replace(((ViewGroup) requireView().getParent()).getId(), fragmentBlog)
                        .addToBackStack(null)
                        .commit();
            }
        });
        binding.imgCate6.setOnClickListener(v -> {
            Fragment fragmentBlog = new FragmentBlog();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

            // Thực hiện giao diện chuyển đổi Fragment
            fragmentManager.beginTransaction()
                    .replace(((ViewGroup) requireView().getParent()).getId(), fragmentBlog)
                    .addToBackStack(null)
                    .commit();
        });
        binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), fragment_notification.class);
                intent.putExtra("function", "notification");
                startActivity(intent);

            }
        });
    }


    private void loadBlog() {
        binding.homeblog.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        ArrayList<Blogs> blogs = new ArrayList<>();
        blogs.add(new Blogs("5 tiêu chuẩn thức ăn cho mèo mà một Sen chính hiệu cần biết", R.drawable.blog2_image_4, "14.02.2024", "1. Giảm lượng tinh bột trong khẩu phần ăn mỗi ngày. Đúng rằng con người không thể sống thiếu ...",2));
        blogs.add(new Blogs("Những lưu ý khi triệt sản chó cái", R.drawable.blog2_image_5, "17.02.2024", "1. Triệt sản là gì?Triệt sản (hay thiến) đây là một phẫu thuật loại bỏ cơ quan sinh dục của động vật. Việc này nhằm...",2));
        blogs.add(new Blogs("Cách xử lý vết thương khi bị chó cắn", R.drawable.blog2_image_6, "20.3.2024", "Hiện nay bệnh dại chưa có thuốc điều trị đặc hiệu. Xử lý vết thương khi bị chó cắn đúng cách và được tiêm vắc-xin...",2));
        blogAdapter = new BlogAdapter(blogs);
        binding.homeblog.setAdapter(blogAdapter);
    }

    public void greeting(User user) {
        binding.txtUserName.setText("Xin chào " + user.getName());
        loadingDialog.dissmis();
    }

}