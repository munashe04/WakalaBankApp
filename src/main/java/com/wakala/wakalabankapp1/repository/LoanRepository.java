package com.wakala.wakalabankapp1.repository;

import com.wakala.wakalabankapp1.entity.Loan;
import com.wakala.wakalabankapp1.service.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan,String> {

    Optional<Loan> findByMobileNumberAndStatus(String mobileNumber, String pending);

}
