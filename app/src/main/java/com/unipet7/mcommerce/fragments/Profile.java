package com.unipet7.mcommerce.fragments;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.databinding.FragmentProfileBinding;

public class Profile extends Fragment {
    FragmentProfileBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        addEvents();
        return binding.getRoot();

    }

    private void addEvents() {
        binding.lnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO:Signout user
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}