package com.unipet7.mcommerce.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.databinding.FragmentFavoriteOverallBinding;
import com.unipet7.mcommerce.firebase.FireStoreClass;
import com.unipet7.mcommerce.utils.Constants;
import com.unipet7.mcommerce.utils.OnRemoveAllFavHelper;

public class FavoriteOverall extends Fragment implements OnRemoveAllFavHelper {

    public boolean loadFavoriteCount;
    FragmentFavoriteOverallBinding binding;

    FireStoreClass fireStoreClass = new FireStoreClass();

    public FavoriteOverall() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoriteOverallBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        fireStoreClass.countFavorite(this);
        return binding.getRoot();
    }

    public void loadUi(boolean loadFavoriteCount) {
        this.loadFavoriteCount = loadFavoriteCount;
        Fragment fragment;
        if (loadFavoriteCount) {
            fragment = new FavoriteList();
            Bundle bundle = new Bundle();
            bundle.putBoolean(Constants.LOADFAV, loadFavoriteCount);
            fragment.setArguments(bundle);
        } else {
            fragment = new EmptyFavorite();
        }
        getChildFragmentManager().beginTransaction().replace(R.id.fragment_favorite_container, fragment).commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        fireStoreClass.countFavorite(this);
    }

    @Override
    public void onRemoveFav(boolean removeFav) {
        if (removeFav) {
            loadUi(false);
        }
    }
}