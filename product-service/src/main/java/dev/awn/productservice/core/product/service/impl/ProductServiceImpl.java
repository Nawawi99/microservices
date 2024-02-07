package dev.awn.productservice.core.product.service.impl;

import dev.awn.productservice.common.exception.ResourceNotFoundException;
import dev.awn.productservice.core.product.dto.ProductDTO;
import dev.awn.productservice.core.product.mapper.ProductMapper;
import dev.awn.productservice.core.product.model.Product;
import dev.awn.productservice.core.product.repository.ProductRepository;
import dev.awn.productservice.core.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductDTO> getProducts() {
        List<Product> products = productRepository.findAll();

        return productMapper.toDTOs(products);
    }

    @Override
    public ProductDTO getProduct(String id) {
        Optional<Product> result = productRepository.findById(id);

        if (result.isEmpty()) {
            throw new ResourceNotFoundException("Product of ID - " + id +
                    " wasn't found");
        }

        return productMapper.toDTO(result.get());
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {

        Product product = productMapper.toModel(productDTO);

        Product savedProduct = productRepository.save(product);

        return productMapper.toDTO(savedProduct);
    }

    @Override
    public ProductDTO modifyProduct(ProductDTO productDTO) {
        Product product = productMapper.toModel(productDTO);

        boolean productExists = productRepository.findById(product.getId()).isPresent();

        if(!productExists) {
            throw new ResourceNotFoundException("Product of ID - " + product.getId() +
                    " wasn't found");
        }

        Product savedProduct = productRepository.save(product);

        return productMapper.toDTO(savedProduct);
    }

    @Override
    public Boolean removeProduct(String id) {
        productRepository.deleteById(id);

        return productRepository.findById(id).isEmpty();
    }
}
