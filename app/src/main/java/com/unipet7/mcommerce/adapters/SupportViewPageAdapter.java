package com.unipet7.mcommerce.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.unipet7.mcommerce.fragments.ContactList;
import com.unipet7.mcommerce.fragments.Question;

public class SupportViewPageAdapter extends FragmentStateAdapter {

    public SupportViewPageAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new ContactList();
        }
        return new Question();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
