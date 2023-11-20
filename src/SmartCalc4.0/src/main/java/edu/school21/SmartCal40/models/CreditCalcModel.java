package edu.school21.SmartCal40.models;

import edu.school21.SmartCal40.dto.CreditResultDTO;
import edu.school21.SmartCal40.enums.CreditType;
import edu.school21.SmartCal40.enums.ErrorMessage;
import edu.school21.SmartCal40.enums.TermType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@AllArgsConstructor
public class CreditCalcModel {

  private static final Double MAX_PERCENT = 100.;
  private static final Double SCALE = 100.;
  private static final Integer MONTHS_OF_YEAR = 12;
  private double overpay;
  private double totalPayment;

  private ArrayList<Double> everyMothPay;

  public CreditCalcModel() {
    this.overpay = 0;
    this.totalPayment = 0;
  }

  public ErrorMessage calculate(
          final String type,
          final String sum,
          final String amountOfMonth,
          final String termType,
          final String percent
  ) {
    try {
      everyMothPay(
              CreditType.getCreditType(type),
              Double.parseDouble(sum),
              Integer.parseInt(amountOfMonth),
              TermType.getTermType(termType),
              Double.parseDouble(percent)
      );
    } catch (NullPointerException e) {
      return ErrorMessage.ERROR_SOMETHING_WRONG;
    } catch (NumberFormatException e) {
      return ErrorMessage.ERROR_WRONG_ARGUMENT;
    }
    totalPayment();
    overpay(Double.parseDouble(sum));
    return ErrorMessage.SUCCESS;
  }

  private void everyMothPay(
      final CreditType type,
      final double sum,
      final int amountOfMonth,
      final TermType termType,
      final double percent
  ) {
    ArrayList<Double>everyMothPay = new ArrayList<>();
    double dynamicSum = sum;
    final int period =
        (termType == TermType.MONTH) ? amountOfMonth : amountOfMonth * MONTHS_OF_YEAR;
    final double newPercent = percent / MAX_PERCENT / MONTHS_OF_YEAR;

    for (int i = 0; i < period; i++) {
      final double result;
      if (type == CreditType.ANNUITY) {
        result = sum * (newPercent / (1. - Math.pow(1. + newPercent, (-1. * period))));
      } else {
        result = sum / period + dynamicSum * newPercent;
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
