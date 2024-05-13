package com.unipet7.mcommerce.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.adapters.ProductAdapter;
import com.unipet7.mcommerce.databinding.ActivitySearchProductListBinding;
import com.unipet7.mcommerce.databinding.ActivitySupportContactBinding;
import com.unipet7.mcommerce.firebase.FireStoreClass;
import com.unipet7.mcommerce.fragments.fragment_cart;
import com.unipet7.mcommerce.models.Product;
import com.unipet7.mcommerce.utils.Constants;
import com.unipet7.mcommerce.utils.LoadingDialog;

import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;

import java.util.List;

public class SearchProductList extends AppCompatActivity {
    ActivitySearchProductListBinding binding;
    ProductAdapter adapter;
    private ArrayList<Product> allProducts;
    private ArrayList<Product> productsSale = new ArrayList<>();
    private ArrayList<Product> filteredProductList = new ArrayList<>();
    FireStoreClass fireStoreClass = new FireStoreClass();
    ArrayList<Product> product;
    private final FirebaseFirestore UniPetdb = FirebaseFirestore.getInstance();
    List<Product> searchResults = new ArrayList<>();

    List<Integer> favList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchProductListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setActionBar(binding.toolbarsearch);
        FireStoreClass fireStoreClass = new FireStoreClass();
        allProducts = new ArrayList<>();
        searchkeyword();
        fireStoreClass.SearchAllProducts(this, allProducts);
        addEvents();
        fireStoreClass.getCountUserCartItemsinSearch(this);
    }

    private void searchkeyword() {
            binding.editTextSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String keyword = s.toString().trim().toLowerCase();
                    searchResults.clear();

                    for (Product product : allProducts) {
                        if (product.getProductname().toLowerCase().contains(keyword)) {
                            searchResults.add(product);
                        }
                    }
                    int numberOfSearchResults = searchResults.size();
                    String numberSearchText = numberOfSearchResults + " Kết quả tìm kiếm";
                    binding.numbersearch.setText(numberSearchText);
                    adapter.setData(searchResults);
                    adapter.notifyDataSetChanged();
                }


                @Override
                public void afterTextChanged(Editable s) {

                }
            });
    }
    public void configAdaptersSearch() {
        int numberOfProducts = allProducts.size();
        String numberSearchText = numberOfProducts + " Kết quả tìm kiếm";
        binding.numbersearch.setText(numberSearchText);
        adapter = new ProductAdapter(allProducts, fireStoreClass);
        binding.lvlProductList.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        binding.lvlProductList.setAdapter(adapter);
        binding.lvlProductList.setHasFixedSize(true);
    }

    private void addEvents() {
        binding.toolbarsearch.setNavigationOnClickListener(v -> finish());
        binding.arrangefilter.setOnClickListener(v -> showBottomSheet());
        binding.filter.setOnClickListener(v -> showBottomSheet2());
        binding.voucherfilter.setOnClickListener(v -> fireStoreClass.getSalesPFilter(SearchProductList.this));
        binding.imageCart1.setOnClickListener(v -> {
            Intent intent = new Intent(SearchProductList.this, MainActivity.class);
            intent.putExtra(Constants.CART, 2);
            startActivity(intent);
        });
    }
    public void loadCartCount1(int count) {
        RelativeLayout cartCountLayout = binding.rlCartNumber1;
        cartCountLayout.setVisibility(RelativeLayout.VISIBLE);
        TextView cartCount = binding.txtNumberCart1;
        cartCount.setText(String.valueOf(count));
    }
    public void loadSalesProducts(ArrayList<Product> productsSale) {
        ProductAdapter adapterSale = new ProductAdapter(productsSale, fireStoreClass);
        binding.lvlProductList.setAdapter(adapterSale);
        binding.lvlProductList.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        binding.lvlProductList.setHasFixedSize(true);
    }
    private void showBottomSheet() {
        Dialog dialog = new Dialog(SearchProductList.this);
        dialog.setContentView(R.layout.bottom_sheet_filter);
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        ImageView closeButton = dialog.findViewById(R.id.ic_close);
        RadioButton radioSortAZ = dialog.findViewById(R.id.rdioNameProductA);
        RadioButton radioSortZA = dialog.findViewById(R.id.rdioNameProductZ);
        RadioButton radioSortPriceLowToHigh = dialog.findViewById(R.id.rdioNamePriceInc);
        RadioButton radioSortPriceHighToLow = dialog.findViewById(R.id.rdioPriceDes);
        closeButton.setOnClickListener(v -> dialog.dismiss());

        // Xử lý sự kiện khi người dùng chọn radio button
        radioSortAZ.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Lọc từ A đến Z
                ArrayList<Product> sortedProducts = sortProductsAscending(allProducts);
                // Hiển thị danh sách sản phẩm mới
                displayProducts(sortedProducts);
            }
        });

        radioSortZA.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Lọc từ Z đến A
                ArrayList<Product> sortedProducts = sortProductsDescending(allProducts);
                // Hiển thị danh sách sản phẩm mới
                displayProducts(sortedProducts);
            }
        });

        radioSortPriceLowToHigh.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Lọc theo giá tăng dần
                ArrayList<Product> sortedProducts = sortProductsByPriceAscending(allProducts);
                // Hiển thị danh sách sản phẩm mới
                displayProducts(sortedProducts);
            }
        });

        radioSortPriceHighToLow.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Lọc theo giá giảm dần
                ArrayList<Product> sortedProducts = sortProductsByPriceDescending(allProducts);
                // Hiển thị danh sách sản phẩm mới
                displayProducts(sortedProducts);
            }
        });

    }
    private ArrayList<Product> sortProductsAscending(ArrayList<Product> products) {
        // Sắp xếp danh sách sản phẩm theo thứ tự tăng dần (A-Z)
        Collections.sort(products, (product1, product2) -> product1.getProductname().compareToIgnoreCase(product2.getProductname()));
        return products;
    }

    private ArrayList<Product> sortProductsDescending(ArrayList<Product> products) {
        // Sắp xếp danh sách sản phẩm theo thứ tự giảm dần (Z-A)
        Collections.sort(products, (product1, product2) -> product2.getProductname().compareToIgnoreCase(product1.getProductname()));
        return products;
    }

    private ArrayList<Product> sortProductsByPriceAscending(ArrayList<Product>  products) {
        // Sắp xếp danh sách sản phẩm theo giá tăng dần
        Collections.sort(products, (product1, product2) -> Double.compare(product1.getProductprice(), product2.getProductprice()));
        return products;
    }

    private ArrayList<Product> sortProductsByPriceDescending(ArrayList<Product> products) {
        // Sắp xếp danh sách sản phẩm theo giá giảm dần
        Collections.sort(products, (product1, product2) -> Double.compare(product2.getProductprice(), product1.getProductprice()));
        return products;
    }

    private void displayProducts(ArrayList<Product> sortedproduct) {
        // Cập nhật số lượng sản phẩm hiển thị trong TextView
        int numberOfProducts = sortedproduct.size();
        String numberSearchText = numberOfProducts + " Kết quả tìm kiếm";
        binding.numbersearch.setText(numberSearchText);
        // Cập nhật giao diện người dùng để hiển thị danh sách sản phẩm mới
        adapter = new ProductAdapter(sortedproduct, fireStoreClass);
        binding.lvlProductList.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        binding.lvlProductList.setAdapter(adapter);
        binding.lvlProductList.setHasFixedSize(true);

    }
    private void showBottomSheet2() {
        Dialog dialog = new Dialog(SearchProductList.this);
        dialog.setContentView(R.layout.bottom_sheet_filter2);
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        ImageView closeButton = dialog.findViewById(R.id.ic_close);
        closeButton.setOnClickListener(v -> dialog.dismiss());
        CheckBox checkboxUnder100k = dialog.findViewById(R.id.chkboxunder100);
        CheckBox checkbox100kTo250k = dialog.findViewById(R.id.chkboxbetween100);
        CheckBox checkbox250kTo400k = dialog.findViewById(R.id.chkboxbetween250);
        CheckBox checkboxAbove400k = dialog.findViewById(R.id.chkboxabove400);
        // Xử lý sự kiện khi CheckBox được chọn/deselect
        checkboxUnder100k.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Thực hiện lọc sản phẩm dưới 100.000₫
                filterProductsByPrice(0, 100000);
            } else {
                // Bỏ điều kiện lọc sản phẩm dưới 100.000₫
                removePriceFilter();
            }
        });

        checkbox100kTo250k.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Thực hiện lọc sản phẩm từ 100.000đ - 250.000đ
                filterProductsByPrice(100000, 250000);
            } else {
                // Bỏ điều kiện lọc sản phẩm từ 100.000đ - 250.000đ
                removePriceFilter();
            }
        });

        checkbox250kTo400k.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Thực hiện lọc sản phẩm từ 250.000đ - 400.000đ
                filterProductsByPrice(250000, 400000);
            } else {
                // Bỏ điều kiện lọc sản phẩm từ 250.000đ - 400.000đ
                removePriceFilter();
            }
        });

        checkboxAbove400k.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Thực hiện lọc sản phẩm trên 400.000đ
                filterProductsByPrice(400000, Integer.MAX_VALUE);
            } else {
                // Bỏ điều kiện lọc sản phẩm trên 400.000đ
                removePriceFilter();
            }
        });
    }
    // Phương thức lọc sản phẩm theo giá
    private void filterProductsByPrice(int minPrice, int maxPrice) {
        filteredProductList.clear();

        // Lọc sản phẩm từ danh sách gốc
        for (Product product : allProducts) {
            double price = product.getProductprice();
            if (price >= minPrice && price <= maxPrice) {
                filteredProductList.add(product);
            }
        }

        // Hiển thị danh sách sản phẩm tương ứng
        displayFilteredProducts();
    }

    // Phương thức bỏ điều kiện lọc sản phẩm theo giá
    private void removePriceFilter() {
        // Khôi phục danh sách sản phẩm lúc ban đầu (sử dụng lại productList)
        filteredProductList.clear();
        filteredProductList.addAll(allProducts);

        // Hiển thị lại toàn bộ danh sách sản phẩm ban đầu
        displayFilteredProducts();
    }
    private void displayFilteredProducts() {
        int numberOfProducts = filteredProductList.size();
        String numberSearchText = numberOfProducts + " Kết quả tìm kiếm";
        binding.numbersearch.setText(numberSearchText);
        adapter = new ProductAdapter(filteredProductList, fireStoreClass);
        binding.lvlProductList.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        binding.lvlProductList.setAdapter(adapter);
        binding.lvlProductList.setHasFixedSize(true);
    }
    public void setActionBar(@Nullable Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        // Set icon arrow
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back_profile);
        actionBar.setDisplayShowTitleEnabled(false);;
}
}