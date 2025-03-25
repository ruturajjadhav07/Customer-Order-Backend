package com.order.ordercart.product;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.order.ordercart.exception.ProductNotFoundException;
import com.order.ordercart.orderitem.OrderItemRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

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
    public List<ProductModel> getProductBySearch(String name) {
        return productRepository.findByProductNameContainingIgnoreCase(name);
    }

    // check stock quantity of product
    public int getProductQuantity(long productId) {
        ProductModel product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));

        return product.getProductQuantity();
    }

    // Update product details by admin
    @Transactional
    public ProductModel updateProduct(long product_id, ProductModel product) {
        ProductModel updateProduct = productRepository.findById(product_id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + product_id));

        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }

        if (product.getProductPrice() <= 0) {
            throw new IllegalArgumentException("Product price cannot be null or negative");
        }

        if (product.getProductDescription() == null || product.getProductDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Product description cannot be empty");
        }

        if (product.getProductCategory() == null || product.getProductCategory().trim().isEmpty()) {
            throw new IllegalArgumentException("Product category cannot be empty");
        }

        if (product.getProductQuantity() <= 0) {
            throw new IllegalArgumentException("Product quantity cannot be null or negative");
        }

        updateProduct.setProductName(product.getProductName());
        updateProduct.setProductPrice(product.getProductPrice());
        updateProduct.setProductDescription(product.getProductDescription());
        updateProduct.setProductCategory(product.getProductCategory());
        updateProduct.setProductQuantity(product.getProductQuantity());

        return productRepository.save(updateProduct);
    }

    // delete product by admin
    @Transactional
    public void deleteProduct(long product_id) {
        // Check if a "Deleted Product" exists if not, create it
        ProductModel deletedProduct = productRepository.findByProductName("Deleted Product")
                .orElseGet(() -> {
                    ProductModel newDeletedProduct = new ProductModel();
                    newDeletedProduct.setProductName("Deleted Product");
                    newDeletedProduct.setProductPrice(0.0);
                    newDeletedProduct.setProductDescription("This product was deleted by admin.");
                    newDeletedProduct.setProductCategory("N/A");
                    newDeletedProduct.setProductQuantity(0);
                    return productRepository.save(newDeletedProduct);
                });

        // Updating all order items referencing this product to point to the Deleted
        // Product
        orderItemRepository.updateProductReference(product_id, deletedProduct.getProductId());

        // Delete the actual product
        ProductModel product = productRepository.findById(product_id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + product_id));
        productRepository.delete(product);
    }

}
