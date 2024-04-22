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

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.adapters.ProductAdapter;
import com.unipet7.mcommerce.databinding.FragmentAllProductBinding;
import com.unipet7.mcommerce.firebase.FireStoreClass;
import com.unipet7.mcommerce.models.Product;
import com.unipet7.mcommerce.utils.Constants;
import com.unipet7.mcommerce.utils.LoadingDialog;

import java.util.ArrayList;

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
    public ArrayList<Product> allPdproducts;

    ArrayList<Product> foodProducts = new ArrayList<>();

    ArrayList<Product> toyProducts = new ArrayList<>();

    ArrayList<Product> healthProducts = new ArrayList<>();

    ArrayList<Product> itemProducts = new ArrayList<>();

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
        setActionBar(binding.toolbarall);
        if (!isAllProductFetched) {
            LoadingDialog ldDialog = new LoadingDialog();
            ldDialog.showLoadingDialog(getActivity());
            FireStoreClass fireStoreClass = new FireStoreClass();
            allPdproducts = new ArrayList<>();
            fireStoreClass.getAllProducts(this, allPdproducts);
            ldDialog.dissmis();
            isAllProductFetched = true;
        }
        allPdadapter = new ProductAdapter(allPdproducts);
        cateClickEvent();
        Bundle bundle = getArguments();
        if (bundle != null) {
            String category = bundle.getString(Constants.CATEGORY);
            assert category != null;
            loadProductOnCategory(category);
        } else {
            binding.btnAll.performClick();
        }
        onSelectCateListener();
        return binding.getRoot();

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
            default:
                loadProduct(allPdproducts);
        }
    }

    public void divideProduct() {
        for (Product product : allPdproducts) {
            if (product.getCategoryId() == 1 || product.getCategoryId() == 2) {
                foodProducts.add(product);
                Log.i("FragmentAllProduct", "divideProduct: " + foodProducts.size());
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
        binding.btnAll.setOnClickListener(v -> {
            loadProduct(allPdproducts);
        });
        binding.btnFood.setOnClickListener(v -> {
            loadProduct(foodProducts);
        });
        binding.btnItem.setOnClickListener(v -> {
            loadProduct(itemProducts);
        });
        binding.btnCare.setOnClickListener(v -> {
            loadProduct(healthProducts);
        });
        binding.btnToy.setOnClickListener(v -> {
            loadProduct(toyProducts);
        });
    }

    private void loadProduct(ArrayList<Product> products) {
        ProductAdapter adapter = new ProductAdapter(products);
        binding.lvlAllProduct.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.lvlAllProduct.setAdapter(adapter);
        binding.lvlAllProduct.setHasFixedSize(true);
    }


    private void onSelectCateListener() {
        binding.btnFood.setOnClickListener(v -> {
        });
    }

    public void setActionBar(@Nullable Toolbar toolbar) {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back_profile);
        actionBar.setDisplayShowTitleEnabled(false);
    }

}