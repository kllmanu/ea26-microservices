package com.ecommerce.app.product.service;

import com.ecommerce.app.product.domain.Product;
import com.ecommerce.app.product.domain.ProductId;
import com.ecommerce.app.product.dto.ProductCreateRequestDto;
import com.ecommerce.app.product.dto.ProductResponseDto;
import com.ecommerce.app.product.dto.ProductUpdateRequestDto;
import com.ecommerce.app.product.entity.ProductEntity;
import com.ecommerce.app.product.exception.InsufficientStockException;
import com.ecommerce.app.product.exception.ProductNotAvailableException;
import com.ecommerce.app.product.exception.ProductNotFoundException;
import com.ecommerce.app.product.mapper.*;
import com.ecommerce.app.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductResponseMapper productResponseMapper;

    @Autowired
    private ProductCreateRequestMapper productCreateRequestMapper;

    @Autowired
    private ProductUpdateRequestMapper productUpdateRequestMapper;

    @Autowired
    private ProductEntityMapper productEntityMapper;

    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productEntityMapper::toDomain)
                .map(productResponseMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<ProductResponseDto> getProductById(UUID id) {
        return Optional.of(productRepository.findById(id)
                .map(productEntityMapper::toDomain)
                .map(productResponseMapper::toDto)
                .orElseThrow(() -> new ProductNotFoundException(id)));
    }

    public ProductResponseDto createProduct(ProductCreateRequestDto productDto) {
        Product domain = productCreateRequestMapper.toDomain(productDto);
        ProductEntity entity = productEntityMapper.toEntity(domain);
        ProductEntity savedEntity = productRepository.save(entity);
        return productResponseMapper.toDto(productEntityMapper.toDomain(savedEntity));
    }

    public Optional<ProductResponseDto> updateProduct(UUID id, ProductUpdateRequestDto productDto) {
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        
        Product updateDomain = productUpdateRequestMapper.toDomain(productDto);
        productEntityMapper.updateEntityFromDomain(entity, updateDomain);
        ProductEntity savedEntity = productRepository.save(entity);
        return Optional.of(productResponseMapper.toDto(productEntityMapper.toDomain(savedEntity)));
    }

    public boolean deleteProduct(UUID id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
        return true;
    }

    public void checkAvailability(ProductId id) {
        ProductEntity entity = productRepository.findById(id.getValue())
                .orElseThrow(() -> new ProductNotFoundException(id));
        
        Product product = productEntityMapper.toDomain(entity);
        
        if (!product.isAvailable()) {
            throw new ProductNotAvailableException("Product " + product.getName() + " is not available");
        }
    }

    public void checkStock(ProductId id, Integer requestedQuantity) {
        ProductEntity entity = productRepository.findById(id.getValue())
                .orElseThrow(() -> new ProductNotFoundException(id));

        Product product = productEntityMapper.toDomain(entity);

        if (!product.isInStock(requestedQuantity)) {
            throw new InsufficientStockException("Insufficient stock for product " + product.getName());
        }
    }

    public void reduceStock(ProductId id, Integer quantity) {
        ProductEntity entity = productRepository.findById(id.getValue())
                .orElseThrow(() -> new ProductNotFoundException(id));

        Product product = productEntityMapper.toDomain(entity);

        if (!product.isAvailable()) {
            throw new ProductNotAvailableException("Product " + product.getName() + " is not available");
        }

        if (!product.isInStock(quantity)) {
            throw new InsufficientStockException("Insufficient stock for product " + product.getName());
        }

        entity.setStock(entity.getStock() - quantity);
        productRepository.save(entity);
    }
}
