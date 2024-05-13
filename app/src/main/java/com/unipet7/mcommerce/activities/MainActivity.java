package com.unipet7.mcommerce.activities;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.adapters.MainViewPager2Adapter;
import com.unipet7.mcommerce.databinding.ActivityMainBinding;
import com.unipet7.mcommerce.fragments.CartOverall;
import com.unipet7.mcommerce.fragments.FragmentAllProduct;
import com.unipet7.mcommerce.fragments.FavoriteOverall;
import com.unipet7.mcommerce.fragments.Home;
import com.unipet7.mcommerce.fragments.Profile;
import com.unipet7.mcommerce.utils.Constants;
import com.unipet7.mcommerce.utils.NonSwipeAbleViewPager;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;

    private static final String TAG = "MainActivity";
    ActivityMainBinding binding;


    public ViewPager2 mainViewPager2;

    public BottomNavigationView bottomNavigationView;
    FloatingActionButton fabCart;

    Fragment fragment = null;

    private boolean doubleBackToExitPressedOnce = false;
    private final Runnable doubleBackToExitRunnable = () -> doubleBackToExitPressedOnce = false;


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

        boolean isFromProductDetail = getIntent().getBooleanExtra(Constants.FROM_PRODUCT_DETAIL, false);
        boolean orderSuccess = getIntent().getBooleanExtra(Constants.ODERSUCCESS, false);

        if (isFromProductDetail || orderSuccess) {
            mainViewPager2.setCurrentItem(2);
        }

        final OnBackPressedDispatcher dispatcher = getOnBackPressedDispatcher();
        dispatcher.addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (doubleBackToExitPressedOnce) {
                    setEnabled(false);
                    dispatcher.onBackPressed();
                    return;
                }

                doubleBackToExitPressedOnce = true;
                Toast.makeText(MainActivity.this, "Nhấn lần nữa để thoát", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(doubleBackToExitRunnable, 2000);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new Handler().removeCallbacks(doubleBackToExitRunnable);
    }

    private void mapping() {
        bottomNavigationView = binding.bottomNavigationView;
        mainViewPager2 = binding.viewPagerMain;
        mainViewPager2.setUserInputEnabled(false);
        RecyclerView recyclerView = (RecyclerView) mainViewPager2.getChildAt(0);
        recyclerView.addOnItemTouchListener(new NonSwipeAbleViewPager());
        fabCart = binding.fabCart;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
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
                        // truyền bundle category all product cho fragment allproduct
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.CATEGORY, Constants.ALLPRODUCT);
                        fragment.setArguments(bundle);
                        bottomNavigationView.getMenu().getItem(1).setChecked(true);
                        break;
                    case 2:
                        fragment = new CartOverall();
                        // set other menu item to false
                        bottomNavigationView.getMenu().getItem(2).setChecked(true);
                        break;
                    case 3:
                        fragment = new FavoriteOverall();
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

}
