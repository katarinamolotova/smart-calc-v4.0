package edu.school21.SmartCal40.models;

import edu.school21.SmartCal40.dto.CreditParametersDTO;
import edu.school21.SmartCal40.dto.CreditResultDTO;
import edu.school21.SmartCal40.enums.CreditType;
import edu.school21.SmartCal40.enums.ErrorMessage;
import edu.school21.SmartCal40.enums.TermType;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;

@Component
public class CreditCalcModel {

  private static final Double MAX_PERCENT = 100.;
  private static final Double SCALE = 100.;
  private static final Integer MONTHS_OF_YEAR = 12;
  private Double overpay;
  private Double totalPayment;
  private ArrayList<Double> everyMothPay;
  @Getter
  private CreditParametersDTO startParameters = null;

  public void validateInputParameters(
          final String type,
          final String sum,
          final String amountOfMonth,
          final String termType,
          final String percent
  ) {
    startParameters = new CreditParametersDTO(
            type,
            sum,
            amountOfMonth,
            termType,
            percent
    );
  }

  public ErrorMessage calculate() {
    overpay = 0.0;
    totalPayment = 0.0;
    everyMothPay = new ArrayList<>();
    if(Objects.isNull(startParameters)) {
      return ErrorMessage.ERROR_SOMETHING_WRONG;
    } else if (startParameters.isBroken()) {
      return startParameters.getErrorMessage();
    } else {
      everyMothPay(startParameters);
      totalPayment();
      overpay(startParameters.getSum());
      return startParameters.getErrorMessage();
    }
  }

  private void everyMothPay(final CreditParametersDTO dto) {
    double dynamicSum = dto.getSum();
    final int period =
        (dto.getTermType() == TermType.MONTH)
                ? dto.getAmountOfMonth()
                : dto.getAmountOfMonth() * MONTHS_OF_YEAR;
    final double newPercent = dto.getPercent() / MAX_PERCENT / MONTHS_OF_YEAR;

    for (int i = 0; i < period; i++) {
      final double result;
      if (dto.getType() == CreditType.ANNUITY) {
        result = dto.getSum() * (newPercent / (1. - Math.pow(1. + newPercent, (-1. * period))));
      } else {
        result = dto.getSum() / period + dynamicSum * newPercent;
      }
      everyMothPay.add(Math.ceil(result * SCALE) / SCALE);
      dynamicSum -= everyMothPay.get(i);
    }
  }

  private void totalPayment() {
    totalPayment = everyMothPay.stream().mapToDouble(i -> i).sum();
    totalPayment = Math.ceil(totalPayment * SCALE) / SCALE;
  }

  private void overpay(final double sum) {
    overpay = Math.ceil((totalPayment - sum) * SCALE) / SCALE;
  }

  public CreditResultDTO getResult() {
    return new CreditResultDTO(overpay, totalPayment, everyMothPay);
  }
}
