package com.fiap.techchallenge.productApi.application.services.impl;

import com.fiap.techchallenge.productApi.application.services.ProductService;
import com.fiap.techchallenge.productApi.domain.Product;
import com.fiap.techchallenge.productApi.domain.exceptions.InvalidDataException;
import com.fiap.techchallenge.productApi.domain.exceptions.NotFoundException;
import com.fiap.techchallenge.productApi.gateway.entity.ProductEntity;
import com.fiap.techchallenge.productApi.gateway.repository.ProductRepository;
import com.fiap.techchallenge.productApi.util.ConstantsUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product save(Product product) {
        return productRepository.save(new ProductEntity(product)).toDomain();
    }

    @Override
    public Product update(Product product) {
        if(isNull(product.id()))
            throw new InvalidDataException(ConstantsUtil.INVALID_ID);
        getProductById(product.id());
        return save(product);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .map(ProductEntity::toDomain)
                .orElseThrow(() -> new NotFoundException(ConstantsUtil.PRODUCT_NOT_FOUND));
    }

    @Override
    public List<Product> getAllProducts(String category) {
        List<ProductEntity> entityList = nonNull(category) ?
                productRepository.findByCategory(category) : productRepository.findAll();

        if(entityList.isEmpty())
            throw new NotFoundException(ConstantsUtil.PRODUCT_LIST_NOT_FOUND);
        return entityList.stream()
                .map(ProductEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProduct(Long id) {
        ProductEntity productEntity = productRepository.findById(id).orElseThrow(() -> new NotFoundException(ConstantsUtil.PRODUCT_NOT_FOUND));
        productRepository.delete(productEntity);
    }
}
