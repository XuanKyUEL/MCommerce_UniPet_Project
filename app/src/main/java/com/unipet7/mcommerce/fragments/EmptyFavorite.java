package com.unipet7.mcommerce.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.activities.MainActivity;
import com.unipet7.mcommerce.databinding.FragmentEmptyFavoriteBinding;

public class EmptyFavorite extends Fragment {

    FragmentEmptyFavoriteBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEmptyFavoriteBinding.inflate(inflater, container, false);
        addEvents();
        return binding.getRoot();
    }

    private void addEvents() {
        binding.btnEmptyFav.setOnClickListener(v -> {
            MainActivity mainActivity = (MainActivity) getActivity();
            if (mainActivity != null) {
                mainActivity.mainViewPager2.setCurrentItem(0);
            }
        });
    }
}