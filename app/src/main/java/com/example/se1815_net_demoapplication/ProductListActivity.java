package com.example.se1815_net_demoapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se1815_net_demoapplication.adapter.ProductAdapter;
import com.example.se1815_net_demoapplication.bean.ProductBean;
import com.example.se1815_net_demoapplication.repository.ProductRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductListActivity extends AppCompatActivity {
    private RecyclerView recyclerViewProductList;
    private ProductAdapter productAdapter;
    private FloatingActionButton fabAddProduct;
    private final List<ProductBean> productList = new ArrayList<>();
    private ProductRepository productRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        productRepository = new ProductRepository(this);

        recyclerViewProductList = findViewById(R.id.recyclerViewProductList);
        fabAddProduct = findViewById(R.id.fabAddProduct);

        productAdapter = new ProductAdapter(productList, this);
        recyclerViewProductList.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewProductList.setAdapter(productAdapter);

        // Xử lý click cho FAB
        fabAddProduct.setOnClickListener(v -> {
            Intent intent = new Intent(ProductListActivity.this, AddProductActivity.class);
            startActivity(intent);
        });

        fetchProductList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchProductList();
    }

    private void fetchProductList() {
        new Thread(() -> {
            try {
                List<ProductBean> products = productRepository.getAllProducts();

                runOnUiThread(() -> {
                    productList.clear();
                    productList.addAll(products); //
                    productAdapter.notifyDataSetChanged();

                    if (productList.isEmpty()) {
                        Toast.makeText(ProductListActivity.this,
                                "Chưa có sản phẩm nào",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                runOnUiThread(() -> {
                    Toast.makeText(ProductListActivity.this,
                            "Lỗi khi tải danh sách: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                });
            }
        }).start();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_view) {
            showDetails();
            return true;
        } else if (item.getItemId() == R.id.menu_edit) {
            showEdit();
            return true;
        } else if (item.getItemId() == R.id.menu_delete) {
            showDelete();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    public void requestGPS() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("GPS permission")
                        .setMessage("Please grant GPS permission")
                        .setPositiveButton("OK", (dialog, which) -> {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                        })
                        .setCancelable(false);
                builder.create().show();
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
        } else {
            // Permission is granted, proceed with GPS functionality
            Toast.makeText(this, "GPS permission granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "GPS permission granted", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void showDetails() {
        Toast.makeText(this, "Details is clicked", Toast.LENGTH_SHORT).show();
    }

    private void showEdit() {
        int position = productAdapter.getSelectedPosition();
        Toast.makeText(this, "Edit is clicked", Toast.LENGTH_SHORT).show();
    }

    private void showDelete() {
        int position = productAdapter.getSelectedPosition();
        productList.remove(position);
        productAdapter.notifyItemRemoved(position);
        Toast.makeText(this, "Delete is clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_setting) {
            showSettting();
            return true;
        } else if (item.getItemId() == R.id.menu_favourite) {
            showFavourite();
            return true;
        } else if (item.getItemId() == R.id.menu_logout) {
            // finish();
        }

        return false;
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            try {
                Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                method.setAccessible(true);
                method.invoke(menu, true);
            } catch (Exception e) {
                Log.e("MenuOptions", "Error setting options icon visibility", e);
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    private void showFavourite() {
        Toast.makeText(this, "Favourite is clicked", Toast.LENGTH_SHORT).show();
    }

    private void showSettting() {
        Toast.makeText(this, "Setting is clicked", Toast.LENGTH_SHORT).show();
    }

    private void filterProductList(String query) {
        List<ProductBean> filteredList = new ArrayList<>();
        if (query == null || query.isEmpty()) {
            productList.clear();
            fetchProductList();
        } else {

            for (ProductBean product : productList) {
                if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(product);
                }
            }
            productList.clear();
            productList.addAll(filteredList);
        }
        productAdapter.notifyDataSetChanged();
    }
}


