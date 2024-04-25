package com.unipet7.mcommerce.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unipet7.mcommerce.adapters.ProductAdapter;
import com.unipet7.mcommerce.databinding.FragmentWishlistProductBinding;
import com.unipet7.mcommerce.firebase.FireStoreClass;
import com.unipet7.mcommerce.models.Product;

import java.util.ArrayList;

public class Fragment_Wishlist_Product extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentWishlistProductBinding binding;
    public ProductAdapter fvAdapter;

    ArrayList<Product> favoriteList = new ArrayList<>();

    RecyclerView fvRecyclerView;

    FireStoreClass fireStoreClass = new FireStoreClass();

    public Fragment_Wishlist_Product() {
        // Required empty public constructor
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
        binding= FragmentWishlistProductBinding.inflate(inflater, container, false);
        fvRecyclerView = binding.favlist;
        fireStoreClass.getUserFavorites(this,favoriteList);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        favoriteList.clear();
        fireStoreClass.getUserFavorites(this,favoriteList);
    }

    public void loadFavoriteProducts(ArrayList<Product> favProducts) {
        fvAdapter = new ProductAdapter(favProducts);
        fvRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        fvRecyclerView.setAdapter(fvAdapter);
        fvRecyclerView.setHasFixedSize(true);
    }
}