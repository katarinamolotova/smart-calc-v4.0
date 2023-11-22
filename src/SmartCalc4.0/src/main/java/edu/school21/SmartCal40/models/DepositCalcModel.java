package edu.school21.SmartCal40.models;

import edu.school21.SmartCal40.dto.DepositParametersDTO;
import edu.school21.SmartCal40.dto.DepositResultDTO;
import edu.school21.SmartCal40.enums.Status;
import edu.school21.SmartCal40.enums.PeriodType;
import edu.school21.SmartCal40.enums.TermType;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DepositCalcModel {

  private static final Integer MONTHS_OF_YEAR = 12;
  private static final Integer DAYS_OF_YEAR = 365;
  private static final Double MAX_PERCENT = 100.;
  private static final Double SCALE = 100.;

  @Getter
  private DepositParametersDTO startParameters = null;
  private double result;
  private double resultPercent;
  private double intermediateSum;
  private double tempPercent;
  private double sum;
  private int countPay;
  private int countCap;

  public void validateInputParameters(
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
    startParameters = new DepositParametersDTO(
            summa,
            amountOfMonth,
            termType,
            percent,
            capitalizationPeriod,
            periodPay,
            monthStart,
            additions,
            withdrawal,
            taxPercent);
  }

  public DepositResultDTO getResult() {
    if(Objects.isNull(startParameters)) {
      return new DepositResultDTO(
              true,
              Status.ERROR_SOMETHING_WRONG
      );

    } else if(!startParameters.isBroken()) {
      resultPercent = resultPercent(startParameters);
      sumTax(startParameters);
      sumAtTheEnd(startParameters);

      return new DepositResultDTO(
              sumAtTheEnd(startParameters),
              resultPercent,
              sumTax(startParameters)
      );
    }
    return new DepositResultDTO(
            true,
            startParameters.getStatus()
    );
  }

  private double resultPercent(final DepositParametersDTO dto) {
    sum = dto.getSumma();
    intermediateSum = dto.getSumma();
    final int period = (dto.getTermType() == TermType.MONTH)
            ? dto.getAmountOfMonth()
            : dto.getAmountOfMonth() * MONTHS_OF_YEAR;

    for (int i = dto.getMonthStart(); i < dto.getMonthStart() + period; i++, countCap++, countPay++) {
      final int numberOfMonth = (i + MONTHS_OF_YEAR - 1) % MONTHS_OF_YEAR;
      final int daysInMonth = (numberOfMonth == 1) ? 28 : (31 - numberOfMonth % 7 % 2);

      checkCapitalisation(dto.getCapitalizationPeriod());
      checkPeriodPay(dto.getPeriodPay());

      final double expression = (intermediateSum + dto.getAdditions() - dto.getWithdrawal() * -1)
                                / MAX_PERCENT
                                * dto.getPercent()
                                * daysInMonth
                                / DAYS_OF_YEAR;
      result += expression;
      tempPercent += expression;
    }
    return Math.ceil(result * SCALE) / SCALE;
  }

  private double sumAtTheEnd(final DepositParametersDTO dto) {
    double result = dto.getSumma() + resultPercent;
    final int period = dto.getTermType() == TermType.MONTH
                          ? dto.getAmountOfMonth()
                          : dto.getAmountOfMonth() * MONTHS_OF_YEAR;
    result += dto.getAdditions() * period;
    result -= dto.getWithdrawal() * period;
    return Math.ceil(result * SCALE) / SCALE;
  }

  private double sumTax(final DepositParametersDTO dto) {
    final double result = resultPercent * ( dto.getTaxPercent() / MAX_PERCENT);
    return Math.ceil(result * SCALE) / SCALE;
  }

  private void checkCapitalisation(final PeriodType capitalisationPeriod) {
    if (capitalisationPeriod.getCountOfMonths() != 0
        && countCap == capitalisationPeriod.getCountOfMonths()
    ) {
      intermediateSum += tempPercent;
      countCap = 0;
      tempPercent = 0;
    }
  }

  private void checkPeriodPay(final PeriodType periodPay) {
    if (periodPay.getCountOfMonths() != 0
        && countPay == periodPay.getCountOfMonths()
    ) {
      intermediateSum = sum;
      countPay = 0;
    }
  }
}
