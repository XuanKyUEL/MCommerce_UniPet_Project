package com.unipet7.mcommerce.firebase;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unipet7.mcommerce.utils.Constants;

import java.util.List;

public class CheckFavoriteHelper {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void checkUserFavorite(String userId, String productId, checkFavoriteCallback callback) {
        db.collection(Constants.PRODUCTS)
                .document(productId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            List<String> favList = (List<String>) document.get(Constants.FAVORITEBY);
                            if (favList != null) {
                                callback.onCheckFavorite(favList.contains(userId));
                            } else {
                                callback.onCheckFavorite(false);
                            }
                        } else {
                            callback.onCheckFavorite(false);
                        }
                    } else {
                        callback.onCheckFavorite(false);
                    }
                });
    }

    public interface checkFavoriteCallback {
        void onCheckFavorite(boolean isFavorite);
    }
}
