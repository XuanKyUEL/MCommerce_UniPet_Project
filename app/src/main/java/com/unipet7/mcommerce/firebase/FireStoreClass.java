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
import com.unipet7.mcommerce.utils.Constants;
import com.unipet7.mcommerce.utils.LoadingDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        getFavList(favList -> UniPetdb.collection(Constants.PRODUCTS)
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
                }));

    }
    public void getAllProductsBlog(BlogDetails activity, ArrayList<Product> allProducts) {
        getFavList(favList -> UniPetdb.collection(Constants.PRODUCTS)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (int i = 0; i < queryDocumentSnapshots.size(); i++) {
                            Product product = queryDocumentSnapshots.getDocuments().get(i).toObject(Product.class);
                            product.isFavoriteProduct(favList);
                            allProducts.add(product);
                            Log.i("FireStoreClass", "getAllProducts: " + product.getProductname());
                        }
                        activity.configAdaptersBlog();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "getAllProducts: ", e);
                }));

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
        getFavList(favList -> UniPetdb.collection(Constants.PRODUCTS)
                .whereGreaterThan(Constants.SALESPERCENT, 0)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (int i = 0; i < queryDocumentSnapshots.size(); i++) {
                            Product product = queryDocumentSnapshots.getDocuments().get(i).toObject(Product.class);
                            assert product != null;
                            product.isFavoriteProduct(favList);
                            productsSale.add(product);
                        }
                    }
                    home.loadSalesProducts(productsSale);
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreClass", "getSalesProducts: ", e);
                }));
    }

    public void getProductsByCategoryIdHome(Home home, int categoryId) {
        ArrayList<Product> productsList = new ArrayList<>();
        getFavList(favList -> UniPetdb.collection(Constants.PRODUCTS)
                .whereEqualTo(Constants.CATEGORYID, categoryId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (int i = 0; i < queryDocumentSnapshots.size(); i++) {
                            Product product = queryDocumentSnapshots.getDocuments().get(i).toObject(Product.class);
                            assert product != null;
                            product.isFavoriteProduct(favList);
                            productsList.add(product);
                        }
                    }
                    home.loadProductsByCategoryId(productsList, categoryId);
                }));
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
                .whereEqualTo(Constants.FAVPRODUCTID, fvProductId)
                .get()
                // check if fvProductId is already in the list
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        UniPetdb.collection(Constants.USERS)
                                .document(getCurrentUID())
                                .update(Constants.FAVPRODUCTID, FieldValue.arrayUnion(fvProductId))
                                .addOnSuccessListener(aVoid -> {
                                    Log.i("FireStoreClass", "addFavorite: Thành công");
                                    Toast.makeText(context, "Thêm sản phẩm vào yêu thích thành công", Toast.LENGTH_SHORT).show();
                                    // send message that user has changed favorite list
                                })
                                .addOnFailureListener(e -> {
                                    Log.e("FireStoreClass", "addFavorite: ", e);
                                });
                    } else {
                        Toast.makeText(context, "Sản phẩm đã có trong danh sách yêu thích", Toast.LENGTH_SHORT).show();
                    }
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

    public void addToCart(double productId, String productName, double productPrice, String productImageUrl) {
        String currentUserId = getCurrentUID();

        // Tạo một HashMap để lưu thông tin sản phẩm

        // Lấy reference đến collection "cart"
        CollectionReference cartRef = UniPetdb.collection("cart");

        // Tạo một document mới trong collection "cart"
        DocumentReference newCartItemRef = cartRef.document();

        // Tạo một đối tượng HashMap chứa thông tin sản phẩm
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
                            loadingDialog.dissmis();
                        }
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
