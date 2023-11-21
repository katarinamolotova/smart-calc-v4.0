package edu.school21.SmartCal40.dto;
import edu.school21.SmartCal40.enums.ErrorMessage;
import edu.school21.SmartCal40.enums.PeriodType;
import edu.school21.SmartCal40.enums.TermType;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
public class DepositParametersDTO {
    double summa;
    int amountOfMonth;
    TermType termType = null;
    double percent;
    PeriodType capitalizationPeriod = null;
    PeriodType periodPay = null;
    int monthStart;
    LocalDate start;
    double additions;
    double withdrawal;
    double taxPercent;
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
            final String additions,
            final String withdrawal,
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
            this.start = LocalDate.parse(monthStart, DateTimeFormatter.ISO_LOCAL_DATE);
            this.additions = Double.parseDouble(additions);
            this.withdrawal = Double.parseDouble(withdrawal) * -1;
            this.taxPercent = Double.parseDouble(taxPercent);
        } catch (NullPointerException e) {
            this.errorMessage = ErrorMessage.ERROR_SOMETHING_WRONG;
            this.isBroken = true;
        } catch (NumberFormatException e) {
            this.errorMessage = ErrorMessage.ERROR_WRONG_ARGUMENT;
            this.isBroken = true;
        }
    }

    public DepositParametersDTO() {
        this.summa = 1000;
        this.amountOfMonth = 12;
        this.termType = TermType.YEAR;
        this.percent = 5;
        this.capitalizationPeriod = PeriodType.MONTHLY; // не работает
        this.periodPay = PeriodType.MONTHLY; // не работает
        this.start = LocalDate.now();
        this.taxPercent = 13;
        this.isBroken = false;
    }
}
