package com.unipet7.mcommerce.activities;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.adapters.MainViewPager2Adapter;
import com.unipet7.mcommerce.databinding.ActivityMainBinding;
import com.unipet7.mcommerce.fragments.FragmentAllProduct;
import com.unipet7.mcommerce.fragments.Fragment_Wishlist_Product;
import com.unipet7.mcommerce.fragments.Home;
import com.unipet7.mcommerce.fragments.Profile;
import com.unipet7.mcommerce.fragments.fragment_cart;
import com.unipet7.mcommerce.utils.Constants;
import com.unipet7.mcommerce.utils.LoadingDialog;
import com.unipet7.mcommerce.utils.NonSwipeAbleViewPager;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;

    private static final String TAG = "MainActivity";
    ActivityMainBinding binding;

    public ViewPager2 mainViewPager2;

    LoadingDialog loadingDialog;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton fabCart;
    private FirebaseFirestore db;

    Fragment fragment = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(TAG, "onCreate: ");
        setTheme(R.style.Base_Theme_UniPet);
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mapping();
        MainViewPager2Adapter adapter = new MainViewPager2Adapter(this);
        mainViewPager2.setAdapter(adapter);
        navigateFragment();
    }


    private void mapping() {
        bottomNavigationView = binding.bottomNavigationView;
        mainViewPager2 = binding.viewPagerMain;
        mainViewPager2.setUserInputEnabled(false);
        RecyclerView recyclerView = (RecyclerView) mainViewPager2.getChildAt(0);
        recyclerView.addOnItemTouchListener(new NonSwipeAbleViewPager());
        fabCart = binding.fabCart;
        db = FirebaseFirestore.getInstance();
    }


    private void navigateFragment() {
        mainViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        fragment = new Home();
                        bottomNavigationView.getMenu().getItem(0).setChecked(true);
                        break;

                    case 1:
                        fragment = new FragmentAllProduct();
                        // truyền bundle category allproduct cho fragment allproduct
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.CATEGORY, Constants.ALLPRODUCT);
                        fragment.setArguments(bundle);
                        bottomNavigationView.getMenu().getItem(1).setChecked(true);
                        break;
                    case 2:
                        fragment = new fragment_cart();
                        bottomNavigationView.getMenu().getItem(2).setChecked(true);
                        break;
                    case 3:
                        fragment = new Fragment_Wishlist_Product();
                        bottomNavigationView.getMenu().getItem(3).setChecked(true);
                        break;
                    case 4:
                        fragment = new Profile();
                        bottomNavigationView.getMenu().getItem(4).setChecked(true);
                        break;
                }
            }

        });
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.home_icon_bottom) {
                removeAllFragments();
                mainViewPager2.setCurrentItem(0);


            } else if (id == R.id.product_icon_bottom) {
                mainViewPager2.setCurrentItem(1);
            } else if (id == R.id.fab_nav_shop) {
                mainViewPager2.setCurrentItem(2);
            } else if (id == R.id.fav_icon_bottom) {
                mainViewPager2.setCurrentItem(3);
            } else if (id == R.id.account_icon_bottom) {
                mainViewPager2.setCurrentItem(4);
            }
            return true;
        });

        fabCart.setOnClickListener(v -> {
            mainViewPager2.setCurrentItem(2);
        });
    }
    private void removeAllFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager.executePendingTransactions();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home_icon_bottom);
    }




}
