
package com.Inferno.sportsacademy.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Fee {

    private int feeId;
    private int athleteId;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private String status;

    public Fee() {
    }

    public Fee(int feeId, int athleteId, BigDecimal amount,
               LocalDate paymentDate, String status) {
        this.feeId = feeId;
        this.athleteId = athleteId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.status = status;
    }

    public Fee(int athleteId, BigDecimal amount,
               LocalDate paymentDate, String status) {
        this.athleteId = athleteId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.status = status;
    }

    public int getFeeId() {
        return feeId;
    }

    public void setFeeId(int feeId) {
        this.feeId = feeId;
    }

    public int getAthleteId() {
        return athleteId;
    }

    public void setAthleteId(int athleteId) {
        this.athleteId = athleteId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
