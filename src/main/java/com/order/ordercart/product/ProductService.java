package com.order.ordercart.product;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // add product
    public ProductModel addProduct(String name, double price, String description, String category, int quantity) {

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Please enter name");
        }

        if (price <= 0) {
            throw new IllegalArgumentException("Please enter price");
        }

        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Please enter description");
        }

        if (category == null || category.isEmpty()) {
            throw new IllegalArgumentException("Category cannot be empty");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Atleast 1 quantity should be there");
        }

        ProductModel product = new ProductModel();
        product.setProductName(name);
        product.setProductPrice(price);
        product.setProductDescription(description);
        product.setProductCategory(category);
        product.setProductQuantity(quantity);

        return productRepository.save(product);

    }

    // get all products
    public List<ProductModel> getAllProducts() {
        return productRepository.findAll();
    }

    // get product by category
    public List<ProductModel> getProductByCategory(String category) {
        List<ProductModel> products = productRepository.findByProductCategory(category);

        if (products.isEmpty()) {
            throw new IllegalArgumentException("Category " + category + " is not available");
        }

        return products;
    }

    // get product within price range
    public List<ProductModel> getProductByPriceRange(double minPrice, double maxPrice) {
        List<ProductModel> product = productRepository.findAll();

        return product.stream().filter(p -> p.getProductPrice() >= minPrice && p.getProductPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    // get product with search name
    public List<ProductModel> getProductBySearch(String productName) {
        return productRepository.findByProductNameContainingIgnoreCase(productName);
    }

}
