package com.example.se1815_net_demoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.se1815_net_demoapplication.bean.ProductBean;
import com.example.se1815_net_demoapplication.database.DatabaseHandler;
import com.example.se1815_net_demoapplication.repository.ProductRepository;

public class AddProductActivity extends AppCompatActivity {

    private ProductRepository productRepository;
    private EditText edtProductName;
    private EditText edtProductPrice;
    private Button btSaveProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        productRepository = new ProductRepository(this);
        edtProductName = findViewById(R.id.edtProductName);
        edtProductPrice = findViewById(R.id.edtProductPrice);
        btSaveProduct = findViewById(R.id.btnSaveProduct);

        btSaveProduct.setOnClickListener(v -> {
            String productName = edtProductName.getText().toString();
            String productPrice = edtProductPrice.getText().toString();
            double price = Double.parseDouble(productPrice);

            ProductBean productBean = new ProductBean();
            productBean.setName(productName);
            productBean.setPrice(price);

            productRepository.createProduct(productBean);
            Intent intent = new Intent(AddProductActivity.this, ProductListActivity.class);
            startActivity(intent);
        });
    }
}