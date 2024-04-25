package com.unipet7.mcommerce.firebase;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.unipet7.mcommerce.activities.DetailProduct;
import com.unipet7.mcommerce.activities.BlogDetails;
import com.unipet7.mcommerce.activities.SignUp;
import com.unipet7.mcommerce.adapters.CartAdapter;
import com.unipet7.mcommerce.fragments.FragmentAccount;
import com.unipet7.mcommerce.fragments.FragmentAllProduct;
import com.unipet7.mcommerce.fragments.Fragment_Wishlist_Product;
import com.unipet7.mcommerce.fragments.Home;
import com.unipet7.mcommerce.fragments.Profile;
import com.unipet7.mcommerce.models.Product;
import com.unipet7.mcommerce.models.ProductCart;
import com.unipet7.mcommerce.models.User;
import com.unipet7.mcommerce.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;
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
    public void getProductBlog(BlogDetails activity, ArrayList<Product> allProducts) {
        UniPetdb.collection(Constants.PRODUCT)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (int i = 0; i < queryDocumentSnapshots.size(); i++) {
                            Product product = queryDocumentSnapshots.getDocuments().get(i).toObject(Product.class);
                            allProducts.add(product);
                        }
                    }
                    activity.configAdaptersBlog();
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "getProductBlog: ", e);
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
    public void addToCart(double productId, String productName, double productPrice, String productImageUrl) {
        String currentUserId = getCurrentUID();

        // Tạo một HashMap để lưu thông tin sản phẩm
        HashMap<String, Object> cartItem = new HashMap<>();
        cartItem.put("productId", productId);
        cartItem.put("productPrice", productPrice);
        cartItem.put("productImageUrl", productImageUrl);
        cartItem.put("productName", productName);
        cartItem.put("numOfProduct", 1); // Mặc định số lượng sản phẩm là 1
        cartItem.put("totalPrice", productPrice); // Tổng giá tiền ban đầu là giá tiền của sản phẩm

        // Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng chưa
        UniPetdb.collection("cart")
                .whereEqualTo("productId", productId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        // Nếu sản phẩm đã tồn tại trong giỏ hàng, tăng số lượng sản phẩm
                        DocumentSnapshot doc = queryDocumentSnapshots.getDocuments().get(0);
                        int currentNumOfProduct = doc.getLong("numOfProduct").intValue();
                        double currentTotalPrice = doc.getDouble("totalPrice");

                        // Cập nhật số lượng sản phẩm và tổng giá tiền mới
                        cartItem.put("numOfProduct", currentNumOfProduct + 1);
                        cartItem.put("totalPrice", currentTotalPrice + productPrice);

                        // Cập nhật thông tin sản phẩm trong giỏ hàng
                        doc.getReference().set(cartItem, SetOptions.merge());
                    } else {
                        // Nếu sản phẩm chưa tồn tại trong giỏ hàng, thêm sản phẩm mới vào giỏ hàng
                        UniPetdb.collection("cart").document().set(cartItem);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "Error checking cart for existing product", e);
                });
    }



    public void getCartItemsRealtime(Context context, CartAdapter.OnQuantityChangeListener listener) {
        UniPetdb.collection("cart")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.e("FireStoreClass", "Error listening for cart updates", error);
                        return;
                    }
                    if (value != null) {
                        ArrayList<ProductCart> cartItems = new ArrayList<>();
                        for (DocumentSnapshot document : value.getDocuments()) {
                            ProductCart productCart = document.toObject(ProductCart.class);
                            if (productCart != null) {
                                cartItems.add(productCart);
                            }
                        }

                        // Gửi danh sách sản phẩm về adapter
                        CartAdapter adapter = new CartAdapter(context, cartItems);
                        adapter.setOnQuantityChangeListener(listener);
                    }
                });
    }
    public void deleteCartItem(String productId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("cart")
                .whereEqualTo("productId", Double.parseDouble(productId))
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            document.getReference().delete()
                                    .addOnSuccessListener(aVoid -> {
                                        // Xóa thành công
                                        Log.d("FireStoreClass", "Xóa sản phẩm khỏi cơ sở dữ liệu thành công");
                                    })
                                    .addOnFailureListener(e -> {
                                        // Xảy ra lỗi khi xóa
                                        Log.e("FireStoreClass", "Lỗi khi xóa sản phẩm khỏi cơ sở dữ liệu", e);
                                    });
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "Error checking cart for existing product", e);
                });
    }




}
