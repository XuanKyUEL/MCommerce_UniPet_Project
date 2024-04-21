package com.unipet7.mcommerce.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.unipet7.mcommerce.fragments.FragmentAllProduct;
import com.unipet7.mcommerce.fragments.Fragment_Empty_Notification;
import com.unipet7.mcommerce.fragments.Home;
import com.unipet7.mcommerce.fragments.Profile;
import com.unipet7.mcommerce.fragments.fragment_blank_cart;
import com.unipet7.mcommerce.fragments.fragment_notification;

public class MainViewPager2Adapter extends FragmentStateAdapter {
    public MainViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Home();
            case 1:
                return new FragmentAllProduct();
            case 2:
                return new fragment_blank_cart();
            case 3:
                return new Fragment_Empty_Notification();
            case 4:
                return new Profile();
            default:
                return new Home();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
