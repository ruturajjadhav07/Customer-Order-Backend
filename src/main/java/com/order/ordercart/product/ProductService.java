package com.order.ordercart.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // add product
    public ProductModel addProduct(String name, double price, String description, int quantity) {

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Please enter name");
        }

        if (price <= 0) {
            throw new IllegalArgumentException("Please enter price");
        }

        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Please enter description");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Atleast 1 quantity should be there");
        }

        ProductModel product = new ProductModel();
        product.setProductName(name);
        product.setProductPrice(price);
        product.setProductDescription(description);
        product.setProductQuantity(quantity);

        return productRepository.save(product);

    }

    // get all products
}
