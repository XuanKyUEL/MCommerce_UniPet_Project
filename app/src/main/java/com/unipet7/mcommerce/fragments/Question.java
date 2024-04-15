package com.unipet7.mcommerce.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unipet7.mcommerce.adapters.QuestionAdapter;
import com.unipet7.mcommerce.databinding.FragmentDeliveryOrderBinding;
import com.unipet7.mcommerce.databinding.FragmentQuestionBinding;
import com.unipet7.mcommerce.models.Questions;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Question#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Question extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentQuestionBinding binding;
    List<Questions> questionsList;
    QuestionAdapter questionAdapter;
    public Question() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment question.
     */
    // TODO: Rename and change types and number of parameters
    public static Question newInstance(String param1, String param2) {
        Question fragment = new Question();
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
        binding = FragmentQuestionBinding.inflate(inflater, container, false);
        initData();
        setRecycleView();

        return binding.getRoot();
    }
    private void initData() {
        questionsList = new ArrayList<>();
        questionsList.add(new Questions("Chính sách đổi trả?","Bạn có thể đổi trả sản phẩm trong vòng 7 ngày sau khi nhận hàng, khi lỗi thuộc về Unipet và đơn  vị vận chuyển.", false));
        questionsList.add(new Questions("Tôi có thêm sản phẩm yêu thích để xem sau?","Bạn có thể đổi trả sản phẩm trong vòng 7 ngày sau khi nhận hàng, khi lỗi thuộc về Unipet và đơn  vị vận chuyển.",false));
        questionsList.add(new Questions("Tôi có thể liên hệ với bộ phận hỗ trợ bằng cách nào?","Bạn có thể đổi trả sản phẩm trong vòng 7 ngày sau khi nhận hàng, khi lỗi thuộc về Unipet và đơn  vị vận chuyển.",false));
//        questionsList.add(new Questions("Làm sao để thêm đánh giá về sản phẩm?","Bạn có thể đổi trả sản phẩm trong vòng 7 ngày sau khi nhận hàng, khi lỗi thuộc về Unipet và đơn  vị vận chuyển."));
//        questionsList.add(new Questions("Những phương thức thanh toán nào được chấp nhận?","Bạn có thể đổi trả sản phẩm trong vòng 7 ngày sau khi nhận hàng, khi lỗi thuộc về Unipet và đơn  vị vận chuyển."));
//        questionsList.add(new Questions("UniCard được sử dụng như thế nào?","Bạn có thể đổi trả sản phẩm trong vòng 7 ngày sau khi nhận hàng, khi lỗi thuộc về Unipet và đơn  vị vận chuyển."));
        questionAdapter = new QuestionAdapter(getActivity(),questionsList);

    }
    private void setRecycleView() {
        binding.recycleview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.recycleview.setAdapter(questionAdapter);
        binding.recycleview.setHasFixedSize(true);
        questionAdapter.notifyDataSetChanged();
    }


}