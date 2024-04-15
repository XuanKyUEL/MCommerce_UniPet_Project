package com.unipet7.mcommerce.fragments.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.activities.SignIn;
import com.unipet7.mcommerce.databinding.FragmentOnBoardSecondScreenBinding;

public class OnBoardSecondScreen extends Fragment {

    FragmentOnBoardSecondScreenBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOnBoardSecondScreenBinding.inflate(inflater, container, false);
        binding.tvSkipIntroScreen2.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SignIn.class);
            startActivity(intent);
            requireActivity().finish();
        });


        return binding.getRoot();
    }
}