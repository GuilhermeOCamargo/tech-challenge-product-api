package com.fiap.techchallenge.productApi.application.useCases;


import com.fiap.techchallenge.productApi.application.services.ProductService;
import com.fiap.techchallenge.productApi.domain.Product;
import com.fiap.techchallenge.productApi.domain.exceptions.*;
import com.fiap.techchallenge.productApi.presentation.dtos.ProductDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductUseCases {

    private final ProductService productService;

    public ProductDto saveProduct(ProductDto productDto) {
        return ProductDto.of(productService.save(productDto.toDomain()));
    }

    public ProductDto updateProduct(ProductDto productDto) {
        try {
            return ProductDto.of(productService.update(productDto.toDomain()));
        } catch (NotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        } catch (InvalidDataException e) {
            throw new DataInputException(e.getMessage());
        }
    }

    public ProductDto getProductById(Long id) {
        try {
            Product product = productService.getProductById(id);
            return ProductDto.of(product);
        } catch (NotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    public List<ProductDto> getAllProducts(String category) {
        try {
            return productService.getAllProducts(category)
                    .stream()
                    .map(ProductDto::of)
                    .collect(Collectors.toList());
        } catch (NotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    public void deleteProduct(Long id) {
        try {
            productService.deleteProduct(id);
        } catch (NotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }
}
