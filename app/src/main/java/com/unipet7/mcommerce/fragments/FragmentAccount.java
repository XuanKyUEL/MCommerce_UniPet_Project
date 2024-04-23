package com.unipet7.mcommerce.fragments;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.adapters.MessageDialogAdapter;
import com.unipet7.mcommerce.databinding.FragmentAccountBinding;
import com.unipet7.mcommerce.databinding.FragmentProfileBinding;
import com.unipet7.mcommerce.firebase.FireStoreClass;
import com.unipet7.mcommerce.models.MessageDialog;
import com.unipet7.mcommerce.models.User;
import com.unipet7.mcommerce.utils.Constants;

import java.util.HashMap;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAccount#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAccount extends Fragment {
    FragmentAccountBinding binding;
    ActivityResultLauncher<Intent> activityResultLauncher;
    boolean openCam;

    Uri userAvatarUri;

    String uriString = "";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    FireStoreClass fireStoreClass = new FireStoreClass();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentAccount() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAccount.
     */
    // TODO: Rename and change types and number of parameters

    User user;
    public static FragmentAccount newInstance(String param1, String param2) {
        FragmentAccount fragment = new FragmentAccount();
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
        binding= FragmentAccountBinding.inflate(inflater, container, false);
        fireStoreClass.getCurrentUID();
        setActionBar(binding.toolbar);
        addEvents();
        addEvents1();
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                if (openCam) {
                    binding.imvUserImageAccount.setImageURI(userAvatarUri);
                } else {
                    userAvatarUri = result.getData().getData();
                    Glide.with(requireContext())
                            .load(userAvatarUri)
                            .into(binding.imvUserImageAccount);
                }
                uriString = userAvatarUri.toString();
                Log.i("FragmentAccount", "onActivityResult: " + userAvatarUri.toString());
            }
        });
        fireStoreClass.loadLoggedUserUI(this);
        return binding.getRoot();
    }

    private void addEvents1() {
        binding.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheet();
            }

            private void showBottomSheet() {
                Dialog dialog = new BottomSheetDialog(requireActivity());
                dialog.setContentView(R.layout.account_picture_pick);

                LinearLayout bsCam = dialog.findViewById(R.id.bsCamera);
                assert bsCam != null;
                bsCam.setOnClickListener(v -> {
                    openCam = true;
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, "New Picture");
                    values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
                    userAvatarUri = requireActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, userAvatarUri);
                    activityResultLauncher.launch(intent);
                    dialog.dismiss();
                    //Open Camera
                });
                LinearLayout bsGallery = dialog.findViewById(R.id.bsGallery);
                assert bsGallery != null;
                bsGallery.setOnClickListener(v -> {
                    openCam = false;
                    //Open Gallery
                    Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activityResultLauncher.launch(intent);
                    dialog.dismiss();
                });
                dialog.show();
                Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
                dialog.getWindow().setGravity(Gravity.BOTTOM);
            }
        });

        binding.btnUpdateAccount.setOnClickListener(v -> {
            confirmUpdateDialog("Xác nhận cập nhật thông tin", "Bạn có chắc chắn muốn cập nhật thông tin không?", "Cập nhật", "Hủy");
        });
    }

    private void uploadUserImage() {
        StorageReference sRef = FirebaseStorage
                .getInstance()
                .getReference(Constants.USER_AVATAR + fireStoreClass.getCurrentUID() + "."+ System.currentTimeMillis() + "." + user.getName() + getFileExtension(userAvatarUri));

        sRef.putFile(userAvatarUri).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(uri -> {
                    Log.i("Downloadable Image URL", uri.toString());
                    uriString = uri.toString();
                    updateUserInfo();
                });
            } else {
                confirmUpdateDialog("Cập nhật thất bại", "Cập nhật ảnh đại diện thất bại", "", "");
                Log.e("FragmentAccount", "uploadUserImage: ", task.getException());
            }
        });
    }

    private void confirmUpdateDialog(String title, String message, String positiveButton, String negativeButton) {
        MessageDialog messageDialog = new MessageDialog(title, message, positiveButton, negativeButton);
        MessageDialogAdapter messageDialogAdapter = new MessageDialogAdapter(requireActivity());
        switch (title) {
            case "Xác nhận cập nhật thông tin":
                messageDialog.setPositiveClickListener(v -> {
                    if (userAvatarUri != null) {
                        uploadUserImage();
                    } else {
                        updateUserInfo();
                    }
                });
                messageDialog.setNegativeClickListener(v -> {
                    messageDialogAdapter.dismissDialog();
                });
                break;
            case "Cập nhật thất bại":
            case "Cập nhật thành công":
                messageDialog.setCancelable(true);
                messageDialog.hasNegativeBtn = false;
                messageDialog.setPositiveClickListener(v -> {
                    messageDialogAdapter.dismissDialog();
                    if (title.equals("Cập nhật thành công")) {
                        requireActivity().finish();
                    }
                });
                break;
        }
        messageDialogAdapter.showDialog(messageDialog);
    }

    private String getFileExtension(Uri uri) {
        return MimeTypeMap
                .getSingleton()
                .getExtensionFromMimeType(requireActivity().getContentResolver().getType(uri));
    }

    private void updateUserInfo() {
        HashMap<String, Object> userHashMap = new HashMap<>();
        String name = binding.edtUserNameAccount.getText().toString().trim();
        String phone = binding.edtPhoneAccount.getText().toString().trim();
        if (!name.isEmpty() && !name.equals(user.getName())) {
            userHashMap.put(Constants.USER_NAME, name);
        }
        if (!phone.equals("Chưa cập nhật") && !phone.isEmpty() && !phone.equals(String.valueOf(user.getMobile()))) {
            long phoneLong = Long.parseLong(phone);
            userHashMap.put(Constants.USER_PHONE, phoneLong);
        }
        if (!uriString.isEmpty()) {
            userHashMap.put(Constants.USER_IMAGE, uriString);
        }
        if (!uriString.isEmpty()) {
            userHashMap.put(Constants.USER_IMAGE, uriString);
        }
        if (!userHashMap.isEmpty()) {
            fireStoreClass.updateUser(this,userHashMap);
        } else {
            Toast.makeText(requireContext(), "Nothing to update", Toast.LENGTH_SHORT).show();
            requireActivity().finish();
        }
    }

    public void setActionBar(@Nullable Toolbar toolbar) {
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back_profile);
        actionBar.setDisplayShowTitleEnabled(false);
    }
    private void addEvents() {
        binding.toolbar.setNavigationOnClickListener(v -> requireActivity().getOnBackPressedDispatcher().onBackPressed());
    }


    public void fetchUserData(User user) {
        this.user = user;
        binding.edtUserNameAccount.setText(user.getName());
        binding.edtEmailAccount.setText(user.getEmail());
        if (user.getMobile() != null) {
            binding.edtPhoneAccount.setText(String.valueOf(user.getMobile()));
        } else {
            binding.edtPhoneAccount.setText("Chưa cập nhật");
        }
        Log.i("FragmentAccount", "fetchUserData: " + user.getImage());
        if (user.getImage() != null) {
            // Load the user image in the ImageView throught img url
            Glide.with(requireContext())
                    .load(user.getImage())
                    .into(binding.imvUserImageAccount);
        } else {
            Glide.with(requireContext())
                    .load(R.drawable.ic_user_profile_placeholder)
                    .into(binding.imvUserImageAccount);
        }
    }

    public void updateUserSuccess() {
        // back to fragment Profile
        confirmUpdateDialog("Cập nhật thành công", "Cập nhật thông tin thành công", "Đóng", "");
    }
}