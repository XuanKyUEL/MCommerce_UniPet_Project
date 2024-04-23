package com.unipet7.mcommerce.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.adapters.NotiAdapter;
import com.unipet7.mcommerce.databinding.FragmentBlankCartBinding;
import com.unipet7.mcommerce.databinding.FragmentNotificationBinding;
import com.unipet7.mcommerce.models.Notice;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_notification#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_notification extends Fragment {
    FragmentNotificationBinding binding;
    ArrayList<Notice> notices;
    NotiAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_notification() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_notification.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_notification newInstance(String param1, String param2) {
        fragment_notification fragment = new fragment_notification();
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
        binding= FragmentNotificationBinding.inflate(inflater, container, false);
        loadNewNotice();
        loadOldNotice();
        return binding.getRoot();
    }

    private void loadNewNotice() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
        binding.rvNewNoti.setLayoutManager(gridLayoutManager);
        binding.rvNewNoti.getRecycledViewPool();
        binding.rvNewNoti.setHasFixedSize(true);

        notices = new ArrayList<>();
        notices.add(new Notice("KHUYẾN MÃI TƯNG BỪNG !!!", "UniPet ưu đãi khách hàng nhân dịp 30/4-1/5, miễn phí vận chuyển mọi đơn hàng cho quý khách!", "20 phút"));
        notices.add(new Notice("Đặt hàng thành công", "Đơn hàng của bạn đã được tạo thành công, thời gian dự kiến giao hàng từ 20-21/04/2024", "43 phút"));
        adapter = new NotiAdapter(requireActivity().getApplicationContext(), notices);
        binding.rvNewNoti.setAdapter(adapter);

    }

    private void loadOldNotice() {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
            binding.rvOldNoti.setLayoutManager(gridLayoutManager);
            binding.rvOldNoti.getRecycledViewPool();
            binding.rvOldNoti.setHasFixedSize(true);

            notices = new ArrayList<>();
            notices.add(new Notice("KHUYẾN MÃI TƯNG BỪNG !!!", "UniPet ưu đãi khách hàng nhân dịp Giỗ tổ Hùng Vương, miễn phí vận chuyển mọi đơn hàng cho quý khách!", "7 ngày trước"));
            notices.add(new Notice("Giao hàng thành công", "Đơn hàng đã được giao đến tay bạn thành công.", "8 ngày trước"));
            adapter = new NotiAdapter(requireActivity().getApplicationContext(), notices);
            binding.rvOldNoti.setAdapter(adapter);
        
    }
}