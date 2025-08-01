package com.norhan.project.product;

import com.norhan.project.dtos.ProductDto;
import com.norhan.project.entities.Product;
import com.norhan.project.mappers.ProductMapper;
import com.norhan.project.repositories.CategoryRepository;
import com.norhan.project.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@AllArgsConstructor
@Service
public class ProductService {
private ProductRepository productRepository;
private ProductMapper productMapper;
private CategoryRepository categoryRepository;


    public List<ProductDto> getAllProducts(
           Byte categoryId
    ) {
        List<Product> products;
        if (categoryId != null) {
            products = productRepository.findByCategoryId(categoryId);
        } else {
            products = productRepository.findAllWithCategory();
        }

        return products.stream().map(productMapper::toDto).toList();
    }


    public ProductDto getProduct(Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        return productMapper.toDto(product);
    }

    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        var category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(CategoryNotFoundException::new);

        var product = productMapper.toEntity(productDto);
        product.setCategory(category);
        productRepository.save(product);
        productDto.setId(product.getId());

        return productDto;
    }
    @Transactional
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        var product = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        var category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(CategoryNotFoundException::new);

        productMapper.update(productDto, product);
        product.setCategory(category);
        productRepository.save(product);
        productDto.setId(product.getId());

        return productDto;
    }

    @Transactional
    public void deleteProduct(Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        productRepository.delete(product);
    }



}
