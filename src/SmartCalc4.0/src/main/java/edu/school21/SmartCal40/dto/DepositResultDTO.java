package edu.school21.SmartCal40.dto;

import edu.school21.SmartCal40.enums.ErrorMessage;
import lombok.Getter;

@Getter
public class DepositResultDTO {
    private double sumAtTheEnd;
    private double resultPercent;
    private double sumTax;
    private boolean isBroken = false;
    private ErrorMessage errorMassage = ErrorMessage.SUCCESS;

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
            final ErrorMessage errorMassage
    ) {
        this.isBroken = error;
        this.errorMassage = errorMassage;
    }
}