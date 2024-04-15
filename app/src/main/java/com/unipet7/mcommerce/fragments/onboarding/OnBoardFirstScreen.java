package com.unipet7.mcommerce.fragments.onboarding;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.activities.SignIn;
import com.unipet7.mcommerce.databinding.FragmentOnBoardFirstScreenBinding;

import java.util.Objects;

public class OnBoardFirstScreen extends Fragment {

    FragmentOnBoardFirstScreenBinding binding;
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOnBoardFirstScreenBinding.inflate(inflater, container, false);
        TextView tvSkip = binding.tvSkipIntroScreen1;
        tvSkip.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SignIn.class);
            startActivity(intent);
            requireActivity().finish();
        });
        return binding.getRoot();
    }
}