package com.alten.test.demo.service;

import com.alten.test.demo.Model.Product;
import com.alten.test.demo.exception.ProductException;
import com.alten.test.demo.repository.ProductRepository;
import com.alten.test.demo.utils.ProductUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Flux<Product> allProducts(){
        return productRepository.findAll();
    }

    public Mono<Product> newProduct(Product product) {
        return ProductUtils.verifyProductFields(product)
                .doOnError(err -> new ProductException(err.getMessage()))
                .flatMap(e -> productRepository.save(e));
    }

    public Mono<Product> productDetails(ObjectId id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new ProductException("No Product found with ID: "+id)));
    }

    public Mono<Product> updateProduct(ObjectId id, Product product) {
        return this.productDetails(id)
                .flatMap(existingProduct -> productRepository.save(ProductUtils.setProductFields(existingProduct,product)))
                .doOnError(err -> new ProductException("Error updating product" + err.getMessage()));
    }

    public Mono<ResponseEntity<String>> deleteProduct(ObjectId id) {
        return this.productDetails(id)
                .flatMap(product -> productRepository.delete(product))
                .thenReturn(ResponseEntity.ok("Product deleted !"))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
}
