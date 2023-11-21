package edu.school21.SmartCal40.dto;

import edu.school21.SmartCal40.enums.CreditType;
import edu.school21.SmartCal40.enums.ErrorMessage;
import edu.school21.SmartCal40.enums.TermType;
import lombok.Getter;

@Getter
public class CreditParametersDTO {
    CreditType type;
    double sum;
    int amountOfMonth;
    TermType termType;
    double percent;
    boolean isBroken = false;
    ErrorMessage errorMessage = ErrorMessage.SUCCESS;

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
        } catch (NumberFormatException e) {
            isBroken = true;
            errorMessage = ErrorMessage.ERROR_WRONG_ARGUMENT;
        } catch (NullPointerException e) {
            isBroken = true;
            errorMessage = ErrorMessage.ERROR_SOMETHING_WRONG;
        }
    }
}
