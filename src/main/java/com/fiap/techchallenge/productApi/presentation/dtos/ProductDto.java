package com.fiap.techchallenge.productApi.presentation.dtos;

import com.fiap.techchallenge.productApi.domain.Product;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductDto {

    private Long id;
    private String category;
    private double price;
    private String description;
    private List<String> images;

    public static ProductDto of(Product Product) {
        return ProductDto.builder()
                .id(Product.id())
                .category(Product.category())
                .price(Product.price())
                .description(Product.description())
                .images(Product.images())
                .build();
    }

    public Product toDomain() {
        return Product.builder()
                .category(this.getCategory())
                .price(this.getPrice())
                .description(this.getDescription())
                .images(this.getImages())
                .build();
    }
}

