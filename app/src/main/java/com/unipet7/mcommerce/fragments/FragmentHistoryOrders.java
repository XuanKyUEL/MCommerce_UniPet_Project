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

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.databinding.FragmentHistoryOrdersBinding;

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
        addEvents();
        addEvents1();
        return binding.getRoot();

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
            if (v.equals(binding.btnAll))
                fragmnent = new HistoryOrder_All();
            else if (v.equals(binding.btnConfirmorder))
                fragmnent = new HistoryOrder_Confirm();
            else if (v.equals(binding.btnDeliverorder))
                fragmnent = new HistoryOrder_Delivery();
            else if (v.equals(binding.btnReceivedorder))
                fragmnent = new HistoryOrder_Received();
            else if (v.equals(binding.btnCancelorder))
                fragmnent = new HistoryOrder_Cancel();
            assert fragmnent != null;
            transaction.replace(R.id.containerLayout, fragmnent);
            transaction.commit();
        }
    };

    private void addEvents1() {
        binding.btnAll.setOnClickListener(onClickListener);
        binding.btnConfirmorder.setOnClickListener(onClickListener);
        binding.btnDeliverorder.setOnClickListener(onClickListener);
        binding.btnReceivedorder.setOnClickListener(onClickListener);
        binding.btnCancelorder.setOnClickListener(onClickListener);

    }
}