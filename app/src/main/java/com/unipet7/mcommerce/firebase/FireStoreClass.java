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
import com.unipet7.mcommerce.fragments.CartOverall;
import com.unipet7.mcommerce.fragments.FragmentAccount;
import com.unipet7.mcommerce.fragments.FragmentAddress;
import com.unipet7.mcommerce.fragments.FragmentAddressEdit;
import com.unipet7.mcommerce.fragments.FragmentAdressAdd;
import com.unipet7.mcommerce.fragments.FragmentAllProduct;
import com.unipet7.mcommerce.fragments.Fragment_Wishlist_Product;
import com.unipet7.mcommerce.fragments.Home;
import com.unipet7.mcommerce.fragments.Profile;
import com.unipet7.mcommerce.models.Addresses;
import com.unipet7.mcommerce.models.Product;
import com.unipet7.mcommerce.models.ProductCart;
import com.unipet7.mcommerce.models.User;
import com.unipet7.mcommerce.models.Voucher;
import com.unipet7.mcommerce.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class FireStoreClass {
    private final FirebaseFirestore UniPetdb = FirebaseFirestore.getInstance();

    private StorageReference sRef = FirebaseStorage.getInstance().getReference();

    private List<FavoriteChangeListener> favoriteChangeListeners = new ArrayList<>();

    public void addFavoriteChangeListener(FavoriteChangeListener listener) {
        favoriteChangeListeners.add(listener);
    }

    public void removeFavoriteChangeListener(FavoriteChangeListener listener) {
        favoriteChangeListeners.remove(listener);
    }

    private void notifyFavoriteChange() {
        for (FavoriteChangeListener listener : favoriteChangeListeners) {
            listener.onFavoriteChange();
        }
    }
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

    public void getDetailProducts(DetailProduct activity, ArrayList<Product> allProducts) {
        UniPetdb.collection(Constants.PRODUCTS)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (int i = 0; i < queryDocumentSnapshots.size(); i++) {
                            Product product = queryDocumentSnapshots.getDocuments().get(i).toObject(Product.class);
                            allProducts.add(product);
                            Log.i("FireStoreClass", "getDetailProducts: " + product.getProductname());
                        }
                        activity.configAdaptersProductDetail();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "getDetailProducts: ", e);
                });
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

    public int getCartItemCount(CartOverall cartOverall) {
        AtomicInteger cartCount = new AtomicInteger();
        UniPetdb.collection(Constants.CART)
                .whereEqualTo(Constants.USER_ID, getCurrentUID())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    cartCount.set(queryDocumentSnapshots.size());
                    cartOverall.loadCart = cartCount.get() > 0;
                    cartOverall.loadUi(cartOverall.loadCart);
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "updateCartCount: ", e);
                });
        return cartCount.get();
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

    public void addFavorite(Context context, int fvProductId) {
        UniPetdb.collection(Constants.PRODUCTS)
                .whereEqualTo(Constants.PRODUCT_ID, fvProductId)
                // thêm vào 1 field có thên là isFavoriteBy chứa danh sách id của user
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot doc = queryDocumentSnapshots.getDocuments().get(0);
                        List<String> favoriteBy = (List<String>) doc.get(Constants.FAVORITEBY);
                        if (favoriteBy != null && !favoriteBy.contains(getCurrentUID())) {
                            doc.getReference().update(Constants.FAVORITEBY, FieldValue.arrayUnion(getCurrentUID()));
                            notifyFavoriteChange();
                            Toast.makeText(context, "Thêm sản phẩm vào yêu thích thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "addFavorite: ", e);
                });
    }

    public void removeFavorite(Context context, int fvProductId) {
        UniPetdb.collection(Constants.PRODUCTS)
                .whereEqualTo(Constants.PRODUCT_ID, fvProductId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot doc = queryDocumentSnapshots.getDocuments().get(0);
                        doc.getReference().update(Constants.FAVORITEBY, FieldValue.arrayRemove(getCurrentUID()));
                        notifyFavoriteChange();
                        Toast.makeText(context, "Xóa sản phẩm khỏi yêu thích thành công", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "removeFavorite: ", e);
                });
    }

    public void addToCart(double productId, String productName, double productPrice, double numOfProduct , String productImageUrl) {
        String currentUserId = getCurrentUID();
        DocumentReference userRef = UniPetdb.collection(Constants.USERS).document(currentUserId);
        userRef.get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        User user = documentSnapshot.toObject(User.class);
                        if (user != null) {
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
                            UniPetdb.collection(Constants.CART)
                                    .whereEqualTo(Constants.PRODUCT_ID, productId)
                                    .whereEqualTo(Constants.USER_ID, currentUserId)
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
                                        if (context instanceof DetailProduct) {
                                            getCountUserCartItems((DetailProduct) context);
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

    public void getCountUserCartItems(DetailProduct detailProduct) {
        UniPetdb.collection(Constants.CART)
                .whereEqualTo("userId", getCurrentUID())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    int count = queryDocumentSnapshots.size();
                    Log.i("FireStoreClass", "getCountUserCartItems: " + count);
                    if (count > 0) {
                        detailProduct.loadCartCount(count);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "getCountUserCartItems: ", e);
                });
    }
    public void getCartItemsRealtime(Context context, CartAdapter.OnQuantityChangeListener listener) {
        String currentUserId = getCurrentUID();
        if (currentUserId != null && !currentUserId.isEmpty()) {
            UniPetdb.collection(Constants.CART)
                    .whereEqualTo(Constants.USER_ID, currentUserId)
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

    public void getUserFavorites(Fragment_Wishlist_Product fragment, ArrayList<Product> favProducts) {
        UniPetdb.collection(Constants.PRODUCTS)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            Product product = document.toObject(Product.class);
                            List<String> favList = (List<String>) document.get(Constants.FAVORITEBY);
                            if (favList != null && favList.contains(getCurrentUID())) {
                                favProducts.add(product);
                            }
                        }
                        fragment.loadFavoriteProducts(favProducts);
                    } else {
                        Log.e("FireStoreClass", "getUserFavorites: ", task.getException());
                    }
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

    // Address API
    // fetch user address from User collection
    public void GetUserAddresses (FragmentAddress fragment) {
        // return the data in addresses field of the user
        UniPetdb.collection(Constants.USERS)
                .document(getCurrentUID())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        User user = documentSnapshot.toObject(User.class);
                        if (user != null) {
                            fragment.loadAddresses(user.getAddresses());
                            Log.i("FireStoreClass", "GetUserAddresses: " + user.getAddresses());
                        } else {
                            Log.e("FireStoreClass", "GetUserAddresses: User is null");
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "GetUserAddresses: ", e);
                });
    }

    public void addAddress(FragmentAdressAdd fragment, Addresses address) {
        address.setAddressId(System.currentTimeMillis() + new Random().nextInt(1000) + getCurrentUID());
        UniPetdb.collection(Constants.USERS)
                .document(getCurrentUID())
                .update(Constants.ADDRESSES, FieldValue.arrayUnion(address))
                .addOnSuccessListener(aVoid -> {
                    fragment.addAddressSuccess();
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "AddAddress: ", e);
                });
    }

    public void deleteAddress(FragmentAddressEdit fragmentAddressEdit, String addressId) {
        UniPetdb.collection(Constants.USERS)
                .document(getCurrentUID())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    List<Addresses> addresses = documentSnapshot.toObject(User.class).getAddresses();
                    for (int i = 0; i < addresses.size(); i++) {
                        if (addresses.get(i).getAddressId().equals(addressId)) {
                            addresses.remove(i); // Xóa địa chỉ
                            break;
                        }
                    }
                    // Cập nhật lại danh sách địa chỉ trên Firestore
                    UniPetdb.collection(Constants.USERS)
                            .document(getCurrentUID())
                            .update(Constants.ADDRESSES, addresses)
                            .addOnSuccessListener(aVoid -> {
                                fragmentAddressEdit.deleteAddressSuccess();
                            })
                            .addOnFailureListener(e -> {
                                Log.e("FireStoreClass", "deleteAddress: ", e);
                            });
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "deleteAddress: ", e);
                });
    }
    public void updateAddress(FragmentAddressEdit fragmentAddressEdit, Addresses newAddress, String addressId) {
        UniPetdb.collection(Constants.USERS)
                .document(getCurrentUID())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    List<Addresses> addresses = documentSnapshot.toObject(User.class).getAddresses();
                    for (int i = 0; i < addresses.size(); i++) {
                        if (addresses.get(i).getAddressId().equals(addressId)) {
                            addresses.set(i, newAddress); // Cập nhật địa chỉ
                            break;
                        }
                    }
                    // Cập nhật lại danh sách địa chỉ trên Firestore
                    UniPetdb.collection(Constants.USERS)
                            .document(getCurrentUID())
                            .update(Constants.ADDRESSES, addresses)
                            .addOnSuccessListener(aVoid -> {
                                fragmentAddressEdit.updateAddressSuccess();
                            })
                            .addOnFailureListener(e -> {
                                Log.e("FireStoreClass", "updateAddress: ", e);
                            });
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "updateAddress: ", e);
                });
    }
}
