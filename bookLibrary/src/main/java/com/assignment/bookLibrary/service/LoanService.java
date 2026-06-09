package com.assignment.bookLibrary.service;

import com.assignment.bookLibrary.dto.common.PagedResponse;
import com.assignment.bookLibrary.dto.loan.LoanRequest;
import com.assignment.bookLibrary.dto.loan.LoanResponse;
import com.assignment.bookLibrary.exception.BookAlreadyLoanedException;
import com.assignment.bookLibrary.exception.BookNotFoundException;
import com.assignment.bookLibrary.model.Book;
import com.assignment.bookLibrary.model.Loan;
import com.assignment.bookLibrary.repository.BookRepository;
import com.assignment.bookLibrary.repository.LoanRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;

    public LoanService(LoanRepository loanRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public LoanResponse createLoan(LoanRequest request) {
        Long bookId = request.getBookId();

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book with id " + bookId + " not found"));

        if (loanRepository.existsByBookIdAndReturnDateIsNull(bookId)) {
            throw new BookAlreadyLoanedException("Book is already on loan");
        }

        Loan loan = new Loan(
                LocalDate.now(),
                null,
                book
        );

        try {
            Loan savedLoan = loanRepository.saveAndFlush(loan);
            return mapToResponse(savedLoan);
        } catch (DataIntegrityViolationException e) {
            throw new BookAlreadyLoanedException("Book is already on loan");
        }
    }

    public PagedResponse<LoanResponse> getAllLoans(Pageable pageable) {
        Page<LoanResponse> page = loanRepository.findByReturnDateIsNull(pageable)
                .map(this::mapToResponse);

        return new PagedResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    private LoanResponse mapToResponse(Loan loan) {
        return new LoanResponse(
                loan.getId(),
                loan.getBook().getId(),
                loan.getBook().getTitle(),
                loan.getLoanDate(),
                loan.getReturnDate()
        );
    }
}