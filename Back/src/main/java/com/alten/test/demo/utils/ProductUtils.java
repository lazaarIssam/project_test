package com.alten.test.demo.utils;

import com.alten.test.demo.Model.Product;
import com.alten.test.demo.exception.ProductException;
import reactor.core.publisher.Mono;

public class ProductUtils {

    public static Mono<Product> verifyProductFields(Product product) {
        if (product.getCode() == null || product.getCode().isBlank()) {
            return Mono.error(new ProductException("Code cannot be blank"));
        }
        if (product.getName() == null || product.getName().isBlank()) {
            return Mono.error(new ProductException("Name cannot be blank"));
        }
        if (product.getDescription() == null || product.getDescription().isBlank()) {
            return Mono.error( new ProductException("Description cannot be blank"));
        }
        if (product.getPrice() == null || product.getPrice() <= 0) {
            return Mono.error( new ProductException("Price cannot be null or < 0"));
        }
//        if (product.getQuantity() == null) {
//            return Mono.error( new ProductException("Quantity cannot be null"));
//        }
        if (product.getInventoryStatus() == null || product.getInventoryStatus().isBlank()) {
            return Mono.error( new ProductException("Quantity cannot be blank"));
        }
        if (product.getCategory() == null || product.getCategory().isBlank()) {
            return Mono.error( new ProductException("Quantity cannot be blank"));
        }
        return Mono.just(product);
    }

    public static Product setProductFields (Product existingProduct, Product product){
        existingProduct.setCode(product.getCode());
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setInventoryStatus(product.getInventoryStatus());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setImage(product.getImage());
        existingProduct.setRating(product.getRating());
        return existingProduct;
    }
}
