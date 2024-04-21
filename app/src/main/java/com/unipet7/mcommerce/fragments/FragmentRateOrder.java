package com.unipet7.mcommerce.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.databinding.FragmentBlogBinding;
import com.unipet7.mcommerce.databinding.FragmentRateOrderBinding;

public class FragmentRateOrder extends Fragment {
    FragmentRateOrderBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRateOrderBinding.inflate(inflater, container, false);
        setActionBar(binding.toolbar1);
        addEvents();
        return binding.getRoot();     }

    private void addEvents() {
        binding.ratingbar1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating == 0.5 || rating ==1) {
                    binding.txtFB1.setText("Rất không hài lòng");
                }
                else if (rating == 1.5 || rating == 2) {
                    binding.txtFB1.setText("Không hài lòng");
                }
                else if (rating == 2.5 ||  rating == 3) {
                    binding.txtFB1.setText("Bình thường");
                }
                else if (rating == 3.5 || rating == 4) {
                    binding.txtFB1.setText("Hài lòng");
                }
                else {
                    binding.txtFB1.setText("Rất hài lòng");
                }
            }
        });
        binding.ratingbar2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating == 0.5 || rating ==1) {
                    binding.txtFB2.setText("Rất không hài lòng");
                }
                else if (rating == 1.5 || rating == 2) {
                    binding.txtFB2.setText("Không hài lòng");
                }
                else if (rating == 2.5 ||  rating == 3) {
                    binding.txtFB2.setText("Bình thường");
                }
                else if (rating == 3.5 || rating == 4) {
                    binding.txtFB2.setText("Hài lòng");
                }
                else {
                    binding.txtFB2.setText("Rất hài lòng");
                }
            }
        });
        binding.ratingbar3.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating == 0.5 || rating ==1) {
                    binding.txtFB3.setText("Rất không hài lòng");
                }
                else if (rating == 1.5 || rating == 2) {
                    binding.txtFB3.setText("Không hài lòng");
                }
                else if (rating == 2.5 ||  rating == 3) {
                    binding.txtFB3.setText("Bình thường");
                }
                else if (rating == 3.5 || rating == 4) {
                    binding.txtFB3.setText("Hài lòng");
                }
                else {
                    binding.txtFB3.setText("Rất hài lòng");
                }
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
}