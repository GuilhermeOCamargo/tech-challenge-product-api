package com.fiap.techchallenge.productApi.application.services.impl;

import com.fiap.techchallenge.productApi.application.services.ProductService;
import com.fiap.techchallenge.productApi.domain.Product;
import com.fiap.techchallenge.productApi.domain.exceptions.NotFoundException;
import com.fiap.techchallenge.productApi.gateway.entity.ProductEntity;
import com.fiap.techchallenge.productApi.gateway.repository.ProductRepository;
import com.fiap.techchallenge.productApi.util.ConstantsUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Autowired
    private final ProductRepository productRepository;

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(new ProductEntity(product)).toDomain();
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepository.save(new ProductEntity(product)).toDomain();
    }

    @Override
    public Product getProductById(Long id) {
        Optional<ProductEntity> productEntity = productRepository.findById(id);
        return productEntity.isPresent() ? productEntity.get().toDomain() : null;
    }

    @Override
    public List<Product> getAllProducts(String category) {

        if (category != null) {
            return getProductsByCategory(category);
        }

        List<ProductEntity> productEntityList = productRepository.findAll();

        List<Product> products = productEntityList.stream()
                .map(p -> p.toDomain())
                .collect(Collectors.toList());

        return products;
    }

    private List<Product> getProductsByCategory(String category) {
        List<ProductEntity> productListByCategory = productRepository.findByCategory(category);

        List<Product> products = productListByCategory.stream()
                .map(p -> p.toDomain())
                .collect(Collectors.toList());

        return products;
    }

    @Override
    public void deleteProduct(Long id) {
        ProductEntity productEntity = productRepository.findById(id).orElseThrow(() -> new NotFoundException(ConstantsUtil.PRODUCT_NOT_FOUND));
        productRepository.delete(productEntity);
    }
}
