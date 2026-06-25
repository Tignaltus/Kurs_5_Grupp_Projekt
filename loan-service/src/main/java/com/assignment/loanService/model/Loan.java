package com.assignment.loanService.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate loanDate;

    private LocalDate returnDate;

    @Column(nullable = false)
    private Long bookId;

    public Loan() {
    }

    public Loan(LocalDate loanDate, LocalDate returnDate, Long bookId) {
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.bookId = bookId;
    }

    //<editor-fold desc="Getters">

    public Long getId() {
        return id;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    //</editor-fold>
}