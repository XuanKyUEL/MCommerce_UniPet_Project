package com.unipet7.mcommerce.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.activities.VoucherActivity;
import com.unipet7.mcommerce.adapters.CartAdapter;
import com.unipet7.mcommerce.adapters.NotiAdapter;
import com.unipet7.mcommerce.databinding.FragmentCartBinding;
import com.unipet7.mcommerce.models.Notice;
import com.unipet7.mcommerce.models.ProductCart;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_cart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_cart extends Fragment {
    FragmentCartBinding binding;
    ArrayList<ProductCart> productCarts;
    CartAdapter adapter;
    private static final int REQUEST_CODE_VOUCHER = 101;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_cart() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_cart.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_cart newInstance(String param1, String param2) {
        fragment_cart fragment = new fragment_cart();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater, container, false);
        setActionBar(binding.toolbarall);
        binding.btnCircleVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), VoucherActivity.class);
                startActivityForResult(intent, REQUEST_CODE_VOUCHER);
            }

        });
        binding.btnVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String voucherCode = binding.edtVoucher.getText().toString().trim();
                if (voucherCode.isEmpty()) {
                    Intent intent = new Intent(getContext(), VoucherActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_VOUCHER);
                } else {
                    //Thêm logic xử lý
                    Toast.makeText(getContext(), "Đã nhập voucher code: " + voucherCode, Toast.LENGTH_SHORT).show();
                }
            }
        });




        loadData();

        return binding.getRoot();
    }

    private void loadData() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
        binding.rcCart.setLayoutManager(gridLayoutManager);
        binding.rcCart.getRecycledViewPool();
        binding.rcCart.setHasFixedSize(true);

        productCarts = new ArrayList<>();
        productCarts.add(new ProductCart(R.drawable.intro_logo, "Con mèo con là con con mèo mướp", 1000.0, 1000.0, 1.0));
        productCarts.add(new ProductCart(R.drawable.intro_logo, "Con mèo con là con con mèo mướp", 1000.0, 1000.0, 1.0));
        productCarts.add(new ProductCart(R.drawable.intro_logo, "Con mèo con là con con mèo mướp", 1000.0, 1000.0, 1.0));
        productCarts.add(new ProductCart(R.drawable.intro_logo, "Con mèo con là con con mèo mướp", 1000.0, 1000.0, 1.0));
        adapter = new CartAdapter(requireActivity().getApplicationContext(), productCarts);
        binding.rcCart.setAdapter(adapter);

    }



    public void setActionBar(@Nullable Toolbar toolbar) {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back_profile);
        actionBar.setDisplayShowTitleEnabled(false);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_VOUCHER && resultCode == Activity.RESULT_OK && data != null) {
            // Lấy dữ liệu voucher code từ Intent
            String voucherCode = data.getStringExtra("voucher_code");
            // Hiển thị dữ liệu voucher code vào EditText edtVoucher
            binding.edtVoucher.setText(voucherCode);
        }
    }


}