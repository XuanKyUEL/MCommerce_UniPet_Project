package com.unipet7.mcommerce.firebase;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.unipet7.mcommerce.activities.DetailProduct;
import com.unipet7.mcommerce.activities.BlogDetails;
import com.unipet7.mcommerce.activities.SignUp;
import com.unipet7.mcommerce.adapters.FavProductInterface;
import com.unipet7.mcommerce.fragments.FragmentAccount;
import com.unipet7.mcommerce.fragments.FragmentAllProduct;
import com.unipet7.mcommerce.fragments.Fragment_Wishlist_Product;
import com.unipet7.mcommerce.fragments.Home;
import com.unipet7.mcommerce.fragments.Profile;
import com.unipet7.mcommerce.models.Product;
import com.unipet7.mcommerce.models.User;
import com.unipet7.mcommerce.utils.Constants;
import com.unipet7.mcommerce.utils.LoadingDialog;

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
                    fragment.divideProduct();
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
    public ArrayList<Product> getAllProductFrag (Fragment fragment) {
        ArrayList<Product> allProducts = new ArrayList<>();
        UniPetdb.collection(Constants.PRODUCTS)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (int i = 0; i < queryDocumentSnapshots.size(); i++) {
                            Product product = queryDocumentSnapshots.getDocuments().get(i).toObject(Product.class);
                            allProducts.add(product);
                        }
                        if (fragment instanceof FragmentAllProduct) {
                            FragmentAllProduct fragmentAllProduct = (FragmentAllProduct) fragment;
                            fragmentAllProduct.loadProduct(allProducts);
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "getAllProducts: ", e);
                });
        return allProducts;
    }

    public void getSalesProducts(Home home) {
        ArrayList<Product> productsSale = new ArrayList<>();
        UniPetdb.collection(Constants.PRODUCTS)
                .whereGreaterThan(Constants.SALESPERCENT, 0)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (int i = 0; i < queryDocumentSnapshots.size(); i++) {
                            Product product = queryDocumentSnapshots.getDocuments().get(i).toObject(Product.class);
                            assert product != null;
                            productsSale.add(product);
                        }
                    }
                    home.loadSalesProducts(productsSale);
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "getSalesProducts: ", e);
                });
    }

    public void getProductsByCategoryIdHome(Home home, int categoryId) {
        ArrayList<Product> products = new ArrayList<>();
        UniPetdb.collection(Constants.PRODUCTS)
                .whereEqualTo(Constants.CATEGORYID, categoryId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (int i = 0; i < queryDocumentSnapshots.size(); i++) {
                            Product product = queryDocumentSnapshots.getDocuments().get(i).toObject(Product.class);
                            assert product != null;
                            products.add(product);
                        }
                    }
                    home.loadProductsByCategoryId(products, categoryId);
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "getProductsByCategoryId: ", e);
                });
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

    public void addFavorite(Context context,int fvProductId) {
        UniPetdb.collection(Constants.USERS)
                .document(getCurrentUID())
                .update(Constants.FAVPRODUCTID, FieldValue.arrayUnion(fvProductId))
                .addOnSuccessListener(aVoid -> {
                    Log.i("FireStoreClass", "addFavorite: Thành công");
                    Toast.makeText(context, "Thêm sản phẩm vào yêu thích thành công", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "addFavorite: ", e);
                });
    }

    public void removeFavorite(Context context, int fvProductId) {
        UniPetdb.collection(Constants.USERS)
                .document(getCurrentUID())
                .update(Constants.FAVPRODUCTID, FieldValue.arrayRemove(fvProductId))
                .addOnSuccessListener(aVoid -> {
                    Log.i("FireStoreClass", "removeFavorite: Thành công");
                    Toast.makeText(context, "Xóa sản phẩm khỏi yêu thích thành công", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "removeFavorite: ", e);
                });
    }

    public void getFavoriteList(FavProductInterface callbak) {
        UniPetdb.collection(Constants.USERS)
                .document(getCurrentUID())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<Integer> favoriteList = (List<Integer>) documentSnapshot.get(Constants.FAVPRODUCTID);
                        callbak.onDataLoaded(favoriteList);
                        Log.i("FireStoreClass", "getFavoriteList: " + favoriteList);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "getFavoriteList: ", e);
                });
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

    public void addToFavoriteFrag(Fragment fragment, int productId) {
        UniPetdb.collection(Constants.USERS)
                .document(getCurrentUID())
                .update(Constants.FAVPRODUCTID, FieldValue.arrayUnion(productId))
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
                    userFav();
                    // if fragment instance of Home and allProduct then send notification wishlist fragment to update
                    if (fragment instanceof Fragment_Wishlist_Product) {
                        Fragment_Wishlist_Product fragment_wishlist_product = (Fragment_Wishlist_Product) fragment;
                        fragment_wishlist_product.loadFavoriteProducts(userFav());
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "addToFavorite: ", e);
                });
    }

    public void removeFromFavoriteFrag(Fragment fragment, int productId) {
        UniPetdb.collection(Constants.USERS)
                .document(getCurrentUID())
                .update(Constants.FAVPRODUCTID, FieldValue.arrayRemove(productId))
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, "Đã xóa khỏi danh sách yêu thích", Toast.LENGTH_SHORT).show();
                    userFav();
                    if (fragment instanceof Fragment_Wishlist_Product) {
                        Fragment_Wishlist_Product fragment_wishlist_product = (Fragment_Wishlist_Product) fragment;
                        fragment_wishlist_product.loadFavoriteProducts(userFav());
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "removeFromFavorite: ", e);
                });
    }

    public ArrayList<Product> userFav() {
        ArrayList<Product> favProducts = new ArrayList<>();
        UniPetdb.collection(Constants.USERS)
                .document(getCurrentUID())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<Integer> favProductIds = (List<Integer>) documentSnapshot.get(Constants.FAVPRODUCTID);
                        if (favProductIds != null) {
                            for (int i = 0; i < favProductIds.size(); i++) {
                                UniPetdb.collection(Constants.PRODUCTS)
                                        .document(String.valueOf(favProductIds.get(i)))
                                        .get()
                                        .addOnSuccessListener(documentSnapshot1 -> {
                                            if (documentSnapshot1.exists()) {
                                                Product product = documentSnapshot1.toObject(Product.class);
                                                favProducts.add(product);
                                            }
                                        })
                                        .addOnFailureListener(e -> {
                                            Log.e("FireStoreClass", "getUserFavorites: ", e);
                                        });
                            }
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "getUserFavorites: ", e);
                });
        return favProducts;
    }

    public void getUserFavorites(Fragment fragment, ArrayList<Product> favProducts) {
        LoadingDialog loadingDialog = new LoadingDialog();
        loadingDialog.showLoadingDialog(fragment.getContext());
        UniPetdb.collection(Constants.USERS)
                .document(getCurrentUID())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<Integer> favProductIds = (List<Integer>) documentSnapshot.get(Constants.FAVPRODUCTID);
                        if (favProductIds != null) {
                            for (int i = 0; i < favProductIds.size(); i++) {
                                UniPetdb.collection(Constants.PRODUCTS)
                                        .document(String.valueOf(favProductIds.get(i)))
                                        .get()
                                        .addOnSuccessListener(documentSnapshot1 -> {
                                            if (documentSnapshot1.exists()) {
                                                Product product = documentSnapshot1.toObject(Product.class);
                                                favProducts.add(product);
                                            }
                                            if (fragment instanceof Fragment_Wishlist_Product) {
                                                Fragment_Wishlist_Product fragment_wishlist_product = (Fragment_Wishlist_Product) fragment;
                                                fragment_wishlist_product.loadFavoriteProducts(favProducts);
                                            }
                                        }
                                        )
                                        .addOnFailureListener(e -> {
                                            Log.e("FireStoreClass", "getUserFavorites: ", e);
                                        });
                            }
                            loadingDialog.dissmis();
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "getUserFavorites: ", e);
                });
    }
}
