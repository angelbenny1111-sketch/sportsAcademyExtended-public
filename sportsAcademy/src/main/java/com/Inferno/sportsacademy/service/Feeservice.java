
package com.Inferno.sportsacademy.service;

import com.Inferno.sportsacademy.dao.Feedao;
import com.Inferno.sportsacademy.model.Fee;
import com.Inferno.sportsacademy.util.Validationutil;

import java.math.BigDecimal;
import java.util.List;

public class Feeservice {

    private Feedao feeDAO = new Feedao();

    public boolean addFee(int athleteId, double amount,
                          java.time.LocalDate paymentDate, String status) {

        if (athleteId <= 0) {
            System.out.println("Invalid athlete ID.");
            return false;
        }

        if (!Validationutil.isPositiveAmount(amount)) {
            System.out.println("Fee amount must be positive.");
            return false;
        }

        if (!Validationutil.isNotEmpty(status)) {
            System.out.println("Fee status cannot be empty.");
            return false;
        }

        Fee fee = new Fee();
        fee.setAthleteId(athleteId);
        fee.setAmount(BigDecimal.valueOf(amount));
        fee.setPaymentDate(paymentDate);
        fee.setStatus(status.trim());

        return feeDAO.addFee(fee);
    }

    public List<Fee> getAllFees() {
        return feeDAO.getAllFees();
    }
}

