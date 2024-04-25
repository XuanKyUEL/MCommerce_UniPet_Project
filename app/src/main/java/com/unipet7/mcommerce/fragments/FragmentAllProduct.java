package com.unipet7.mcommerce.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.unipet7.mcommerce.R;
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
    private ArrayList<Product> allProducts;

    ArrayList<Button> categoryButtons = new ArrayList<>();

    ArrayList<Product> foodProducts = new ArrayList<>();

    ArrayList<Product> toyProducts = new ArrayList<>();

    ArrayList<Product> healthProducts = new ArrayList<>();

    ArrayList<Product> itemProducts = new ArrayList<>();

    Button btnAll, btnFood, btnItem, btnCare, btnToy;

    List<Integer> favList = new ArrayList<>();

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
        cateClickEvent();
        binding.btnAll.performClick();
        return binding.getRoot();

    }

    private void mapping() {
        btnAll = binding.btnAll;
        btnFood = binding.btnFood;
        btnItem = binding.btnItem;
        btnCare = binding.btnCare;
        btnToy = binding.btnToy;
    }

    private void loadProductOnCategory(String category) {
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
            case  Constants.ALLPRODUCT:
                loadProduct(allProducts);
                break;
            default:
                loadProduct(allProducts);
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
            addOtherButtonToDefault(btnFood, btnItem, btnCare, btnToy);
            setButtonToPressed(btnAll);
            loadProduct(allProducts);
        });
        btnFood.setOnClickListener(v -> {
            addOtherButtonToDefault(btnAll, btnItem, btnCare, btnToy);
            setButtonToPressed(btnFood);
            loadProduct(foodProducts);
        });
        btnItem.setOnClickListener(v -> {
            addOtherButtonToDefault(btnAll, btnFood, btnCare, btnToy);
            setButtonToPressed(btnItem);
            loadProduct(itemProducts);
        });
        btnCare.setOnClickListener(v -> {
            addOtherButtonToDefault(btnAll, btnFood, btnItem, btnToy);
            setButtonToPressed(btnCare);
            loadProduct(healthProducts);
        });
        btnToy.setOnClickListener(v -> {
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
        ProductAdapter adapter = new ProductAdapter(products);
        // set layout manager to grid layout with 2 columns and stretch the items to fill the screen
        binding.lvlAllProduct.setLayoutManager(new GridLayoutManager(this.getContext(), 2, GridLayoutManager.VERTICAL, false));
        binding.lvlAllProduct.setAdapter(adapter);
        binding.lvlAllProduct.setHasFixedSize(true);
        ldDialog.dissmis();
    }



}