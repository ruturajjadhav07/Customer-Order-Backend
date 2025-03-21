package com.order.ordercart.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
// @RequestMapping("/customers")
public class ProductController {

    @Autowired
    private ProductService productService;

    // add product by admin
    @PreAuthorize("hasRole('ADMIN')") // only admins can access this
    @PostMapping("/admin/addproduct")
    public ResponseEntity<String> addProduct(@RequestBody ProductModel product) {
        productService.addProduct(
                product.getProductName(),
                product.getProductPrice(),
                product.getProductDescription(),
                product.getProductCategory(),
                product.getProductQuantity());
        return ResponseEntity.ok("Product added successfully");
    }

    // get all products
    @GetMapping("/allproducts")
    public List<ProductModel> getAppProducts() {
        return productService.getAllProducts();
    }

    // get product by category
    @GetMapping("/allproducts/category")
    public List<ProductModel> getProductByCategory(@RequestParam String category) {
        return productService.getProductByCategory(category);
    }

    // get product within price range
    @GetMapping("/allproducts/price-range")
    public List<ProductModel> getProductByPriceRange(@RequestParam double minPrice, @RequestParam double maxPrice) {
        return productService.getProductByPriceRange(minPrice, maxPrice);
    }

    // get product with search name
    @GetMapping("/allproducts/search")
    public List<ProductModel> getProductBySearch(@RequestParam String name) {
        return productService.getProductBySearch(name);
    }

}
