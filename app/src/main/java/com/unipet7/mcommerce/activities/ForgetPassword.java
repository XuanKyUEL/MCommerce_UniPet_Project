package com.unipet7.mcommerce.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.adapters.MessageDialogAdapter;
import com.unipet7.mcommerce.databinding.ActivityForgetPasswordBinding;
import com.unipet7.mcommerce.models.MessageDialog;

import java.util.Objects;

public class ForgetPassword extends BaseActivity {

    ActivityForgetPasswordBinding binding;
    RelativeLayout rlForgetPasswordProgress;

    AppCompatEditText edtEmail;
    TextInputLayout tilEmail;

    LottieAnimationView lottieLoadingForgetPassword;

    TextView tvForgetPasswordCta;

    MessageDialog msDialog;
    MessageDialogAdapter msdAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mapping();
        addEvents();
        resetErrorUI();
    }

    private void resetErrorUI() {
        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                tilEmail.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilEmail.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                tilEmail.setError(null);
            }
        });

    }


    private void mapping() {
        rlForgetPasswordProgress = binding.rlForgetPwdProgress;
        edtEmail = binding.edtEmailFgpw;
        tilEmail = binding.tilEmailFgpw;

        lottieLoadingForgetPassword = binding.lottieLoadingFgpw;
        tvForgetPasswordCta = binding.tvFgpwCta;
    }

    private void addEvents() {
        binding.rlForgetPwdProgress.setOnClickListener(v -> {
            if (isEmailValid()) {
                loadingAnimation();
                FirebaseAuth.getInstance().sendPasswordResetEmail(edtEmail.getText().toString())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                showMessageDialog("Thành công", "Vui lòng kiểm tra email của bạn để đặt lại mật khẩu", "OK");
                            } else {
                                showMessageDialog("Thất bại", "Email không tồn tại", "OK");
                            }
                            resetButtonUI();
                        });
            }
        });
        binding.ibBackFgpw.setOnClickListener(v -> finish());
    }

    private void showMessageDialog(String title, String message, String pstvText) {
        msdAdapter = new MessageDialogAdapter(this);
        msDialog = new MessageDialog(title, message, pstvText, null);
        msDialog.setHasNegativeBtn(false);
        if (title.contains("Thành công")) {
            msDialog.setPositiveClickListener(v -> {
                msdAdapter.dismissDialog();
                // send user email to sign in activity
                String email = Objects.requireNonNull(edtEmail.getText()).toString();
                Intent intent = new Intent(this, SignIn.class);
                intent.putExtra("email", email);
                startActivity(intent);
            });
        } else {
            msDialog.setPositiveClickListener(v -> msdAdapter.dismissDialog());
        }
        msdAdapter.showDialog(msDialog);
    }

    private void loadingAnimation() {
        lottieLoadingForgetPassword.setVisibility(View.VISIBLE);
        lottieLoadingForgetPassword.playAnimation();
        tvForgetPasswordCta.setVisibility(View.GONE);
    }

    private void resetButtonUI() {
        lottieLoadingForgetPassword.setVisibility(View.GONE);
        lottieLoadingForgetPassword.cancelAnimation();
        tvForgetPasswordCta.setVisibility(View.VISIBLE);
    }

    private boolean isEmailValid() {
        if (edtEmail.getText().toString().isEmpty()) {
            tilEmail.setErrorIconDrawable(null);
            setErrorWithIcon(tilEmail, "Email không được để trống", R.drawable.error_input_icon);
            edtEmail.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()) {
            tilEmail.setErrorIconDrawable(null);
            setErrorWithIcon(tilEmail, "Email không hợp lệ", R.drawable.error_input_icon);
            edtEmail.requestFocus();
            return false;
        } else {
            tilEmail.setError(null);
        }
        return true;
    }

}