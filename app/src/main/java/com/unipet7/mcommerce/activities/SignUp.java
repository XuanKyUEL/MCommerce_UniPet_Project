package com.unipet7.mcommerce.activities;

import static com.unipet7.mcommerce.activities.SignIn.EMAIL_KEY;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.adapters.MessageDialogAdapter;
import com.unipet7.mcommerce.databinding.ActivitySignUpBinding;
import com.unipet7.mcommerce.firebase.FireStoreClass;
import com.unipet7.mcommerce.models.MessageDialog;
import com.unipet7.mcommerce.models.User;

public class SignUp extends BaseActivity {

    ActivitySignUpBinding binding;
    MessageDialogAdapter messageDialogAdapter;

    MessageDialog messageDialog;

    AppCompatEditText edtEmail, edtPassword, edtConfirmPassword;

    TextInputLayout tilEmail, tilPassword, tilConfirmPassword;

    RelativeLayout rlSignUp;
    TextView tvSignUnCta;
    LottieAnimationView lottieAnimationView;

    boolean isTermsAndConditionsChecked = false;

    FirebaseAuth mAuth;
    FireStoreClass fireStoreClass;



    public static final String SHARED_PREFS = "signUpInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        fireStoreClass = new FireStoreClass();

        configTermsAndConditions();
        mapping();
        resetErrorUI();
        addEvents();
    }

    public void resetErrorUI() {
        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0) {
                    tilEmail.setEndIconMode(TextInputLayout.END_ICON_CUSTOM);
                    ColorStateList colorStateList = ColorStateList.valueOf(ContextCompat.getColor(SignUp.this, R.color.brandPrimary));
                    tilEmail.setEndIconTintList(colorStateList);
                    tilEmail.setEndIconDrawable(R.drawable.clear_input);
                    tilEmail.setEndIconOnClickListener(v -> {
                        edtEmail.setText("");
                    });
                } else {
                    tilEmail.setEndIconMode(TextInputLayout.END_ICON_NONE);
                }
                tilEmail.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // do nothing
            }
        });
        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilPassword.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilConfirmPassword.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void mapping() {
        edtEmail = binding.edtEmailSignUp;
        edtPassword = binding.edtPasswordSignUp;
        edtConfirmPassword = binding.rePwdEdtSignup;

        tilEmail = binding.tilEmailSignUp;
        tilPassword = binding.tilPasswordSignUp;
        tilConfirmPassword = binding.tilRePwdSignup;

        rlSignUp = binding.rlSignUpCta;
        tvSignUnCta = binding.tvSignUpCta;
        lottieAnimationView = binding.lottieLoadingSignUp;
    }


    private void addEvents() {
        binding.tvSignInCta.setOnClickListener(v -> {
            finish();
        });

        binding.cbTermsSignUp.setOnClickListener(v -> {
            isTermsAndConditionsChecked = ((CheckBox) v).isChecked();
            Log.i("SignUp", "addEvents: " + isTermsAndConditionsChecked);
        });

        binding.ibBackSignup.setOnClickListener(v -> {
            finish();
        });

        rlSignUp.setOnClickListener(v -> {
            if (isSignUpValid()) {
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                signUpUser(email, password);
                // hide keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null && getCurrentFocus() != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });
    }

    private boolean isSignUpValid() {
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

        if (edtPassword.getText().toString().isEmpty()) {
            tilPassword.setErrorIconDrawable(null);
            setErrorWithIcon(tilPassword, "Mật khẩu không được để trống", R.drawable.error_input_icon);
            edtPassword.requestFocus();
            return false;
        } else if (edtPassword.getText().toString().length() < 8) {
            tilPassword.setErrorIconDrawable(null);
            setErrorWithIcon(tilPassword, "Mật khẩu phải có ít nhất 8 ký tự", R.drawable.error_input_icon);
            edtPassword.requestFocus();
            return false;
        } else {
            tilPassword.setError(null);
        }

        if (edtConfirmPassword.getText().toString().isEmpty()) {
            tilConfirmPassword.setErrorIconDrawable(null);
            setErrorWithIcon(tilConfirmPassword, "Nhập lại mật khẩu không được để trống", R.drawable.error_input_icon);
            edtConfirmPassword.requestFocus();
            return false;
        } else if (!edtPassword.getText().toString().equals(edtConfirmPassword.getText().toString())) {
            tilConfirmPassword.setErrorIconDrawable(null);
            setErrorWithIcon(tilConfirmPassword, "Mật khẩu không khớp", R.drawable.error_input_icon);
            edtConfirmPassword.requestFocus();
            return false;
        } else {
            tilConfirmPassword.setError(null);
        }

        if (!isTermsAndConditionsChecked) {
            Toast.makeText(this, "Vui lòng đồng ý với Điều Khoản và Dịch Vụ", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    private void signUpUser(String email, String password) {
        loadingAnimation();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String initName = email.substring(0, email.indexOf("@"));
                        FirebaseUser user = mAuth.getCurrentUser();
                        assert user != null;
                        User initUserInfo = new User(user.getUid(), email, initName);
                        Log.i("SignUp", "signUpUser: " + initUserInfo.getId());
                        fireStoreClass.addUserToFirebaseDB(this, initUserInfo);
                    } else {
                        signUpDialog("Đăng ký thất bại", "Đăng ký tài khoản không thành công", "", "Đóng");
                    }
                    resetAnimation();
                });
    }

    public void resetAnimation() {
        rlSignUp.setClickable(true);
        tvSignUnCta.setVisibility(View.VISIBLE);
        lottieAnimationView.setVisibility(View.GONE);
        lottieAnimationView.cancelAnimation();
    }

    private void loadingAnimation() {
        rlSignUp.setClickable(false);
        tvSignUnCta.setVisibility(View.GONE);
        lottieAnimationView.setVisibility(View.VISIBLE);
        lottieAnimationView.playAnimation();
    }

    public void signUpDialog(String title, String message, String positiveText, String negativeText) {
        messageDialogAdapter = new MessageDialogAdapter(this);
        messageDialog = new MessageDialog(title, message, positiveText, negativeText);
        messageDialog.setCancelable(true);
        if (negativeText.isEmpty()) {
            messageDialog.hasNegativeBtn = false;
        } else {
            messageDialog.setNegativeClickListener(v1 -> {
                messageDialogAdapter.dismissDialog();
            });
        }
        if (positiveText.isEmpty()) {
            messageDialog.hasPositiveBtn = false;
        } else {
            messageDialog.setPositiveClickListener(v1 -> {
                navigateToSignIn();
            });
        }
        messageDialogAdapter.showDialog(messageDialog);
    }

    private void navigateToSignIn() {
        // Lưu dữ liệu vào SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAIL_KEY, edtEmail.getText().toString());
        editor.apply();

        // Chuyển người dùng sang hoạt động SignIn và truyền dữ liệu qua Intent
        Intent intent = new Intent(this, SignIn.class);
        intent.putExtra(EMAIL_KEY, edtEmail.getText().toString());
        startActivity(intent);
        finish();
    }

    private void configTermsAndConditions() {
        // Tạo một SpannableString từ chuỗi nguồn
        String fullText = "Bằng cách nhấn nút Đăng Ký, bạn đã đồng ý với các Điều Khoản và Dịch Vụ của UniPet";
        SpannableString spannableString = new SpannableString(fullText);

// Tạo một UnderlineSpan để gạch chân cho chuỗi
        UnderlineSpan underlineSpan = new UnderlineSpan();

// Tạo một ForegroundColorSpan để thay đổi màu sắc của chuỗi
// Sử dụng màu brandColor của bạn, giả sử là R.color.brandColor
        Context context = this.binding.getRoot().getContext();
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(context, R.color.brandPrimary));

// Tìm vị trí bắt đầu và kết thúc của chuỗi "Điều Khoản và Dịch Vụ" trong chuỗi nguồn
        int start = fullText.indexOf("Điều Khoản và Dịch Vụ");
        int end = start + "Điều Khoản và Dịch Vụ".length();

// Áp dụng UnderlineSpan và ForegroundColorSpan cho SpannableString
// SPAN_EXCLUSIVE_EXCLUSIVE nghĩa là span sẽ không mở rộng đến text được thêm vào sau này
        spannableString.setSpan(underlineSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(colorSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

// Đặt SpannableString cho TextView
        TextView termsAndConditions = binding.tvTerms;
        termsAndConditions.setText(spannableString);
    }

    public void userRegisteredSuccess() {
        signUpDialog("Đăng ký thành công", "Chúc mừng bạn đã đăng ký tài khoản thành công", "Đăng nhập ngay", "Đóng");
        resetAnimation();
        // sign out user
        mAuth.signOut();
    }
}