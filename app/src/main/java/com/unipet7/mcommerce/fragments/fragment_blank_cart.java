package com.unipet7.mcommerce.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.databinding.FragmentBlankCartBinding;
import com.unipet7.mcommerce.databinding.FragmentEmptyNotificationBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_blank_cart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_blank_cart extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentBlankCartBinding binding;

    public fragment_blank_cart() {
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
    public static fragment_blank_cart newInstance(String param1, String param2) {
        fragment_blank_cart fragment = new fragment_blank_cart();
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
        binding.btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment homeFragment = new Home();

                // Lấy FragmentManager
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                // Thực hiện giao diện chuyển đổi Fragment
                fragmentManager.beginTransaction()
                        .replace(((ViewGroup) requireView().getParent()).getId(), homeFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}