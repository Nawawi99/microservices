package dev.awn.productservice.core.product.mapper;

import dev.awn.productservice.core.product.dto.ProductDTO;
import dev.awn.productservice.core.product.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    public ProductDTO toDTO(Product product) {
        return ProductDTO.builder()
                         .id(product.getId())
                         .name(product.getName())
                         .description(product.getDescription())
                         .price(product.getPrice())
                         .build();
    }

    public Product toModel(ProductDTO productDTO) {
        return Product.builder()
                      .id(productDTO.getId())
                      .name(productDTO.getName())
                      .description(productDTO.getDescription())
                      .price(productDTO.getPrice())
                      .build();
    }

    public List<ProductDTO> toDTOs(List<Product> products) {
        return products.stream()
                       .map(this::toDTO)
                       .collect(Collectors.toList());
    }

    public List<Product> toModels(List<ProductDTO> productDTOs) {
        return productDTOs.stream()
                          .map(this::toModel)
                          .collect(Collectors.toList());
    }
}
