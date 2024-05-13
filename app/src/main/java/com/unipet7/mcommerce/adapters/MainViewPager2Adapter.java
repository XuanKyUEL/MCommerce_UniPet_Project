package com.unipet7.mcommerce.adapters;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.unipet7.mcommerce.fragments.CartOverall;
import com.unipet7.mcommerce.fragments.FragmentAllProduct;
import com.unipet7.mcommerce.fragments.FavoriteOverall;
import com.unipet7.mcommerce.fragments.Home;
import com.unipet7.mcommerce.fragments.Profile;

public class MainViewPager2Adapter extends FragmentStateAdapter {
    public MainViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        return switch (position) {
            case 1 -> new FragmentAllProduct();
            case 2 -> new CartOverall();
            case 3 -> new FavoriteOverall();
            case 4 -> new Profile();
            default -> new Home();
        };
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
