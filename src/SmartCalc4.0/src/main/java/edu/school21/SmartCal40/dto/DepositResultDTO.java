package edu.school21.SmartCal40.dto;

import edu.school21.SmartCal40.enums.Status;
import lombok.Getter;

@Getter
public class DepositResultDTO {
    private double sumAtTheEnd;
    private double resultPercent;
    private double sumTax;
    private boolean isBroken;
    private Status errorMassage = Status.SUCCESS;

    public DepositResultDTO(
            final double sumAtTheEnd,
            final double resultPercent,
            final double sumTax
    ) {
        this.sumAtTheEnd = sumAtTheEnd;
        this.resultPercent = resultPercent;
        this.sumTax = sumTax;
    }

    public DepositResultDTO(
            final boolean error,
            final Status errorMassage
    ) {
        this.isBroken = error;
        this.errorMassage = errorMassage;
    }
}