package com.assignment.loanService.controller.v1;



import com.assignment.loanService.dto.common.PagedResponse;
import com.assignment.loanService.dto.loan.LoanRequest;
import com.assignment.loanService.dto.loan.LoanResponse;
import com.assignment.loanService.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@RestController
@RequestMapping("/api/v1/loans")
@Tag(name = "Loans", description = "Endpoints for managing book loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @Operation(summary = "Create a new loan", description = "Creates a new loan for a book if it is available")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Loan created successfully"),
            @ApiResponse(responseCode = "400", description = "Book is already on loan or request is invalid"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @PostMapping
    public ResponseEntity<LoanResponse> createLoan(@Valid @RequestBody LoanRequest request) {
        LoanResponse response = loanService.createLoan(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get all active loans", description = "Returns all currently active book loans")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loans retrieved successfully")
    })

    @GetMapping
    public ResponseEntity<PagedResponse<LoanResponse>> getAllLoans(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return ResponseEntity.ok(loanService.getAllLoans(pageable));
    }
}