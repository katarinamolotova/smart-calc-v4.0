package edu.school21.SmartCal40.dto;

import edu.school21.SmartCal40.enums.ErrorMessage;
import lombok.Getter;
import java.util.ArrayList;

@Getter
public class CreditResultDTO {
    private double overPay = 0;
    private double totalPayment = 0;
    private ArrayList<Double> everyMothPay = null;
    private boolean isBroken = false;
    private String errorMassage = ErrorMessage.SUCCESS.getName();


    public CreditResultDTO(
            final double overPay,
            final double totalPayment,
            final ArrayList<Double> everyMothPay
    ) {
        this.overPay = overPay;
        this.totalPayment = totalPayment;
        this.everyMothPay = everyMothPay;
    }

    public CreditResultDTO(
            final boolean error,
            final String errorMassage
    ) {
        this.isBroken = error;
        this.errorMassage = errorMassage;
    }
}
