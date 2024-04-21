package com.unipet7.mcommerce.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.databinding.FragmentRateOrderBinding;

public class FragmentRateOrder extends AppCompatActivity {
    FragmentRateOrderBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentRateOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvents();
    }

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
        this.setSupportActionBar(toolbar);
        ActionBar actionBar = this.getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back_profile);
        actionBar.setDisplayShowTitleEnabled(false);
    }
}