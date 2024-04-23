package com.unipet7.mcommerce.firebase;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.unipet7.mcommerce.activities.DetailProduct;
import com.unipet7.mcommerce.activities.BlogDetails;
import com.unipet7.mcommerce.activities.SignUp;
import com.unipet7.mcommerce.fragments.FragmentAccount;
import com.unipet7.mcommerce.fragments.FragmentAllProduct;
import com.unipet7.mcommerce.fragments.Fragment_Wishlist_Product;
import com.unipet7.mcommerce.fragments.Home;
import com.unipet7.mcommerce.fragments.Profile;
import com.unipet7.mcommerce.models.Product;
import com.unipet7.mcommerce.models.User;
import com.unipet7.mcommerce.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class FireStoreClass {
    private final FirebaseFirestore UniPetdb = FirebaseFirestore.getInstance();

    private StorageReference sRef = FirebaseStorage.getInstance().getReference();
    private User currentUser = null;

    Context context;

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

    public void addFavorite(int fvProductId) {
        UniPetdb.collection(Constants.USERS)
                .document(getCurrentUID())
                .update(Constants.FAVPRODUCTID, FieldValue.arrayUnion(fvProductId))
                .addOnSuccessListener(aVoid -> {
                    Log.i("FireStoreClass", "addFavorite: Thành công");
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "addFavorite: ", e);
                });
    }

    public void removeFavorite(int fvProductId) {
        UniPetdb.collection(Constants.USERS)
                .document(getCurrentUID())
                .update(Constants.FAVPRODUCTID, FieldValue.arrayRemove(fvProductId))
                .addOnSuccessListener(aVoid -> {
                    Log.i("FireStoreClass", "removeFavorite: Thành công");
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "removeFavorite: ", e);
                });
    }

    public void findProductByName(String fvProductName) {
        // query product id by name
        UniPetdb.collection(Constants.PRODUCTS)
                .whereEqualTo(Constants.PRODUCTNAME, fvProductName)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        int fvProductId = Objects.requireNonNull(queryDocumentSnapshots.getDocuments().get(0).getLong(Constants.PRODUCT_ID)).intValue();
                        // if fvProductId is in favorite list, remove it, else add it
                        if (getFavoriteList().contains(fvProductId)) {
                            removeFavorite(fvProductId);
                        } else {
                            addFavorite(fvProductId);
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "addFavoriteByName: ", e);
                });
    }

    public List<Integer> getFavoriteList() {
        AtomicReference<List<Integer>> favoriteList = new AtomicReference<>(new ArrayList<>());
        UniPetdb.collection(Constants.USERS)
                .document(getCurrentUID())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        favoriteList.set((List<Integer>) documentSnapshot.get(Constants.FAVPRODUCTID));
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "getFavoriteList: ", e);
                });
        return favoriteList.get();
    }

    public ArrayList<Product> getFavoriteProducts() {
        ArrayList<Product> favoriteProducts = new ArrayList<>();
        UniPetdb.collection(Constants.USERS)
                .document(getCurrentUID())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<Integer> favoriteList = (List<Integer>) documentSnapshot.get(Constants.FAVPRODUCTID);
                        if (favoriteList != null) {
                            for (int i = 0; i < favoriteList.size(); i++) {
                                UniPetdb.collection(Constants.PRODUCTS)
                                        .document(String.valueOf(favoriteList.get(i)))
                                        .get()
                                        .addOnSuccessListener(documentSnapshot1 -> {
                                            if (documentSnapshot1.exists()) {
                                                Product product = documentSnapshot1.toObject(Product.class);
                                                favoriteProducts.add(product);
                                            }
                                        })
                                        .addOnFailureListener(e -> {
                                            Log.e("FireStoreClass", "getFavoriteProducts: ", e);
                                        });
                            }
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "getFavoriteProducts: ", e);
                });
        return favoriteProducts;
    }

    public void getFavoriteList(Fragment_Wishlist_Product wishlistF) {
        UniPetdb.collection(Constants.USERS)
                .document(getCurrentUID())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        wishlistF.loadFavoriteList(getFavoriteProducts());
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "getFavoriteList: ", e);
                });
    }
}
