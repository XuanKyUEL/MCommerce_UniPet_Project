package com.unipet7.mcommerce.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.adapters.FavouriteAdapter;
import com.unipet7.mcommerce.adapters.ProductAdapter;
import com.unipet7.mcommerce.databinding.FragmentAccountBinding;
import com.unipet7.mcommerce.databinding.FragmentWishlistProductBinding;
import com.unipet7.mcommerce.models.Product;
import com.unipet7.mcommerce.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Wishlist_Product#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Wishlist_Product extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentWishlistProductBinding binding;
    FavouriteAdapter favouriteAdapter;
    ProductAdapter productAdapter;

    public Fragment_Wishlist_Product() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Wishlist_Product.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Wishlist_Product newInstance(String param1, String param2) {
        Fragment_Wishlist_Product fragment = new Fragment_Wishlist_Product();
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
        binding= FragmentWishlistProductBinding.inflate(inflater, container, false);
        addEvents();
        return binding.getRoot();
    }

    private void addEvents() {
        binding.favlist.setLayoutManager(new GridLayoutManager(getContext(), 2));
        productAdapter = new ProductAdapter(getFavoriteProductList());
        binding.favlist.setAdapter(productAdapter);
        binding.favlist.setHasFixedSize(false);
    }

    private List<Product> getFavoriteProductList() {
        List<Product> productList = new ArrayList<>();

        return productList;

    }

}