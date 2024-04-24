package com.unipet7.mcommerce.firebase;

import static com.google.common.io.Files.getFileExtension;

import android.net.Uri;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.unipet7.mcommerce.activities.DetailProduct;
import com.unipet7.mcommerce.activities.BlogDetails;
import com.unipet7.mcommerce.activities.SignUp;
import com.unipet7.mcommerce.fragments.FragmentAccount;
import com.unipet7.mcommerce.fragments.FragmentAllProduct;
import com.unipet7.mcommerce.fragments.Home;
import com.unipet7.mcommerce.fragments.Profile;
import com.unipet7.mcommerce.models.Product;
import com.unipet7.mcommerce.models.User;
import com.unipet7.mcommerce.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;

public class FireStoreClass {
    private final FirebaseFirestore UniPetdb = FirebaseFirestore.getInstance();

    private StorageReference sRef = FirebaseStorage.getInstance().getReference();
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
                        } else if ((fragment instanceof FragmentAccount)) {
                            FragmentAccount fragmentAccount = (FragmentAccount) fragment;
                            fragmentAccount.fetchUserData(user);
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "loadUserUI: ", e);
                });
    }

    public void getAllProducts(FragmentAllProduct fragment, ArrayList<Product> allProducts) {
        UniPetdb.collection(Constants.PRODUCTS)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (int i = 0; i < queryDocumentSnapshots.size(); i++) {
                            Product product = queryDocumentSnapshots.getDocuments().get(i).toObject(Product.class);
                            allProducts.add(product);
                            Log.i("FireStoreClass", "getAllProducts: " + product.getProductname());
                        }
                    }
                    fragment.divideProduct(allProducts);
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "getAllProducts: ", e);
                });
    }
    public void getAllProductsBlog(BlogDetails activity, ArrayList<Product> allProducts) {
        UniPetdb.collection(Constants.PRODUCTS)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (int i = 0; i < queryDocumentSnapshots.size(); i++) {
                            Product product = queryDocumentSnapshots.getDocuments().get(i).toObject(Product.class);
                            allProducts.add(product);
                            Log.i("FireStoreClass", "getAllProducts: " + product.getProductname());
                        }
                        activity.configAdaptersBlog();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "getAllProducts: ", e);
                });
    }
    public void getProductList(Home home, ArrayList<Product> productsSale, ArrayList<Product> productsDog, ArrayList<Product> productsCat) {

        UniPetdb.collection(Constants.PRODUCTS)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (int i = 0; i < queryDocumentSnapshots.size(); i++) {
                            Product product = queryDocumentSnapshots.getDocuments().get(i).toObject(Product.class);
                            if (product.getSalepercent() > 0) {
                                productsSale.add(product);
                            }
                            if (product.getCategoryId() == 1) {
                                productsDog.add(product);
                            }
                            if (product.getCategoryId() == 2) {
                                productsCat.add(product);
                            }
                        }
                        home.configAdapters();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "getProductList: ", e);
                })
        ;
    }

    public void getProductDetailViaId(DetailProduct detailProduct, int productId) {
        UniPetdb.collection(Constants.PRODUCTS)
                .document(String.valueOf(productId))
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Product product = documentSnapshot.toObject(Product.class);
                        detailProduct.loadProductDetail(product);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "getProductDetail: ", e);
                });
    }

    public void updateUser(Fragment fragment, HashMap<String, Object> userHashMap) {
        UniPetdb.collection(Constants.USERS)
                .document(getCurrentUID())
                .update(userHashMap)
                .addOnSuccessListener(aVoid -> {
                    Log.i("FireStoreClass", "updateUser: Thành công");
                    if (fragment instanceof FragmentAccount) {
                        FragmentAccount fragmentAccount = (FragmentAccount) fragment;
                        fragmentAccount.updateUserSuccess();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "updateUser: ", e);
                });
    }

//    public void uploadImageToStorage(FragmentAccount fragmentAccount, Uri userAvatarUri) {
//        sRef = FirebaseStorage.getInstance()
//                .getReference(Constants.USER_AVATAR)
//                .child(Constants.USER_AVATAR + System.currentTimeMillis() + Uri.parse(userAvatarUri.toString()).getLastPathSegment());
//        sRef.putFile(userAvatarUri)
//                .addOnSuccessListener(taskSnapshot -> {
//                    taskSnapshot.getStorage().getDownloadUrl()
//                            .addOnSuccessListener(uri -> {
//                                fragmentAccount.uploadImageSuccess(uri.toString());
//                            });
//                })
//                .addOnFailureListener(e -> {
//                    Log.e("FireStoreClass", "uploadImageToStorage: ", e);
//                });
//    }



    public void addToCart(String productName, double productPrice, String productImage) {
        // Lấy reference đến collection "cart"
        CollectionReference cartRef = UniPetdb.collection("cart");

        // Tạo một document mới trong collection "cart"
        DocumentReference newCartItemRef = cartRef.document();

        // Tạo một đối tượng HashMap chứa thông tin sản phẩm
        HashMap<String, Object> cartItem = new HashMap<>();
        cartItem.put("productName", productName);
        cartItem.put("productPrice", productPrice);
        cartItem.put("productImage", productImage);
        cartItem.put("numbProduct", 1);
        cartItem.put("totalPrice", productPrice);

        // Thêm thông tin sản phẩm vào document mới
        newCartItemRef.set(cartItem)
                .addOnSuccessListener(aVoid -> {
                    // Thêm thành công
                    Log.i("FireStoreClass", "addToCart: Thành công");
                })
                .addOnFailureListener(e -> {
                    // Xảy ra lỗi
                    Log.e("FireStoreClass", "addToCart: Fail", e);
                });
    }
}


