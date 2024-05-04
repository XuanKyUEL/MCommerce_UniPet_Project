package com.unipet7.mcommerce.firebase;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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
import com.unipet7.mcommerce.activities.SearchProductList;
import com.unipet7.mcommerce.activities.SignUp;
import com.unipet7.mcommerce.adapters.CartAdapter;
import com.unipet7.mcommerce.adapters.FavProductInterface;
import com.unipet7.mcommerce.fragments.FragmentAccount;
import com.unipet7.mcommerce.fragments.FragmentAllProduct;
import com.unipet7.mcommerce.fragments.Fragment_Wishlist_Product;
import com.unipet7.mcommerce.fragments.Home;
import com.unipet7.mcommerce.fragments.Profile;
import com.unipet7.mcommerce.models.Product;
import com.unipet7.mcommerce.models.ProductCart;
import com.unipet7.mcommerce.models.User;
import com.unipet7.mcommerce.models.Voucher;
import com.unipet7.mcommerce.utils.Constants;
import com.unipet7.mcommerce.utils.LoadingDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
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
                        }
                        for (Product product1 : allProducts) {
                            if (userFav().contains(product1.getProductId())) {
                                product1.setIsFavorite(true);
                            }
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
                            assert product != null;
                            allProducts.add(product);
                            for (Product product1 : allProducts) {
                                if (userFav().contains(product1.getProductId())) {
                                    product1.setIsFavorite(true);
                                }
                            }
                            Log.i("FireStoreClass", "getAllProducts: " + product.getProductname());
                        }
                        activity.configAdaptersBlog();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "getAllProducts: ", e);
                });
    }

    public void getRandomProductsBlog(BlogDetails activity, ArrayList<Product> randomProducts, int limit) {
        UniPetdb.collection(Constants.PRODUCTS)
                .limit(limit) // Giới hạn số lượng sản phẩm trả về
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                        List<DocumentSnapshot> randomDocuments = getRandomDocuments(documents, limit);

                        for (DocumentSnapshot document : randomDocuments) {
                            Product product = document.toObject(Product.class);
                            if (product != null) {
                                randomProducts.add(product);
                                if (userFav().contains(product.getProductId())) {
                                    product.setIsFavorite(true);
                                }
                                Log.i("FireStoreClass", "getRandomProducts: " + product.getProductname());
                            }
                        }
                        activity.configAdaptersBlog();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "getRandomProducts: ", e);
                });
    }

    private List<DocumentSnapshot> getRandomDocuments(List<DocumentSnapshot> documents, int limit) {
        List<DocumentSnapshot> randomDocuments = new ArrayList<>();
        Random random = new Random();
        int size = documents.size();
        if (size <= limit) {
            return documents;
        }
        Set<Integer> indexes = new HashSet<>();
        while (indexes.size() < limit) {
            indexes.add(random.nextInt(size));
        }
        for (int index : indexes) {
            randomDocuments.add(documents.get(index));
        }
        return randomDocuments;
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
    public void getDetailProducts(DetailProduct activity, ArrayList<Product> allProducts) {
        getFavList(favList -> UniPetdb.collection(Constants.PRODUCTS)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (int i = 0; i < queryDocumentSnapshots.size(); i++) {
                            Product product = queryDocumentSnapshots.getDocuments().get(i).toObject(Product.class);
                            product.isFavoriteProduct(favList);
                            allProducts.add(product);
                            Log.i("FireStoreClass", "getDetailProducts: " + product.getProductname());
                        }
                        activity.configAdaptersProductDetail();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "getDetailProducts: ", e);
                }));

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
                            productsSale.add(product);
                        }
                        for (Product product1 : productsSale) {
                            if (userFav().contains(product1.getProductId())) {
                                product1.setIsFavorite(true);
                            }
                        }
                    }
                    home.loadSalesProducts(productsSale);
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "getSalesProducts: ", e);
                });
    }

    public void getProductsByCategoryIdHome(Home home, int categoryId) {
        ArrayList<Product> productsList = new ArrayList<>();
        UniPetdb.collection(Constants.PRODUCTS)
                .whereEqualTo(Constants.CATEGORYID, categoryId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (int i = 0; i < queryDocumentSnapshots.size(); i++) {
                            Product product = queryDocumentSnapshots.getDocuments().get(i).toObject(Product.class);
                            Log.i("FireStoreClass", "getProductsByCategoryIdHome: " + product.getProductId() + " " + userFav());
                            productsList.add(product);
                        }
                        for (Product product1 : productsList) {
                            if (userFav().contains(product1.getProductId())) {
                                product1.setIsFavorite(true);
                            }
                        }
                    }
                    home.loadProductsByCategoryId(productsList, categoryId);
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
                        if (userFav().contains(productId)) {
                            assert product != null;
                            product.setIsFavorite(true);
                        }
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

    public void addToCart(String userId, double productId, String productName, double productPrice, double numOfProduct ,String productImageUrl) {
        String currentUserId = getCurrentUID();
        DocumentReference userRef = UniPetdb.collection("users").document(currentUserId);
        userRef.get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        User user = documentSnapshot.toObject(User.class);
                        if (user != null) {
                            String userID = user.getId();
                            CollectionReference cartRef = UniPetdb.collection("cart");
                            DocumentReference newCartItemRef = cartRef.document();
                            HashMap<String, Object> cartItem = new HashMap<>();
                            cartItem.put("userId", currentUserId);
                            cartItem.put("productId", productId);
                            cartItem.put("productPrice", productPrice);
                            cartItem.put("productImageUrl", productImageUrl);
                            cartItem.put("productName", productName);
                            cartItem.put("numOfProduct", numOfProduct);
                            cartItem.put("totalPrice", productPrice*numOfProduct);
                            UniPetdb.collection("cart")
                                    .whereEqualTo("productId", productId)
                                    .whereEqualTo("userId", currentUserId)
                                    .get()
                                    .addOnSuccessListener(queryDocumentSnapshots -> {
                                        if (!queryDocumentSnapshots.isEmpty()) {
                                            DocumentSnapshot doc = queryDocumentSnapshots.getDocuments().get(0);
                                            int currentNumOfProduct = doc.getLong("numOfProduct").intValue();
                                            double currentTotalPrice = doc.getDouble("totalPrice");
                                            cartItem.put("numOfProduct", currentNumOfProduct + numOfProduct);
                                            cartItem.put("totalPrice", currentTotalPrice + productPrice*numOfProduct);
                                            doc.getReference().set(cartItem, SetOptions.merge());
                                        } else {
                                            newCartItemRef.set(cartItem);
                                        }
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.e("FireStoreClass", "Error checking cart for existing product", e);
                                    });
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "Error getting user information", e);
                });
    }
    public void getCartItemsRealtime(Context context, CartAdapter.OnQuantityChangeListener listener) {
        String currentUserId = getCurrentUID();
        if (currentUserId != null && !currentUserId.isEmpty()) {
            UniPetdb.collection("cart")
                    .whereEqualTo("userId", currentUserId)
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
                                    Log.d("Firestore", "productCart userId: " + productCart.getUserId());
                                    Log.d("Firestore", "currentUserId: " + currentUserId);
                                    if (productCart.getUserId().equals(currentUserId)) {
                                        cartItems.add(productCart);
                                    }
                                } else {
                                    Log.e("Firestore", "productCart is null");
                                }
                            }
                            CartAdapter adapter = new CartAdapter(context, cartItems);
                            adapter.setOnQuantityChangeListener(listener);
                        } else {
                            Log.e("Firestore", "value is null");
                        }
                    });
        } else {
            Log.e("FireStoreClass", "Current user ID is null or empty");
        }
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
                                        Log.d("FireStoreClass", "Xóa sản phẩm khỏi cơ sở dữ liệu thành công");
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.e("FireStoreClass", "Lỗi khi xóa sản phẩm khỏi cơ sở dữ liệu", e);
                                    });
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "Error checking cart for existing product", e);
                });
    }
    public void getAllVouchers(OnVoucherListListener listener) {
        UniPetdb.collection("vouchers")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    ArrayList<Voucher> vouchers = new ArrayList<>();
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        Voucher voucher = document.toObject(Voucher.class);
                        vouchers.add(voucher);
                    }
                    listener.onSuccess(vouchers);
                })
                .addOnFailureListener(e -> {
                    listener.onFailure("Error fetching vouchers: " + e.getMessage());
                });
    }
    public interface OnVoucherListListener {
        void onSuccess(ArrayList<Voucher> vouchers);
        void onFailure(String errorMessage);
    }


    public List<Integer> userFav() {
        AtomicReference<List<Integer>> favProducts = new AtomicReference<>(new ArrayList<>());
        UniPetdb.collection(Constants.USERS)
                .document(getCurrentUID())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        favProducts.set((List<Integer>) documentSnapshot.get(Constants.FAVPRODUCTID));
                        Log.i("FireStoreClass", "userFav: " + favProducts);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "userFav: ", e);
                });
        return favProducts.get();
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
                                                assert product != null;
                                                product.setIsFavorite(true);
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
                        }
                        loadingDialog.dissmis();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "getUserFavorites: ", e);
                });
    }

    public void getFavList(FavProductInterface callback) {
        UniPetdb.collection(Constants.USERS)
                .document(getCurrentUID())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<Integer> favList = (List<Integer>) documentSnapshot.get(Constants.FAVPRODUCTID);
                        callback.onDataLoaded(favList);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "getFavList: ", e);
                });
    }
    public void getSalesPFilter(SearchProductList searchProductList) {
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
                    searchProductList.loadSalesProducts(productsSale);
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "getSalesProducts: ", e);
                });
    }
    public void SearchAllProducts(SearchProductList searchProductList, ArrayList<Product> allProducts) {
        UniPetdb.collection(Constants.PRODUCTS)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (int i = 0; i < queryDocumentSnapshots.size(); i++) {
                            Product product = queryDocumentSnapshots.getDocuments().get(i).toObject(Product.class);
                            allProducts.add(product);
                            Log.i("FireStoreClass", "SearchAllProducts: " + product.getProductname());
                        }
                    }
                    searchProductList.configAdaptersSearch();
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "SearchAllProducts: ", e);
                });
    }
}
