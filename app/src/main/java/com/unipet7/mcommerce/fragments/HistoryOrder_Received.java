package com.unipet7.mcommerce.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.adapters.HistoryOrderAdapter;
import com.unipet7.mcommerce.databinding.FragmentReceivedOrderBinding;
import com.unipet7.mcommerce.models.HistoryOrders;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryOrder_Received#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryOrder_Received extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentReceivedOrderBinding binding;
    HistoryOrderAdapter HistoryAdapter;
    ArrayList<HistoryOrders> Historyorders;
    public HistoryOrder_Received() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReceivedOrder.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryOrder_Received newInstance(String param1, String param2) {
        HistoryOrder_Received fragment = new HistoryOrder_Received();
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
        binding = FragmentReceivedOrderBinding.inflate(inflater,container,false);
        loadData();
        addEvents();

        return binding.getRoot();    }

    private void addEvents() {
    }

    private void loadData() {
        HistoryAdapter = new HistoryOrderAdapter(getActivity(),R.layout.layout_history_order, initData());
        binding.lvlReceivedOrder.setAdapter(HistoryAdapter);
    }

    private List<HistoryOrders> initData() {
        Historyorders = new ArrayList<>();

        return Historyorders;
    }
}