package com.unipet7.mcommerce.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.adapters.ProductAdapter;
import com.unipet7.mcommerce.databinding.FragmentAllProductBinding;
import com.unipet7.mcommerce.databinding.FragmentHistoryOrdersBinding;
import com.unipet7.mcommerce.models.Product;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAllProduct#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAllProduct extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentAllProductBinding binding;
    ProductAdapter adapter;
    ArrayList<Product> products;

    public FragmentAllProduct() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAllProduct.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAllProduct newInstance(String param1, String param2) {
        FragmentAllProduct fragment = new FragmentAllProduct();
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
        binding = FragmentAllProductBinding.inflate(inflater, container, false);
        setActionBar(binding.toolbarall);
        initData();
        loadData();
        return binding.getRoot();

    }

    public void setActionBar(@Nullable Toolbar toolbar) {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back_profile);
        actionBar.setDisplayShowTitleEnabled(false);
    }

    private void initData() {
        products = new ArrayList<>();
        products.add(new Product(R.drawable.pate1, "Thức ăn mèo gâu gâu", 20000, 3, 3, 32, 20000));
        products.add(new Product(R.drawable.pate1, "Thức ăn mèo gâu gâu", 20000, 3, 3, 32, 20000));
        products.add(new Product(R.drawable.pate1, "Thức ăn mèo gâu gâu", 20000, 3, 3, 20, 30000));

        adapter = new ProductAdapter(products);
    }

    private void loadData() {
        binding.lvlAllProduct.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.lvlAllProduct.setAdapter(adapter);
        binding.lvlAllProduct.setHasFixedSize(true);
    }
}