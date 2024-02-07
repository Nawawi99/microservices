package dev.awn.productservice.core.product.service;

import dev.awn.productservice.core.product.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getProducts();

    ProductDTO getProduct(String id);

    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO modifyProduct(ProductDTO productDTO);

    Boolean removeProduct(String id);
}
