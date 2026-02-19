package com.hector.hotdogtaz.model;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "commands")
public class Command {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int number;
    private BigDecimal total;
    private String observation;
    private LocalDate openingDate;
    private LocalDate closingDate;
    private Status commandStatus;

    protected Command(){}

    public Command(Long id, int number, BigDecimal total, String observation, LocalDate openingDate, LocalDate closingDate){
        this.id=id;
        this.number=number;
        this.total=total;
        this.observation=observation;
        this.openingDate=openingDate;
        this.closingDate=closingDate;
    }

    @PrePersist
    protected void onCreate() {
        this.openingDate = LocalDate.now();
    }

    public enum Status{
        CREATED,
        IN_PREPARATION,
        READY,
        COMPLETED,
        CANCELED
    }




    public Long getId() {
        return id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getObservation() {
        return observation;
    }

    public LocalDate getOpeningDate() {
        return openingDate;
    }

    public LocalDate getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(LocalDate closingDate) {
        this.closingDate = closingDate;
    }
}
