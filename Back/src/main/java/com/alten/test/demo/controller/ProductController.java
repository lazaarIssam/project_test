package com.alten.test.demo.controller;

import com.alten.test.demo.Model.Product;
import com.alten.test.demo.service.ProductService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public Flux<Product> getAllProducts(){
        return productService.allProducts();
    }

    @PostMapping
    public Mono<ResponseEntity<Product>> createNewProduct(@RequestBody Product product){
        return productService.newProduct(product);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Product>> getProductDetails (@PathVariable Integer id){
        return productService.productDetails(id);
    }

    @PatchMapping("/{id}")
    public Mono<ResponseEntity<Product>> updateAProduct(@PathVariable Integer id, @RequestBody Product product){
        return productService.updateProduct(id,product);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Product>> deleteProduct (@PathVariable Integer id){
        return productService.deleteProduct(id);
    }
}
