package com.unipet7.mcommerce.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unipet7.mcommerce.adapters.ProductAdapter;
import com.unipet7.mcommerce.databinding.FragmentFavoriteListBinding;
import com.unipet7.mcommerce.firebase.FireStoreClass;
import com.unipet7.mcommerce.models.Product;
import com.unipet7.mcommerce.utils.Constants;
import com.unipet7.mcommerce.utils.LoadingDialog;
import com.unipet7.mcommerce.utils.OnRemoveAllFavHelper;

import java.util.ArrayList;
import java.util.List;

public class FavoriteList extends Fragment {

    FragmentFavoriteListBinding binding;

    public ProductAdapter fvAdapter;

    ArrayList<Product> favoriteList = new ArrayList<>();

    List<Integer> favList = new ArrayList<>();

    RecyclerView fvRecyclerView;

    FireStoreClass fireStoreClass = new FireStoreClass();

    LoadingDialog loadingDialog = new LoadingDialog();

    private OnRemoveAllFavHelper onRemoveFavHelper;

    Bundle bundle;

    private boolean loadFav = false;

    public FavoriteList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteListBinding.inflate(inflater, container, false);
        this.fvRecyclerView = binding.favlist;
        this.bundle = getArguments();
        if (bundle != null) {
            loadFav = bundle.getBoolean(Constants.LOADFAV, false);
        }
        fireStoreClass.getUserFavorites(this,favoriteList);
        return binding.getRoot();
    }

    public void loadFavoriteProducts(ArrayList<Product> favProducts) {
        fvRecyclerView.setVisibility(View.VISIBLE);
        loadingDialog.dissmis();
        fvAdapter = new ProductAdapter(favProducts,fireStoreClass, true);
        Log.d("TAG", "loadFavoriteProducts: "+ favProducts.size());
        fvRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        fvRecyclerView.setAdapter(fvAdapter);
        fvRecyclerView.setHasFixedSize(true);
    }
}