package edu.school21.SmartCal40.dto;
import edu.school21.SmartCal40.enums.Status;
import edu.school21.SmartCal40.enums.PeriodType;
import edu.school21.SmartCal40.enums.TermType;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@ToString
public class DepositParametersDTO {
    private double summa;
    private int amountOfMonth;
    private TermType termType;
    private double percent;
    private PeriodType capitalizationPeriod;
    private PeriodType periodPay;
    private int monthStart;
    private LocalDate start;
    private double additions;
    private double withdrawal;
    private double taxPercent;
    private boolean isBroken;
    private Status status = Status.SUCCESS;

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
            this.start = LocalDate.parse(monthStart, DateTimeFormatter.ISO_LOCAL_DATE);
            this.monthStart = this.start.getMonthValue();
            this.additions = Double.parseDouble(additions);
            this.withdrawal = Double.parseDouble(withdrawal);
            this.taxPercent = Double.parseDouble(taxPercent);
        } catch (final NullPointerException e) {
            this.status = Status.ERROR_SOMETHING_WRONG;
            this.isBroken = true;
        } catch (final NumberFormatException e) {
            this.status = Status.ERROR_WRONG_ARGUMENT;
            this.isBroken = true;
        }
    }

    public DepositParametersDTO() {
        this.summa = 1000000;
        this.amountOfMonth = 12;
        this.termType = TermType.YEAR;
        this.percent = 5;
        this.capitalizationPeriod = PeriodType.MONTHLY;
        this.periodPay = PeriodType.MONTHLY;
        this.start = LocalDate.now();
        this.taxPercent = 13;
        this.isBroken = false;
    }
}
