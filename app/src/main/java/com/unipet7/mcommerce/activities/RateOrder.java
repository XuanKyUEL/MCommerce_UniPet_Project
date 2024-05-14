package com.unipet7.mcommerce.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.adapters.MessageDialogAdapter;
import com.unipet7.mcommerce.databinding.ActivityRateOrderBinding;
import com.unipet7.mcommerce.models.MessageDialog;

public class RateOrder extends AppCompatActivity {
    ActivityRateOrderBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRateOrderBinding.inflate(getLayoutInflater());
        setActionBar(binding.toolbar1);
        addEvents();
        addEvents1();
        addEvents2();
        setContentView(binding.getRoot());

    }

    private void addEvents2() {
            binding.toolbar1.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

    private void addEvents1() {
        binding.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isRatingBarsSelected = isRatingBarsSelected();
                boolean isEditTextFilled = isEditTextFilled();

                if (isRatingBarsSelected && isEditTextFilled) {
                    MessageDialogAdapter messageDialogAdapter = new MessageDialogAdapter(RateOrder.this);
                    MessageDialog messageDialog = new MessageDialog("Đánh giá thành công", "Bạn đã đánh giá thành công", "Quay lại", "Đóng");
                    messageDialog.setCancelable(true);
                    messageDialog.setNegativeClickListener(v1 -> {
                        messageDialogAdapter.dismissDialog();
                        // Quay lại trang Home
                        Intent intent = new Intent(RateOrder.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    });
                    messageDialog.setPositiveClickListener(v1 -> {
                        messageDialogAdapter.dismissDialog();
                        // Quay lại Fragment trước đó
                        finish();
                    });
                    messageDialogAdapter.showDialog(messageDialog);
                } else {
                    // Hiển thị thông báo khi chưa chọn đủ rating bar hoặc nhập liệu
                    Toast.makeText(RateOrder.this, "Vui lòng chọn đủ rating và nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean isRatingBarsSelected() {
         if (binding.ratingbar1.getRating() > 0 && binding.ratingbar2.getRating() > 0 && binding.ratingbar3.getRating() > 0) {
             return true;
         } else {
             return false;
         }
    }

    private boolean isEditTextFilled() {
         String editTextValue = binding.edtRating.getText().toString();
         if (!TextUtils.isEmpty(editTextValue)) {
             return true;
         } else {
             return false;
         }
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
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_profile);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

}