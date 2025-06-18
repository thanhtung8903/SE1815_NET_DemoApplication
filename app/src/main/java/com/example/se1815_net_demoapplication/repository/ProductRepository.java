package com.example.se1815_net_demoapplication.repository;

import android.content.Context;

import com.example.se1815_net_demoapplication.bean.ProductBean;
import com.example.se1815_net_demoapplication.dao.ProductDao;
import com.example.se1815_net_demoapplication.dao.ProductRoomDatabase;
import com.example.se1815_net_demoapplication.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    private final ProductDao productDao;

    public ProductRepository(Context context) {
        ProductRoomDatabase database = ProductRoomDatabase.getInstance(context);
        productDao = database.productDao();
    }

    public void createProduct(ProductBean productBean) {
        Product product = new Product(productBean.getName(), productBean.getPrice());
        product.setId(productBean.getId());
        productDao.insert(product);
    }

    public void updateProduct(ProductBean productBean) {
        Product product = new Product(productBean.getName(), productBean.getPrice());
        product.setId(productBean.getId());
        productDao.update(product);
    }

    public void deleteProduct(int id) {
        Product product = productDao.getProductById(id);
        if (product != null) {
            productDao.delete(product);
        }
    }

    public List<ProductBean> getAllProducts() {
        List<Product> products = productDao.getAllProducts();
        List<ProductBean> productBeans = new ArrayList<>();
        for (Product product : products) {
            ProductBean productBean = new ProductBean();
            productBean.setId(product.getId());
            productBean.setName(product.getName());
            productBean.setPrice(product.getPrice());
            productBeans.add(productBean);
        }
        return productBeans;
    }
}
