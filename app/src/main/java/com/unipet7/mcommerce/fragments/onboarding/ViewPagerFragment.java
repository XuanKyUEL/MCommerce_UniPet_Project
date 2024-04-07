package com.unipet7.mcommerce.fragments.onboarding;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.databinding.FragmentViewPagerBinding;

import java.util.ArrayList;
import java.util.Objects;

public class ViewPagerFragment extends Fragment {

    private FragmentViewPagerBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentViewPagerBinding.inflate(inflater, container, false);

        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new OnBoardFirstScreen());
        fragmentList.add(new OnBoardSecondScreen());
        fragmentList.add(new OnBoardThirdScreen());

        ViewPagerAdapter adapter = new ViewPagerAdapter(fragmentList, requireActivity().getSupportFragmentManager(), requireActivity().getLifecycle());

        binding.viewPagerIntro.setAdapter(adapter);

        binding.dotsIndicator.attachTo(binding.viewPagerIntro);



        return binding.getRoot();
    }
}