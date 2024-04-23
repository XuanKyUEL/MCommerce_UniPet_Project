package com.unipet7.mcommerce.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.util.TypedValue;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.ItemTouchHelper;
import java.util.ArrayList;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.View;
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
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if (direction == ItemTouchHelper.LEFT) {
                // Xử lý khi vuốt sang trái
            } else if (direction == ItemTouchHelper.RIGHT) {
                // Xử lý khi vuốt sang phải
            }
        }
        @Override
        public void onChildDraw(
                Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                float dX, float dY, int actionState, boolean isCurrentlyActive
        ) {
            View view = viewHolder.itemView;

            Paint paint = new Paint();
            paint.setColor(getResources().getColor(R.color.brandPrimary));
            paint.setTextSize(30f);
            paint.setAntiAlias(true);

            // Fix position for button
            float deleteButtonLeft = view.getRight() - (view.getRight() / 5f);
            float deleteButtonTop = view.getTop();
            float deleteButtonRight = view.getRight() - view.getPaddingRight();
            float deleteButtonBottom = view.getBottom();

            // Draw a button
            float radius = 20f;
            RectF deleteButtonDelete = new RectF(deleteButtonLeft, deleteButtonTop, deleteButtonRight, deleteButtonBottom);
            c.drawRoundRect(deleteButtonDelete, radius, radius, paint);

            // Set color for draw text inside button
            paint.setColor(getResources().getColor(R.color.white));

            // Load the vector drawable
            Drawable deleteIconDrawable = AppCompatResources.getDrawable(getContext(), R.drawable.ic_cart_delete); // Replace R.drawable.ic_cart_delete with your actual vector icon

// Set the bounds for the drawable
            int iconWidth = deleteIconDrawable.getIntrinsicWidth();
            int iconHeight = deleteIconDrawable.getIntrinsicHeight();
            int iconLeft = (int) (deleteButtonDelete.centerX() - iconWidth / 2f);
            int iconTop = (int) (deleteButtonDelete.centerY() - iconHeight / 2f);
            deleteIconDrawable.setBounds(iconLeft, iconTop, iconLeft + iconWidth, iconTop + iconHeight);

// Draw the vector icon
            deleteIconDrawable.draw(c);


            // dX of item run from 0 to `-X` width of screen
            // Item will stop in dX / 5
            super.onChildDraw(c, recyclerView, viewHolder, dX / 5f, dY, actionState, isCurrentlyActive);
        }

        // Swipe back (start, end, top, down)
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return makeMovementFlags(0, ItemTouchHelper.START);
        }};






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
    ItemTouchHelper itemTouchHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater, container, false);
        setActionBar(binding.toolbarall);
        itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.rcCart);
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