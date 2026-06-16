package com.assignment.loanService.service;

import com.assignment.loanService.client.BookClient;
import com.assignment.loanService.dto.common.PagedResponse;
import com.assignment.loanService.exception.BookAlreadyLoanedException;
import com.assignment.loanService.dto.book.v1.BookDTO;
import com.assignment.loanService.dto.loan.LoanRequest;
import com.assignment.loanService.dto.loan.LoanResponse;
import com.assignment.loanService.exception.BookNotFoundException;
import com.assignment.loanService.model.Loan;
import com.assignment.loanService.repository.LoanRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookClient bookClient;

    public LoanService(LoanRepository loanRepository, BookClient bookClient) {
        this.loanRepository = loanRepository;
        this.bookClient = bookClient;
    }

    @Transactional
    public LoanResponse createLoan(LoanRequest request) {
        BookDTO book = bookClient.getBookById(request.getBookId());

        if (loanRepository.existsByBookIdAndReturnDateIsNull(request.getBookId())) {
            throw new BookAlreadyLoanedException("Book is already on loan");
        }

        Loan loan = new Loan(
                LocalDate.now(),
                null,
                request.getBookId()
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

        BookDTO book = bookClient.getBookById(loan.getBookId());

        String title = book != null ? book.title() : "Book not found";

        return new LoanResponse(
                loan.getId(),
                loan.getBookId(),
                title,
                loan.getLoanDate(),
                loan.getReturnDate()
        );
    }
}