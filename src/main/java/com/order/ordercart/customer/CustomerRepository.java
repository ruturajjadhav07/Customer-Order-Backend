package com.order.ordercart.customer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerModel, Long> {

    // check existing email
    boolean existsByEmail(String email);

    // login by email
    Optional<CustomerModel> findByEmail(String email);
}
