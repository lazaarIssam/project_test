package com.alten.test.demo.controllerTest;

import com.alten.test.demo.Model.Product;
import com.alten.test.demo.controller.ProductController;
import com.alten.test.demo.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Test
    public void testGetAllProducts() {
        // Mock data
        Product product1 = new Product(1, "P1", "Product 1", "Description 1", 100, 10, "InStock", "FITNESS", null, 4);
        Product product2 = new Product(2, "P2", "Product 2", "Description 2", 200, 20, "InStock", "FITNESS", null, 5);
        Flux<Product> mockProducts = Flux.just(product1, product2);

        // Mock the service
        when(productService.allProducts()).thenReturn(mockProducts);

        // Call the controller
        Flux<Product> result = productController.getAllProducts();

        // Assert the result
        StepVerifier.create(result)
                .expectNext(product1)
                .expectNext(product2)
                .verifyComplete();
    }

    @Test
    public void testCreateNewProduct() {
        Product product = new Product(null, "P1", "Product 1", "Description 1", 100, 10, "InStock", "FITNESS", null, 4);
        Mono<Product> mockProduct = Mono.just(product);

        // Mock the service
        when(productService.newProduct(product)).thenReturn(mockProduct);

        Mono<Product> result = productController.createNewProduct(product);

        StepVerifier.create(result)
                .expectNext(product)
                .verifyComplete();
    }

    @Test
    public void testGetProductDetails(){
        Product product = new Product(1, "P1", "Product 1", "Description 1", 100, 10, "InStock", "FITNESS", null, 4);
        Mono<Product> mockProduct = Mono.just(product);

        // Mock the service
        when(productService.productDetails(1)).thenReturn(mockProduct);

        Mono<Product> result = productController.getProductDetails(1);

        StepVerifier.create(result)
                .expectNext(product)
                .verifyComplete();
    }

    @Test
    public void testUpdateAProduct(){
        Product source = new Product(1, "P1", "Product 1", "Description 1", 100, 10, "InStock", "FITNESS", null, 4);
        Product product = new Product(1, "P1Modified", "Product Modified", "Description Modified", 200, 20, "LowStock", "RETAIL", null, 3);
        Mono<Product> mockProduct = Mono.just(product);

        // mock the service
        when(productService.updateProduct(1,source)).thenReturn(mockProduct);

        Mono<Product> result = productController.updateAProduct(1,source);

        StepVerifier.create(result)
                .expectNext(product)
                .verifyComplete();
    }

    @Test
    public void testDeleteProduct(){
        when(productService.deleteProduct(1)).thenReturn(Mono.just(ResponseEntity.ok("Product deleted !")));

        Mono<ResponseEntity<String>> result = productController.deleteProduct(1);

        StepVerifier.create(result)
                .expectNextMatches(responseEntity -> responseEntity.getStatusCode().is2xxSuccessful())
                .verifyComplete();
    }
}
