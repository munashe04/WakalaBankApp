package com.wakala.wakalabankapp1.repository;

import com.wakala.wakalabankapp1.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,String> {
    Optional<Customer> findByMobileNumber(String mobileNumber);
}
