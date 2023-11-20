package edu.school21.SmartCal40.dto;
import edu.school21.SmartCal40.enums.ErrorMessage;
import edu.school21.SmartCal40.enums.PeriodType;
import edu.school21.SmartCal40.enums.TermType;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Map;

@Getter
public class DepositParametersDTO {
    double summa = 0;
    int amountOfMonth = 0;
    TermType termType = null;
    double percent = 0;
    PeriodType capitalizationPeriod = null;
    PeriodType periodPay = null;
    int monthStart = 0;
    Map<LocalDate, Double> additions = null;
    Map<LocalDate, Double> withdrawal = null;
    double sumBegin = 0;
    double resultPercent = 0;
    double taxPercent = 0;
    boolean isBroken = false;
    ErrorMessage errorMessage = ErrorMessage.SUCCESS;

    public DepositParametersDTO(
            final String summa,
            final String amountOfMonth,
            final String termType,
            final String percent,
            final String capitalizationPeriod,
            final String periodPay,
            final String monthStart,
            Map<LocalDate, Double> additions,
            Map<LocalDate, Double> withdrawal,
            final String sumBegin,
            final String resultPercent,
            final String taxPercent
    ) {
        try {
            this.summa = Double.parseDouble(summa);
            this.amountOfMonth = Integer.parseInt(amountOfMonth);
            this.termType = TermType.getTermType(termType);
            this.percent = Double.parseDouble(percent);
            this.capitalizationPeriod = PeriodType.getPeriodType(capitalizationPeriod);
            this.periodPay = PeriodType.getPeriodType(periodPay);
            this.monthStart = Integer.parseInt(monthStart);
            this.additions = additions;
            this.withdrawal = withdrawal;
            this.sumBegin = Double.parseDouble(sumBegin);
            this.resultPercent = Double.parseDouble(resultPercent);
            this.taxPercent = Double.parseDouble(taxPercent);
        } catch (NullPointerException e) {
            this.errorMessage = ErrorMessage.ERROR_SOMETHING_WRONG;
            this.isBroken = true;
        } catch (NumberFormatException e) {
            this.errorMessage = ErrorMessage.ERROR_WRONG_ARGUMENT;
            this.isBroken = true;
        }
    }
}
