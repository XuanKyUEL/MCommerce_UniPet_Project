package com.unipet7.mcommerce.fragments.onboarding;

import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unipet7.mcommerce.R;

public class Splash extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        new Handler().postDelayed(() -> {
            NavHostFragment.findNavController(Splash.this)
                    .navigate(R.id.action_splash_to_viewPagerFragment);
            Log.d("Splash", "ViewPagerFragment");
        }, 3000);
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }
}