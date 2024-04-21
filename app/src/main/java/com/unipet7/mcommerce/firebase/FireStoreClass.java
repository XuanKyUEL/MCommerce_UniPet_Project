package com.unipet7.mcommerce.firebase;

import android.util.Log;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.unipet7.mcommerce.activities.SignUp;
import com.unipet7.mcommerce.fragments.Home;
import com.unipet7.mcommerce.fragments.Profile;
import com.unipet7.mcommerce.models.User;
import com.unipet7.mcommerce.utils.Constants;

public class FireStoreClass {
    private final FirebaseFirestore UniPetdb = FirebaseFirestore.getInstance();
    private User currentUser = null;

    public String getCurrentUID() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUID = "";
        if (currentUser != null) {
            currentUID = currentUser.getUid();
        }
        return currentUID;
    }

    public void addUserToFirebaseDB(SignUp activity, User userInfo) {
        Log.i("FireStoreClassIsCalled", "addUserToFirebaseDB: " + userInfo.getId());
        UniPetdb.collection(Constants.USERS).
                document(userInfo.getId())
                .set(userInfo, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {
                    activity.userRegisteredSuccess();
                    Log.i("FireStoreClass", "addUserToFirebaseDB: Thành công");
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "addUserToFirebaseDB: ", e);
                    activity.resetAnimation();
                    activity.signUpDialog("Đăng ký thất bại", "Lỗi hệ thống đăng ký, vui lòng thử lại sau.", "", "Đóng");
                });
    }

    public void loadLoggedUserUI(Fragment fragment) {
        UniPetdb.collection(Constants.USERS)
                .document(getCurrentUID())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        User user = documentSnapshot.toObject(User.class);
                        if ((fragment instanceof Home)) {
                            Home home = (Home) fragment;
                            home.greeting(user);
                        } else if ((fragment instanceof Profile)) {
                            Profile profile = (Profile) fragment;
                            profile.loadUserData(user);
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "loadUserUI: ", e);
                });
    }
}
