package com.fiap.techchallenge.productApi.application.services;


import com.fiap.techchallenge.productApi.domain.Product;

import java.util.List;

public interface ProductService {

    Product save(Product product);

    Product update(Product product);

    Product getProductById(Long id);

    List<Product> getAllProducts(String category);

    void deleteProduct(Long id);
}
