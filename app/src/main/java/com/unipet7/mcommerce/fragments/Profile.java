package com.unipet7.mcommerce.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.activities.MainActivity;
import com.unipet7.mcommerce.activities.ProfileFunction;
import com.unipet7.mcommerce.activities.SignIn;
import com.unipet7.mcommerce.adapters.MessageDialogAdapter;
import com.unipet7.mcommerce.databinding.FragmentProfileBinding;
import com.unipet7.mcommerce.models.MessageDialog;

public class Profile extends Fragment {
    FragmentProfileBinding binding;

    MessageDialogAdapter messageDialogAdapter;
    MessageDialog messageDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        addEvents();
        addEvents1();
        addEvents2();
        setActionBar(binding.toolbar);
        return binding.getRoot();

    }

    private void addEvents2() {
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay về trang MainActivity
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }

    public void setActionBar(@Nullable Toolbar toolbar) {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back_profile);
        actionBar.setDisplayShowTitleEnabled(false);
    }

    private void addEvents1() {
        binding.lnAboutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileFunction.class);
                intent.putExtra("function", "about_me");
                startActivity(intent);

            }
        });
        binding.lnAddress.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileFunction.class);
                intent.putExtra("function", "address");
                startActivity(intent);

            }
        }));
        binding.lnHelpCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileFunction.class);
                intent.putExtra("function", "help_center");
                startActivity(intent);

            }
        });
        binding.lnPaymentMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileFunction.class);
                intent.putExtra("function", "payment_method");
                startActivity(intent);
            }
        });
        binding.lnPurchasedOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileFunction.class);
                intent.putExtra("function", "purchased_orders");
                startActivity(intent);
            }
        });
    }

    private void addEvents() {
        binding.lnSignout.setOnClickListener(v -> {
            showConfirmSignOutDialog();
        });
    }

    private void showConfirmSignOutDialog() {
        String title = getString(R.string.sign_out_dialog_title);
        messageDialogAdapter = new MessageDialogAdapter(getActivity());
        messageDialog = new MessageDialog(title, null, "Đăng Xuất", "Hủy");
        messageDialog.setPositiveClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getActivity(), SignIn.class);
            startActivity(intent);
            requireActivity().finish();
        });
        messageDialogAdapter.showDialog(messageDialog);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}