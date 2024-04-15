package com.unipet7.mcommerce.fragments.onboarding;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.adapters.HistoryOrderAdapter;
import com.unipet7.mcommerce.databinding.FragmentCancelOrderBinding;
import com.unipet7.mcommerce.models.HistoryOrders;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryOrder_Cancel#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryOrder_Cancel extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentCancelOrderBinding binding;
    HistoryOrderAdapter HistoryAdapter;
    ArrayList<HistoryOrders> Historyorders;
    public HistoryOrder_Cancel() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CancelOrder.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryOrder_Cancel newInstance(String param1, String param2) {
        HistoryOrder_Cancel fragment = new HistoryOrder_Cancel();
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
        binding = FragmentCancelOrderBinding.inflate(inflater,container,false);
        loadData();
        addEvents();

        return binding.getRoot();    }

    private void addEvents() {
    }

    private void loadData() {
        HistoryAdapter = new HistoryOrderAdapter(getActivity(),R.layout.layout_cancelorder, initData());
        binding.lvlCancelOrder.setAdapter(HistoryAdapter);
    }

    private List<HistoryOrders> initData() {
        Historyorders = new ArrayList<>();
        Historyorders.add(new HistoryOrders("11/12/2024","1111", "Đã hủy", R.drawable.unipet_app_icon,"Royal Canin Rottweiler Puppy con mèo con đi hia long nhong",
                "Túi", 1.0,24000.0, 24000.0));

        return Historyorders;
    }
}