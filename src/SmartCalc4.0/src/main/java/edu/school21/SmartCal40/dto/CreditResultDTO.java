package edu.school21.SmartCal40.dto;

import edu.school21.SmartCal40.enums.Status;
import lombok.Getter;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class CreditResultDTO {
    private double overPay;
    private double totalPayment;
    private String everyMonthPay;
    private boolean isBroken;
    private Status errorMassage = Status.SUCCESS;
    
    public CreditResultDTO(
            final double overPay,
            final double totalPayment,
            final ArrayList<Double> everyMonthPay
    ) {
        this.overPay = overPay;
        this.totalPayment = totalPayment;
        this.everyMonthPay = getEveryMonthPayAsString(everyMonthPay);
    }

    public CreditResultDTO(final boolean error, final Status errorMassage) {
        this.isBroken = error;
        this.errorMassage = errorMassage;
    }

    private String getEveryMonthPayAsString(final ArrayList<Double> everyMonthPay) {
        return IntStream
                .range(0, everyMonthPay.size())
                .mapToObj(i -> String.format("Месяц %d: %f руб.\n", i + 1, everyMonthPay.get(i)))
                .collect(Collectors.joining());
    }
}
