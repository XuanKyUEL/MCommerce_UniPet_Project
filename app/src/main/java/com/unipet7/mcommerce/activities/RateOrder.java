package com.unipet7.mcommerce.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.Gravity;
import android.widget.RatingBar;
import android.widget.TextView;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.databinding.ActivityHistoryOrderBinding;
import com.unipet7.mcommerce.databinding.ActivityRateOrderBinding;

public class RateOrder extends AppCompatActivity {
    ActivityRateOrderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRateOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvents();
        setActionBar(binding.toolbar1);
    }
    public void setActionBar(@Nullable Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        // Set icon arrow
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        // Create a TextView programmatically.
        TextView textView = new TextView(this);
        textView.setText("Đánh giá sản phẩm");
        textView.setTextColor(Color.BLACK);
        // Set text style using TextAppearanceSpan.
        SpannableString spannableString = new SpannableString(textView.getText());
        spannableString.setSpan(new TextAppearanceSpan(this, R.style.HEAD5_BOLD_18), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);

        // Set layout parameters for TextView.
        Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        textView.setLayoutParams(layoutParams);

        actionBar.setCustomView(textView);
        actionBar.setDisplayShowCustomEnabled(true);

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
}