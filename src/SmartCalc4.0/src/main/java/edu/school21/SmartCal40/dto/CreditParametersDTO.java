package edu.school21.SmartCal40.dto;

import edu.school21.SmartCal40.enums.CreditType;
import edu.school21.SmartCal40.enums.Status;
import edu.school21.SmartCal40.enums.TermType;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CreditParametersDTO {
    private CreditType type;
    private double sum;
    private int amountOfMonth;
    private TermType termType;
    private double percent;
    private boolean isBroken;
    private Status status = Status.SUCCESS;

    public CreditParametersDTO(
            final String type,
            final String sum,
            final String amountOfMonth,
            final String termType,
            final String percent
    ) {
        try {
            this.type = CreditType.getCreditType(type);
            this.sum = Double.parseDouble(sum);
            this.amountOfMonth = Integer.parseInt(amountOfMonth);
            this.termType = TermType.getTermType(termType);
            this.percent = Double.parseDouble(percent);
        } catch (final NumberFormatException e) {
            isBroken = true;
            status = Status.ERROR_WRONG_ARGUMENT;
        } catch (final NullPointerException e) {
            isBroken = true;
            status = Status.ERROR_SOMETHING_WRONG;
        }
    }

    public CreditParametersDTO() {
        this.type = CreditType.ANNUITY;
        this.sum = 1000000;
        this.amountOfMonth = 12;
        this.termType = TermType.MONTH;
        this.percent = 5;
        this.isBroken = false;
    }
}

