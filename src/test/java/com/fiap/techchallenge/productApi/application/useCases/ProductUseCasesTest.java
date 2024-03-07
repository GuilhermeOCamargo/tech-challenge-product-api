package com.fiap.techchallenge.productApi.application.useCases;

import com.fiap.techchallenge.productApi.application.services.impl.ProductServiceImpl;
import com.fiap.techchallenge.productApi.domain.Product;
import com.fiap.techchallenge.productApi.domain.exceptions.DataInputException;
import com.fiap.techchallenge.productApi.domain.exceptions.NotFoundException;
import com.fiap.techchallenge.productApi.domain.exceptions.ResourceNotFoundException;
import com.fiap.techchallenge.productApi.presentation.dtos.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductUseCasesTest {

    @Mock
    private ProductServiceImpl productService;

    @InjectMocks
    private ProductUseCases productUseCases;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productUseCases = new ProductUseCases(productService);
    }

    @Nested
    @DisplayName("Deve testar salvar novos produtos")
    class ShouldTestSaveProduct {
        @Test
        @DisplayName("Deve salvar novo produto no banco de dados")
        void shouldSaveProduct() {
            Product product = createSampleProduct(50.00);

            doReturn(product).when(productService).save(any(Product.class));

            ProductDto savedProduct = productUseCases.saveProduct(createSampleDto(50.00));

            assertEquals(savedProduct.getId(), product.id());
            assertEquals(savedProduct.getCategory(), product.category());
            assertEquals(savedProduct.getPrice(), product.price());
            assertEquals(savedProduct.getDescription(), product.description());
            assertEquals(savedProduct.getImages(), product.images());

            verify(productService, times(1)).save(any(Product.class));
        }

        @Test
        @DisplayName("Deve retornar DataInputException ao salvar novo produto")
        void shouldThrowDataInputErrorWhenSaveProduct() {
            assertThrows(DataInputException.class, () -> productUseCases.saveProduct(createSampleDto(null)));

            verify(productService, never()).save(any(Product.class));
        }
    }

    @Nested
    @DisplayName("Deve testar atualizar informacoes de produtos")
    class ShouldTestUpdateProduct {

        @Test
        @DisplayName("Deve atualizar um produto existente")
        void shouldUpdateProduct() {
            Product product = createSampleProduct(50.00);

            when(productService.update(any(Product.class))).thenReturn(product);

            ProductDto savedProduct = productUseCases.updateProduct(ProductDto.of(product));

            assertEquals(savedProduct.getId(), product.id());
            assertEquals(savedProduct.getCategory(), product.category());
            assertEquals(savedProduct.getPrice(), product.price());
            assertEquals(savedProduct.getDescription(), product.description());
            assertEquals(savedProduct.getImages(), product.images());

            verify(productService).update(any(Product.class));
        }

        @Test
        @DisplayName("Deve retornar erro de produto nao encontrado ao atualizar produto")
        void shouldReturnNotFoundError() {

            doThrow(NotFoundException.class).when(productService).update(any(Product.class));

            assertThrows(ResourceNotFoundException.class, () -> productUseCases.updateProduct(createSampleDto(50.00))) ;

            verify(productService).update(any(Product.class));
        }

        @Test
        @DisplayName("Deve retornar erro de dados inválidos ao atualizar produto")
        void shouldReturnDataInputError() {

            assertThrows(DataInputException.class, () -> productUseCases.updateProduct(createSampleDto(null))) ;

            verify(productService, never()).update(any(Product.class));
        }
    }

    @Nested
    @DisplayName("Deve testar a consulta de um produto por id")
    class ShouldTestGetProdutById {

        @Test
        @DisplayName("Deve buscar o produto por id")
        void shouldGetProductById() {
            Long productId = 1L;
            Product product = createSampleProduct(50.00);

            when(productService.getProductById(productId)).thenReturn(product);

            ProductDto savedProduct = productUseCases.getProductById(productId);

            assertNotNull(product);
            assertEquals(savedProduct.getId(), product.id());
            assertEquals(savedProduct.getCategory(), product.category());
            assertEquals(savedProduct.getPrice(), product.price());
            assertEquals(savedProduct.getDescription(), product.description());
            assertEquals(savedProduct.getImages(), product.images());

            verify(productService).getProductById(productId);
        }

        @Test
        @DisplayName("Deve return ResourceNotFound quando buscar produto por id")
        void shouldReturnNotFoundErrorWhenFindById() {
            Long productId = 1L;

            when(productService.getProductById(productId)).thenThrow(NotFoundException.class);

            assertThrows(ResourceNotFoundException.class, () -> productUseCases.getProductById(productId));

            verify(productService).getProductById(productId);
        }

    }

    @Nested
    @DisplayName("Deve testar buscar lista de produtos")
    class ShouldTestListOfProducts {

        @Test
        @DisplayName("Deve buscar todos os produtos")
        void shouldGetAllProducts() {
            List<Product> productsList = Arrays.asList(
                    createSampleProduct(50.00),
                    createSampleProduct(50.00),
                    createSampleProduct(50.00)
            );

            when(productService.getAllProducts(null)).thenReturn(productsList);

            List<ProductDto> products = productUseCases.getAllProducts(null);

            assertEquals(productsList.size(), products.size());
            verify(productService, times(1)).getAllProducts(null);
        }

        @Test
        @DisplayName("Deve buscar produtos por categoria")
        void shouldGetAllProductsByCategory() {
            String category = "Lanches";
            List<Product> productsList = Arrays.asList(
                    createSampleProduct(50.00),
                    createSampleProduct(50.00),
                    createSampleProduct(50.00)
            );

            when(productService.getAllProducts(category)).thenReturn(productsList);

            List<ProductDto> products = productUseCases.getAllProducts(category);

            assertEquals(productsList.size(), products.size());

            verify(productService).getAllProducts(category);
        }

        @Test
        @DisplayName("Deve return ResourceNotFound quando buscar todos os produtos")
        void shouldReturnNotFoundErrorWhenFindListOfProducts() {
            String category = "Lanche";
            when(productService.getAllProducts(category)).thenThrow(NotFoundException.class);

            assertThrows(ResourceNotFoundException.class, () -> productUseCases.getAllProducts(category));

            verify(productService).getAllProducts(category);
        }

    }

    @Nested
    @DisplayName("Deve testar deletar produtos")
    class ShouldTestDeleteProduct{

        @Test
        @DisplayName("Deve deletar produto")
        void shouldDeleteProduct() {
            Long productId = 1L;

            doNothing().when(productService).deleteProduct(productId);
            productUseCases.deleteProduct(productId);

            verify(productService).deleteProduct(productId);
        }

        @Test
        @DisplayName("Deve retornar ResourceNotFound quando deletar produto")
        void shouldReturnNotFoundErrorWhenDeleteProduct() {
            Long productId = 1L;

            doThrow(NotFoundException.class).when(productService).deleteProduct(productId);
            assertThrows(ResourceNotFoundException.class, () -> productUseCases.deleteProduct(productId));

            verify(productService).deleteProduct(productId);
        }
    }

    private Product createSampleProduct(Double price) {
        return Product.builder()
                .id(1L)
                .category("Lanches")
                .price(price)
                .description("Sabor 4 queijos")
                .images(Arrays.asList("image1.jpg", "image2.jpg", "image3.jpg"))
                .build();
    }

    private ProductDto createSampleDto(Double price) {
        return ProductDto.builder()
                .id(1L)
                .category("Lanches")
                .price(price)
                .description("Sabor 4 queijos")
                .images(Arrays.asList("image1.jpg", "image2.jpg", "image3.jpg"))
                .build();
    }
}