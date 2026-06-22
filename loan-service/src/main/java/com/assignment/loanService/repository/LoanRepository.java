package com.assignment.loanService.repository;


import com.assignment.loanService.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    boolean existsByBookIdAndReturnDateIsNull(Long bookId);
    Page<Loan> findByReturnDateIsNull(Pageable pageable);
}