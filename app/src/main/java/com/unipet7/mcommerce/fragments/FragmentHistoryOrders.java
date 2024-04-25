package com.unipet7.mcommerce.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.databinding.FragmentHistoryOrdersBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentHistoryOrders#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHistoryOrders extends Fragment {
    FragmentHistoryOrdersBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<Button> statusbutton = new ArrayList<>();
    Button btnAll1, btnConfirm, btnDelivery, btnReceived, btnCancel;


    public FragmentHistoryOrders() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentHistoryOrders.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHistoryOrders newInstance(String param1, String param2) {
        FragmentHistoryOrders fragment = new FragmentHistoryOrders();
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
        binding= FragmentHistoryOrdersBinding.inflate(inflater, container, false);
        setActionBar(binding.toolbar);
        mapping();
        addEvents();
        addEvents1();
        return binding.getRoot();

    }
    private void mapping() {
        btnAll1 = binding.btnAll1;
        btnConfirm = binding.btnConfirm;
        btnDelivery = binding.btnDelivery;
        btnReceived = binding.btnReceived;
        btnCancel = binding.btnCancel;
    }
    public void buttonUIDefault(ArrayList<Button> buttons) {
        for (Button button : buttons) {
            button.setBackgroundResource(R.drawable.btn_bg_outline);
            button.setTextColor(getResources().getColor(R.color.black));
        }
    }
    private void setButtonToPressed(Button btn) {
        btn.setBackgroundResource(R.drawable.colorbrand_btn_bg);
        btn.setTextColor(getResources().getColor(R.color.white));
    }
    private void addOtherButtonToDefault(Button btn1, Button btn2, Button btn3, Button btn4) {
        statusbutton.add(btn1);
        statusbutton.add(btn2);
        statusbutton.add(btn3);
        statusbutton.add(btn4);
        buttonUIDefault(statusbutton);
    }
    public void setActionBar(@Nullable Toolbar toolbar) {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back_profile);
        actionBar.setDisplayShowTitleEnabled(false);
    }
    private void addEvents() {
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager manager = requireActivity().getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            Fragment fragmnent = null;
            if (v.equals(btnAll1)) {
                addOtherButtonToDefault(btnConfirm, btnDelivery, btnReceived, btnCancel);
                setButtonToPressed(btnAll1);
                fragmnent = new HistoryOrder_All();
            }
            else if (v.equals(btnConfirm))
            {
                addOtherButtonToDefault(btnAll1, btnDelivery, btnReceived, btnCancel);
                setButtonToPressed(btnConfirm);
                fragmnent = new HistoryOrder_Confirm();
            }
            else if (v.equals(btnDelivery))
            {
                addOtherButtonToDefault(btnAll1, btnConfirm, btnReceived, btnCancel);
                setButtonToPressed(btnDelivery);
                fragmnent = new HistoryOrder_Delivery();
            } else if (v.equals(btnReceived))
            {
                addOtherButtonToDefault(btnAll1, btnConfirm, btnDelivery, btnCancel);
                setButtonToPressed(btnReceived);
                fragmnent = new HistoryOrder_Received();
            }
            else if (v.equals(btnCancel)){
                addOtherButtonToDefault(btnAll1, btnConfirm, btnDelivery, btnReceived);
                setButtonToPressed(btnCancel);
                fragmnent = new HistoryOrder_Cancel();
            }
            assert fragmnent != null;
            transaction.replace(R.id.containerLayout, fragmnent);
            transaction.commit();
        }
    };

    private void addEvents1() {
        btnAll1.setOnClickListener(onClickListener);
        btnConfirm.setOnClickListener(onClickListener);
        btnDelivery.setOnClickListener(onClickListener);
        btnReceived.setOnClickListener(onClickListener);
        btnCancel.setOnClickListener(onClickListener);

    }
}