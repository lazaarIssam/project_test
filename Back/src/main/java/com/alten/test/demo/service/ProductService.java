package com.alten.test.demo.service;

import com.alten.test.demo.Model.Product;
import com.alten.test.demo.exception.ProductException;
import com.alten.test.demo.repository.ProductRepository;
import com.alten.test.demo.utils.ProductUtils;
import com.alten.test.demo.utils.SequenceManager;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Flux<Product> allProducts(){
        return productRepository.findAll();
    }

    public Mono<ResponseEntity<Product>> newProduct(Product product) {
        return ProductUtils.verifyProductFields(product)
                .flatMap(element -> {
                    element.setId(SequenceManager.getNextSequence());
                    return productRepository.save(element)
                            .doOnError(err -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving the product")))
                            .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(element));
                })
                .onErrorResume(ProductException.class, ex -> Mono.just(ResponseEntity.badRequest().body(new Product())));

    }

    public Mono<ResponseEntity<Product>> productDetails(Integer id) {
        return productRepository.findById(id)
                .map(product -> ResponseEntity.ok(product))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    public Mono<ResponseEntity<Product>> updateProduct(Integer id, Product product) {
        return this.productDetails(id)
                .flatMap(existingProduct -> {
                    if (existingProduct.getStatusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Product()));
                    }
                    Product updatedProduct = ProductUtils.setProductFields(existingProduct.getBody(), product);
                    return productRepository.save(updatedProduct)
                            .thenReturn(ResponseEntity.ok(updatedProduct));
                })
                .onErrorResume(err -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Product())));
    }

    public Mono<ResponseEntity<Product>> deleteProduct(Integer id) {
        return this.productDetails(id)
                .flatMap(product -> {
                    if (product.getStatusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Product()));
                    }
                    return productRepository.delete(product.getBody()).thenReturn(ResponseEntity.ok(new Product()));
                })
                .onErrorResume(err -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Product())));
    }
}
