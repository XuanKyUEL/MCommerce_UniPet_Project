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
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.activities.MainActivity;
import com.unipet7.mcommerce.activities.ProfileFunction;
import com.unipet7.mcommerce.activities.SignIn;
import com.unipet7.mcommerce.adapters.MessageDialogAdapter;
import com.unipet7.mcommerce.databinding.FragmentProfileBinding;
import com.unipet7.mcommerce.firebase.FireStoreClass;
import com.unipet7.mcommerce.models.MessageDialog;
import com.unipet7.mcommerce.models.User;
import com.unipet7.mcommerce.utils.LoadingDialog;

public class Profile extends Fragment {
    FragmentProfileBinding binding = null;

    MessageDialogAdapter messageDialogAdapter;
    MessageDialog messageDialog;

    LoadingDialog ldDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ldDialog = new LoadingDialog();
        ldDialog.showLoadingDialog(getActivity());
        if (binding == null) {
            binding = FragmentProfileBinding.inflate(inflater, container, false);
            binding.loadingAvatarProfile.setVisibility(View.VISIBLE);
            binding.loadingAvatarProfile.playAnimation();
        }
        addEvents();
        addEvents1();
        FireStoreClass fireStoreClass = new FireStoreClass();
        fireStoreClass.loadLoggedUserUI(this);
        return binding.getRoot();

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

    public void loadUserData(User user) {
        binding.txtUserName.setText(user.getName());
        binding.txtEmail.setText(user.getEmail());
        Glide.with(requireContext())
                .load(user.getImage())
                .into(binding.profileImage);
        binding.loadingAvatarProfile.cancelAnimation();
        binding.loadingAvatarProfile.setVisibility(View.GONE);
        String mobile = user.getMobile().toString();
        if (mobile.isEmpty()) {
            binding.phoneNumberProfile.setText("Chưa cập nhật");
        } else {
            binding.phoneNumberProfile.setText(mobile);
        }
        ldDialog.dissmis();
    }
}