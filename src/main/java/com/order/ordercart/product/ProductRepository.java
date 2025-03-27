package com.order.ordercart.product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {

    // get product by category
    List<ProductModel> findByProductCategory(String productCategory);

    // get product with search name
    List<ProductModel> findByProductNameContainingIgnoreCase(String productName);

    // delete product
    Optional<ProductModel> findByProductName(String string);
}
