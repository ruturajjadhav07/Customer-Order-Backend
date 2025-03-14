package com.order.ordercart.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    // add product
    @PostMapping("/addproduct")
    public ResponseEntity<String> addProduct(ProductModel product) {
        productService.addProduct(product.getProductName(), product.getProductPrice(), product.getProductDescription(),
                product.getProductQuantity());
        return ResponseEntity.ok("Product added successfully");
    }
}
