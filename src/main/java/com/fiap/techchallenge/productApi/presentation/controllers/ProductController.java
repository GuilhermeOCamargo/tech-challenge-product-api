package com.fiap.techchallenge.productApi.presentation.controllers;

import com.fiap.techchallenge.productApi.application.useCases.ProductUseCases;
import com.fiap.techchallenge.productApi.presentation.dtos.ErrorResponseDto;
import com.fiap.techchallenge.productApi.presentation.dtos.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/tech-challenge-product/products")
@Tag(name = "Produtos", description = "Operações para gerenciamento de produtos")
@RequiredArgsConstructor
public class ProductController {

    private final ProductUseCases productUseCases;

    @Operation(summary = "Cadastra Produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto cadastrado",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProductDto.class))
                    }),
            @ApiResponse(responseCode = "409", description = "Produto já cadastrado",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class))
                    }
            ),
            @ApiResponse(responseCode = "422", description = "Dados inválidos para o cadastro do produto",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class))
                    }
            )
    })
    @PostMapping
    @ResponseStatus(CREATED)
    public ResponseEntity<?> saveProduct(@RequestBody ProductDto productDto) {
        ProductDto createdProductDto = productUseCases.saveProduct(productDto);
        return ResponseEntity.status(CREATED).body(createdProductDto);
    }

    @Operation(summary = "Altera Produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto Alterado",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProductDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class))
                    }
            ),
            @ApiResponse(responseCode = "422", description = "Dados inválidos para o cadastro do produto",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class))
                    }
            )
    })
    @PutMapping
    @ResponseStatus(OK)
    public ResponseEntity<?> updateProduct(@RequestBody ProductDto productDto) {
        ProductDto updatedProductDto = productUseCases.updateProduct(productDto);
        return ResponseEntity.status(OK).body(updatedProductDto);
    }

    @Operation(summary = "Consulta Produto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProductDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class))
                    }
            )
    })
    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        ProductDto productDto = productUseCases.getProductById(id);
        return ResponseEntity.status(OK).body(productDto);
    }

    @Operation(summary = "Consulta todos os produtos ou todos os produtos por categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos encontrado",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = ProductDto.class)))
                    }),
            @ApiResponse(responseCode = "404", description = "Produtos não encontrado",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class))
                    }
            )
    })
    @GetMapping
    @ResponseStatus(OK)
    public ResponseEntity<?> getAllProducts(@RequestParam(value = "category", required = false) String category) {
        List<ProductDto> productDtos = productUseCases.getAllProducts(category);
        return ResponseEntity.status(OK).body(productDtos);
    }

    @Operation(summary = "Deleta um produto por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produtos encontrado",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = ProductDto.class)))
                    }),
            @ApiResponse(responseCode = "404", description = "Produtos não encontrado",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class))
                    }
            )
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productUseCases.deleteProduct(id);
        return ResponseEntity.status(NO_CONTENT).body(null);
    }
}

