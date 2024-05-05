package com.unipet7.mcommerce.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.activities.MainActivity;
import com.unipet7.mcommerce.adapters.ProductAdapter;
import com.unipet7.mcommerce.databinding.FragmentAllProductBinding;
import com.unipet7.mcommerce.firebase.FireStoreClass;
import com.unipet7.mcommerce.models.Product;
import com.unipet7.mcommerce.utils.Constants;
import com.unipet7.mcommerce.utils.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAllProduct#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAllProduct extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private boolean isAllProductFetched = false;
    public static final String KEY_FLAG = "isAllProductFetched";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentAllProductBinding binding;
    public ProductAdapter allPdadapter;
    public ArrayList<Product> allProducts;

    FireStoreClass fireStoreClass = new FireStoreClass();
    private String sKey;

    public void SetId(String id) {
        sKey = id;
    }
    List<Product> searchResults = new ArrayList<>();
//    ProductAdapter adapter;
private String currentCategory = Constants.ALLPRODUCT;

    ArrayList<Button> categoryButtons = new ArrayList<>();

    ArrayList<Product> foodProducts = new ArrayList<>();

    ArrayList<Product> toyProducts = new ArrayList<>();

    ArrayList<Product> healthProducts = new ArrayList<>();

    ArrayList<Product> itemProducts = new ArrayList<>();

    Button btnAll, btnFood, btnItem, btnCare, btnToy;

    public FragmentAllProduct() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAllProduct.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAllProduct newInstance(String param1, String param2) {
        FragmentAllProduct fragment = new FragmentAllProduct();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static FragmentAllProduct newInstance(String category) {
        FragmentAllProduct fragment = new FragmentAllProduct();
        Bundle args = new Bundle();
        args.putString("category", category);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        if (savedInstanceState != null) {
            isAllProductFetched = savedInstanceState.getBoolean(KEY_FLAG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAllProductBinding.inflate(inflater, container, false);
        mapping();
        LoadingDialog ldDialog = new LoadingDialog();
        ldDialog.showLoadingDialog(this.getContext());
        FireStoreClass fireStoreClass = new FireStoreClass();
        allProducts = new ArrayList<>();
        fireStoreClass.getAllProducts(this, allProducts);
        ldDialog.dissmis();
        loadProduct(allProducts);
        searchproduct();
        cateClickEvent();
        Bundle bundle = getArguments();
        if (bundle != null) {
            String category = bundle.getString(Constants.CATEGORY);
            assert category != null;
            loadProductOnCategory(category);
        } else {
            binding.btnAll.performClick();
        }
        return binding.getRoot();

    }

    private void searchproduct() {
        ProductAdapter adapter = new ProductAdapter(new ArrayList<>(), fireStoreClass);

        binding.searchallproduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = s.toString().trim().toLowerCase();
                searchResults.clear();

                ArrayList<Product> searchList;
                if (currentCategory.equals(Constants.FOOD)) {
                    searchList = foodProducts;
                }
                else if (currentCategory.equals(Constants.ITEM)){
                    searchList = itemProducts;
                }
                else if (currentCategory.equals(Constants.HEALTH)){
                    searchList = healthProducts;
                }
                else if (currentCategory.equals(Constants.TOY)){
                    searchList = toyProducts;
                } else searchList = allProducts;

                for (Product product : searchList) {
                    if (product.getProductname().toLowerCase().contains(keyword)) {
                        searchResults.add(product);
                    }
                }
                adapter.setData(searchResults);
                adapter.notifyDataSetChanged();
                binding.lvlAllProduct.setAdapter(adapter);

            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void mapping() {
        btnAll = binding.btnAll;
        btnFood = binding.btnFood;
        btnItem = binding.btnItem;
        btnCare = binding.btnCare;
        btnToy = binding.btnToy;
    }

    private void loadProductOnCategory(String category) {
        currentCategory = category;
        switch (category) {
            case Constants.FOOD:
                loadProduct(foodProducts);
                break;
            case Constants.ITEM:
                loadProduct(itemProducts);
                break;
            case Constants.HEALTH:
                loadProduct(healthProducts);
                break;
            case Constants.TOY:
                loadProduct(toyProducts);
                break;
            case Constants.ALLPRODUCT:
                loadProduct(allProducts);
                break;
        }
    }

    public void buttonUIDefault(ArrayList<Button> buttons) {
        for (Button button : buttons) {
            button.setBackgroundResource(R.drawable.btn_bg_outline);
            button.setTextColor(getResources().getColor(R.color.black));
        }
    }

    public void divideProduct() {
        for (Product product : allProducts) {
            if (product.getCategoryId() == 1 || product.getCategoryId() == 2) {
                foodProducts.add(product);
            }
            if (product.getCategoryId() == 3) {
                itemProducts.add(product);
            }
            if (product.getCategoryId() == 4) {
                healthProducts.add(product);
            }
            if (product.getCategoryId() == 5) {
                toyProducts.add(product);
            }
        }
    }

    private void cateClickEvent() {
        btnAll.setOnClickListener(v -> {
            currentCategory = Constants.ALLPRODUCT;
            addOtherButtonToDefault(btnFood, btnItem, btnCare, btnToy);
            setButtonToPressed(btnAll);
            loadProduct(allProducts);
        });
        btnFood.setOnClickListener(v -> {
            currentCategory = Constants.FOOD;
            addOtherButtonToDefault(btnAll, btnItem, btnCare, btnToy);
            setButtonToPressed(btnFood);
            loadProduct(foodProducts);
        });
        btnItem.setOnClickListener(v -> {
            currentCategory = Constants.ITEM;
            addOtherButtonToDefault(btnAll, btnFood, btnCare, btnToy);
            setButtonToPressed(btnItem);
            loadProduct(itemProducts);
        });
        btnCare.setOnClickListener(v -> {
            currentCategory = Constants.HEALTH;
            addOtherButtonToDefault(btnAll, btnFood, btnItem, btnToy);
            setButtonToPressed(btnCare);
            loadProduct(healthProducts);
        });
        btnToy.setOnClickListener(v -> {
            currentCategory = Constants.TOY;
            addOtherButtonToDefault(btnAll, btnFood, btnItem, btnCare);
            setButtonToPressed(btnToy);
            loadProduct(toyProducts);
        });
    }

    private void setButtonToPressed(Button btn) {
        btn.setBackgroundResource(R.drawable.colorbrand_btn_bg);
        btn.setTextColor(getResources().getColor(R.color.white));
    }

    private void addOtherButtonToDefault(Button btn1, Button btn2, Button btn3, Button btn4) {
        categoryButtons.add(btn1);
        categoryButtons.add(btn2);
        categoryButtons.add(btn3);
        categoryButtons.add(btn4);
        buttonUIDefault(categoryButtons);
    }

    public void loadProduct(ArrayList<Product> products) {
        LoadingDialog ldDialog = new LoadingDialog();
        ldDialog.showLoadingDialog(this.getContext());
        ProductAdapter adapter = new ProductAdapter(products, fireStoreClass);
        binding.lvlAllProduct.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.lvlAllProduct.setAdapter(adapter);
        binding.lvlAllProduct.setHasFixedSize(false);
        ldDialog.dissmis();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (sKey != null && sKey.equals("0")) {
            LoadingDialog ldDialog = new LoadingDialog();
            ldDialog.showLoadingDialog(this.getContext());
            view.postDelayed(() -> btnFood.performClick(), 300);
            // Set the second menu item in the bottomNavigationView to be checked
            if (getActivity() != null && getActivity() instanceof AppCompatActivity) {
                BottomNavigationView bottomNavigationView = ((AppCompatActivity) getActivity()).findViewById(R.id.bottomNavigationView);
                bottomNavigationView.getMenu().getItem(1).setChecked(true);
            }
            ldDialog.dissmis();

        }
        else if (sKey != null && sKey.equals("1")) {
            LoadingDialog ldDialog = new LoadingDialog();
            ldDialog.showLoadingDialog(this.getContext());
            view.postDelayed(() -> btnToy.performClick(), 300);
            // Set the second menu item in the bottomNavigationView to be checked
            if (getActivity() != null && getActivity() instanceof AppCompatActivity) {
                BottomNavigationView bottomNavigationView = ((AppCompatActivity) getActivity()).findViewById(R.id.bottomNavigationView);
                bottomNavigationView.getMenu().getItem(1).setChecked(true);
            }
            ldDialog.dissmis();

        }
        else if (sKey != null && sKey.equals("2")) {
            LoadingDialog ldDialog = new LoadingDialog();
            ldDialog.showLoadingDialog(this.getContext());
            view.postDelayed(() -> btnItem.performClick(), 300);
            // Set the second menu item in the bottomNavigationView to be checked
            if (getActivity() != null && getActivity() instanceof AppCompatActivity) {
                BottomNavigationView bottomNavigationView = ((AppCompatActivity) getActivity()).findViewById(R.id.bottomNavigationView);
                bottomNavigationView.getMenu().getItem(1).setChecked(true);
            }
            ldDialog.dissmis();

        }
        else if (sKey != null && sKey.equals("3")) {
            LoadingDialog ldDialog = new LoadingDialog();
            ldDialog.showLoadingDialog(this.getContext());
            view.postDelayed(() -> btnCare.performClick(), 300);
            // Set the second menu item in the bottomNavigationView to be checked
            if (getActivity() != null && getActivity() instanceof AppCompatActivity) {
                BottomNavigationView bottomNavigationView = ((AppCompatActivity) getActivity()).findViewById(R.id.bottomNavigationView);
                bottomNavigationView.getMenu().getItem(1).setChecked(true);
            }
            ldDialog.dissmis();

        }
//        else if (sKey != null && sKey.equals("3")) {
//            LoadingDialog ldDialog = new LoadingDialog();
//            ldDialog.showLoadingDialog(this.getContext());
//            view.postDelayed(() -> btnCare.performClick(), 300);
//            // Set the second menu item in the bottomNavigationView to be checked
//            if (getActivity() != null && getActivity() instanceof AppCompatActivity) {
//                BottomNavigationView bottomNavigationView = ((AppCompatActivity) getActivity()).findViewById(R.id.bottomNavigationView);
//                bottomNavigationView.getMenu().getItem(1).setChecked(true);
//            }
//            ldDialog.dissmis();
//
//        }
        else if (sKey != null && sKey.equals("null")){
            LoadingDialog ldDialog = new LoadingDialog();
            ldDialog.showLoadingDialog(this.getContext());
            view.postDelayed(() -> btnAll.performClick(), 300);
            // Set the second menu item in the bottomNavigationView to be checked
            if (getActivity() != null && getActivity() instanceof AppCompatActivity) {
                BottomNavigationView bottomNavigationView = ((AppCompatActivity) getActivity()).findViewById(R.id.bottomNavigationView);
                bottomNavigationView.getMenu().getItem(1).setChecked(true);
            }
            ldDialog.dissmis();
        }
        else {
            LoadingDialog ldDialog = new LoadingDialog();
            ldDialog.showLoadingDialog(this.getContext());
            view.postDelayed(() -> btnAll.performClick(), 300);
            // Set the second menu item in the bottomNavigationView to be checked
            if (getActivity() != null && getActivity() instanceof AppCompatActivity) {
                BottomNavigationView bottomNavigationView = ((AppCompatActivity) getActivity()).findViewById(R.id.bottomNavigationView);
                bottomNavigationView.getMenu().getItem(1).setChecked(true);
            }
            ldDialog.dissmis();
        }


    }
}







