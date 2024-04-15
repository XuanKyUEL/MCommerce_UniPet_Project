package com.unipet7.mcommerce.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.activities.SignIn;
import com.unipet7.mcommerce.adapters.IntroViewPagerAdapter;
import com.unipet7.mcommerce.databinding.ActivityIntroBinding;
import com.unipet7.mcommerce.models.IntroLayoutItems;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    ActivityIntroBinding binding;

    private ViewPager2 viewPager2;
    IntroViewPagerAdapter introViewPagerAdapter;

    ArrayList<IntroLayoutItems> introList;

    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initScreens();
        loadScreens();
        moveNext();
        swipeToLastScreen();
        naviGate();
    }

    private void naviGate() {
        binding.buttonGetStarted.setOnClickListener(v -> {
            moveToSignIn();
        });
        binding.tvSkipIntro.setOnClickListener(v -> {
            moveToSignIn();
        });
    }

    private void moveToSignIn() {
        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);
        finish();
    }

    private void swipeToLastScreen() {
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == introList.size() - 1) {
                    loadLastScreen();
                } else {
                    binding.buttonNextIntro.setVisibility(View.VISIBLE);
                    binding.tvSkipIntro.setVisibility(View.VISIBLE);
                    binding.dotsIndicator.setVisibility(View.VISIBLE);
                    binding.buttonGetStarted.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void moveNext() {
        binding.buttonNextIntro.setOnClickListener(v -> {
            int positon = viewPager2.getCurrentItem();
            if (positon < introList.size()) {
                positon++;
                viewPager2.setCurrentItem(positon);
            } if (positon == introList.size() - 1) {
                loadLastScreen();
            }
        });
    }

    private void loadLastScreen() {
        Log.d("IntroActivity", "loadLastScreen: " + viewPager2.getCurrentItem());
        binding.buttonNextIntro.setVisibility(View.INVISIBLE);
        binding.tvSkipIntro.setVisibility(View.INVISIBLE);
        binding.dotsIndicator.setVisibility(View.INVISIBLE);
        binding.buttonGetStarted.setVisibility(View.VISIBLE);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.getstarted_animation);
        binding.buttonGetStarted.setAnimation(animation);
        // render animation for get started button
    }

    private void initScreens() {
        introList = new ArrayList<>();
        introList.add(new IntroLayoutItems(getResources().getString(R.string.intro_title_1),
                getResources().getString(R.string.content_intro_01)
                , R.drawable.intro_logo));
        introList.add(new IntroLayoutItems(getResources().getString(R.string.intro_title_2),
                getResources().getString(R.string.content_intro_02)
                , R.drawable.intro_logo));
        introList.add(new IntroLayoutItems(getResources().getString(R.string.intro_title_3),
                getResources().getString(R.string.content_intro_03)
                , R.drawable.intro_logo));
        introViewPagerAdapter = new IntroViewPagerAdapter(introList);
    }

    private void loadScreens() {
        viewPager2 = binding.viewPager2;
        viewPager2.setAdapter(introViewPagerAdapter);
        binding.dotsIndicator.attachTo(viewPager2);
    }
}