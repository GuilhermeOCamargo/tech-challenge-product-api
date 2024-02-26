package com.fiap.techchallenge.productApi.gateway.entity;

import com.fiap.techchallenge.productApi.domain.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;

    private double price;

    private String description;

    @ElementCollection
    private List<String> images;

    public ProductEntity(Product product) {
        this(product.id(), product.category(), product.price(), product.description(), product.images());
    }

    public Product toDomain() {
        return Product.builder()
                .id(this.id)
                .price(this.price)
                .category(this.category)
                .description(this.description)
                .images(this.images)
                .build();
    }
}
