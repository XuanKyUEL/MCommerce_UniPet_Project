package com.unipet7.mcommerce.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.adapters.HistoryOrderAdapter;
import com.unipet7.mcommerce.databinding.FragmentAllHistoryOrderBinding;
import com.unipet7.mcommerce.models.HistoryOrders;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryOrder_All#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryOrder_All extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentAllHistoryOrderBinding binding;
    HistoryOrderAdapter HistoryAdapter;
    ArrayList<HistoryOrders> Historyorders;
    ActivityResultLauncher<Intent> activityResultLauncher;


    public HistoryOrder_All() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllHistoryOrder.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryOrder_All newInstance(String param1, String param2) {
        HistoryOrder_All fragment = new HistoryOrder_All();
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
        binding = FragmentAllHistoryOrderBinding.inflate(inflater,container,false);
        loadData();
        //addEvents();

        return binding.getRoot();
    }



    private void loadData() {
        HistoryAdapter = new HistoryOrderAdapter(getActivity(),R.layout.layout_history_order, initData());
        binding.lvlOrder.setAdapter(HistoryAdapter);
    }
//    private void addEvents() {
//        binding.lvlOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Button buttonbuyagian = view.findViewById(R.id.btn_buyagain);
//                buttonbuyagian.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                    }
//                });
//                Button buttonrate = view.findViewById(R.id.btn_rate);
//                buttonrate.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                });
//            }
//        });
//    }
    private List<HistoryOrders> initData() {
        Historyorders = new ArrayList<>();
        Historyorders.add(new HistoryOrders("11/12/2024","1111", "Đã hủy", R.drawable.unipet_app_icon,"Royal Canin Rottweiler Puppy con mèo con đi hia long nhong",
                "Túi", 1.0,24000.0, 24000.0));
        Historyorders.add(new HistoryOrders("11/12/2024","1111", "Đã hủy", R.drawable.unipet_app_icon,"Royal Canin Rottweiler Puppy con mèo con đi hia long nhong",
                "Túi", 1.0,24000.0, 24000.0));
        Historyorders.add(new HistoryOrders("11/12/2024","1111", "Đã hủy", R.drawable.unipet_app_icon,"Royal Canin Rottweiler Puppy con mèo con đi hia long nhong",
                "Túi", 1.0,24000.0, 24000.0));
        Historyorders.add(new HistoryOrders("11/12/2024","1111", "Đã hủy", R.drawable.unipet_app_icon,"Royal Canin Rottweiler Puppy con mèo con đi hia long nhong",
                "Túi", 1.0,24000.0, 24000.0));
        Historyorders.add(new HistoryOrders("11/12/2024","1111", "Đã hủy", R.drawable.unipet_app_icon,"Royal Canin Rottweiler Puppy con mèo con đi hia long nhong",
                "Túi", 1.0,24000.0, 24000.0));

        return Historyorders;
    }
}