package com.order.ordercart.customer;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomerPrinciple implements UserDetails {

    private final CustomerModel customerModel;

    public CustomerPrinciple(CustomerModel customerModel) {
        this.customerModel = customerModel;
    }

    @Override
    // public Collection<? extends GrantedAuthority> getAuthorities() {
    // return Collections.singleton(new
    // SimpleGrantedAuthority("ROLE_"+customerModel.getRole().name()));
    // }
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = "ROLE_" + customerModel.getRole().name();
        System.out.println("User Role: " + role); // 🛠 Debugging statement
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return customerModel.getPassword();
    }

    @Override
    public String getUsername() {
        return customerModel.getEmail(); // ✅ Make sure this method exists in CustomerModel
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
