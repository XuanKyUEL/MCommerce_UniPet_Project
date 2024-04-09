package com.unipet7.mcommerce.fragments.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.activities.SignIn;
import com.unipet7.mcommerce.databinding.FragmentOnBoardThirdScreenBinding;

public class OnBoardThirdScreen extends Fragment {

    FragmentOnBoardThirdScreenBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOnBoardThirdScreenBinding.inflate(inflater, container, false);

        binding.btnIntroThirdScreen.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SignIn.class);
            startActivity(intent);
            Log.d("OnBoardThirdScreen", "Bắt đầu thôi đã được nhấn");
        });

        return binding.getRoot();
    }
}