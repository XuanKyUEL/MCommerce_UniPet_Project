package com.unipet7.mcommerce.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unipet7.mcommerce.activities.MainActivity;
import com.unipet7.mcommerce.adapters.ProductAdapter;
import com.unipet7.mcommerce.databinding.FragmentWishlistProductBinding;
import com.unipet7.mcommerce.firebase.FireStoreClass;
import com.unipet7.mcommerce.models.Product;
import com.unipet7.mcommerce.utils.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

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

    List<Integer> favList = new ArrayList<>();

    RecyclerView fvRecyclerView;

    FireStoreClass fireStoreClass = new FireStoreClass();

    LoadingDialog loadingDialog = new LoadingDialog();

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
        
        binding.btnEmptyFav.setOnClickListener(v -> {
            // TODO: go to home fragment which is the first fragment, implement the menu item click listener in main activity, position 0
            MainActivity mainActivity = (MainActivity) getActivity();
            if (mainActivity != null) {
                mainActivity.mainViewPager2.setCurrentItem(0);
            }
        });
        fetchFav();
        return binding.getRoot();
    }

    private void fetchFav() {
        fireStoreClass.getUserFavorites(this,favoriteList);

        if (favoriteList.isEmpty()) {
            fvRecyclerView.setVisibility(View.GONE);
            binding.emptyFav.setVisibility(View.VISIBLE);
            loadingDialog.dissmis();
        } else {
            loadFavoriteProducts(favoriteList);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // remove the previous data
        favoriteList.clear();
        fetchFav();
    }

    public void loadFavoriteProducts(ArrayList<Product> favProducts) {
        fvRecyclerView.setVisibility(View.VISIBLE);
        binding.emptyFav.setVisibility(View.GONE);
        loadingDialog.dissmis();
        fvAdapter = new ProductAdapter(favProducts);
        Log.d("TAG", "loadFavoriteProducts: "+ favProducts.size());
        fvRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        fvRecyclerView.setAdapter(fvAdapter);
        fvRecyclerView.setHasFixedSize(true);
    }
}