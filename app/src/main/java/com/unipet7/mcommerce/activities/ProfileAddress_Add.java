package com.unipet7.mcommerce.activities;

import androidx.annotation.NonNull;
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
import android.view.MenuItem;
import android.widget.TextView;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.databinding.ActivityProfileAddressAddBinding;
import com.unipet7.mcommerce.databinding.ActivityProfileAddressBinding;

public class ProfileAddress_Add extends AppCompatActivity {
    ActivityProfileAddressAddBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_address);
        binding = ActivityProfileAddressAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setActionBar(binding.toolbaradd);
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
        textView.setText("Thêm địa chỉ");
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
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Go back to the previous screen
        }
        return super.onOptionsItemSelected(item);
    }
}