package com.unipet7.mcommerce.fragments;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.activities.SignIn;
import com.unipet7.mcommerce.adapters.MessageDialogAdapter;
import com.unipet7.mcommerce.databinding.FragmentProfileBinding;
import com.unipet7.mcommerce.models.MessageDialog;

import java.util.Objects;

public class Profile extends Fragment {
    FragmentProfileBinding binding;

    MessageDialogAdapter messageDialogAdapter;
    MessageDialog messageDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        addEvents();
        return binding.getRoot();

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