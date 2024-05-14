package com.unipet7.mcommerce.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unipet7.mcommerce.activities.MainActivity;
import com.unipet7.mcommerce.databinding.FragmentBlankCartBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankCart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankCart extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentBlankCartBinding binding;

    public BlankCart() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_blank_cart.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankCart newInstance(String param1, String param2) {
        BlankCart fragment = new BlankCart();
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
        binding= FragmentBlankCartBinding.inflate(inflater, container, false);
        addEvents();
        return binding.getRoot();    }

    private void addEvents() {
        binding.btnBackHome.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.mainViewPager2.setCurrentItem(0);
            }
        });
    }
}