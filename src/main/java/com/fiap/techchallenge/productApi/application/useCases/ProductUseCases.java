package com.fiap.techchallenge.productApi.application.useCases;


import com.fiap.techchallenge.productApi.application.services.ProductService;
import com.fiap.techchallenge.productApi.domain.Product;
import com.fiap.techchallenge.productApi.domain.exceptions.*;
import com.fiap.techchallenge.productApi.presentation.dtos.ProductDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductUseCases {

    private final ProductService productService;

    public ProductDto saveProduct(@Valid ProductDto productDto) {
        try {
            var product = productService.saveProduct(productDto.toDomain());
            return ProductDto.of(product);
        } catch (AlreadyExistsException e) {
            throw new ResourceAlreadyExists(e.getMessage());
        } catch (InvalidDataException e) {
            throw new DataInputException(e.getMessage());
        }
    }

    public ProductDto updateProduct(ProductDto productDto) {
        try {
            Product product = productService.updateProduct(productDto.toDomain());
            return ProductDto.of(product);
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
            List<Product> products = productService.getAllProducts(category);
            return products.stream().map(p -> ProductDto.of(p)).collect(Collectors.toList());
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
