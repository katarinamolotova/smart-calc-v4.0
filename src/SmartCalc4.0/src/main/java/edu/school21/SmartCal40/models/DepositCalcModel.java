package edu.school21.SmartCal40.models;

import edu.school21.SmartCal40.enums.PeriodType;
import edu.school21.SmartCal40.enums.TermType;
import javafx.util.Pair;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class DepositCalcModel {

  private static final Integer MONTHS_OF_YEAR = 12;
  private static final Integer DAYS_OF_YEAR = 365;
  private static final Double MAX_PERCENT = 100.;
  private static final Double SCALE = 100.;

  private double result;
  private double intermediateSum;
  private double tempPercent;
  private double add;
  private double sum;
  private double sub;
  private int countPay;
  private int countCap;

  public double resultPercent(
      final double summa,
      final int amountOfMonthsOrYears,
      final TermType termType,
      final double percent,
      final PeriodType capitalizationPeriod,
      final PeriodType periodPay,
      final int monthStart,
      final Map<LocalDate, Double> additions,
      final Map<LocalDate, Double> withdrawal
  ) {
    sum = summa;
    intermediateSum = summa;
    final int period =
        (termType == TermType.MONTH) ? amountOfMonthsOrYears : amountOfMonthsOrYears * MONTHS_OF_YEAR;

    for (int i = monthStart; i < monthStart + period; i++, countCap++, countPay++) {
      // счет с нуля
      final int numberOfMonth = (i + MONTHS_OF_YEAR - 1) % MONTHS_OF_YEAR;
      final int daysInMonth = (numberOfMonth == 1) ? 28 : (31 - numberOfMonth % 7 % 2);

      checkAddList(numberOfMonth, additions);
      checkSubList(numberOfMonth, withdrawal);
      checkCapitalisation(capitalizationPeriod);
      checkPeriodPay(periodPay);

      final double expression =
          (intermediateSum + add - sub) / MAX_PERCENT * percent * daysInMonth / DAYS_OF_YEAR;
      result += expression;
      tempPercent += expression;
    }
    return Math.ceil(result * SCALE) / SCALE;
  }

  public double sumAtTheEnd(
      final double sumBegin,
      final double resultPercent,
      final Map<LocalDate, Double> additions,
      final Map<LocalDate, Double> withdrawal
  ) {
    double result = sumBegin + resultPercent;
    result += additions.values().stream().mapToDouble(i -> i).sum();
    result += withdrawal.values().stream().mapToDouble(i -> -1 * i).sum();
    return Math.ceil(result * SCALE) / SCALE;
  }

  public double sumTax(final double taxPercent, final double resultPercent) {
    final double result = resultPercent * (taxPercent / MAX_PERCENT);
    return Math.ceil(result * SCALE) / SCALE;
  }

  private void checkAddList(
      final int numberOfMonth,
      final Map<LocalDate, Double> additions
  ) {
    final Pair<Integer, Double> countAndSum = checkList(numberOfMonth, additions);
    add += countAndSum.getValue();
  }

  private void checkSubList(
      final int numberOfMonth,
      final Map<LocalDate, Double> withdrawal
  ) {
    final Pair<Integer, Double> countAndSum = checkList(numberOfMonth, withdrawal);
    sub += countAndSum.getValue();
  }

  private Pair<Integer, Double> checkList(
      final int numberOfMonth,
      final Map<LocalDate, Double> list
  ) {
    final AtomicReference<Double> add = new AtomicReference<>(0d);
    final AtomicReference<Integer> index = new AtomicReference<>(0);
    final List<LocalDate> deleteList = new ArrayList<>();
    list.forEach((key, value) -> {
      final int number = key.getMonthValue() - 1;
      if (numberOfMonth == number) {
        add.updateAndGet(v -> v + list.get(key));
        index.getAndSet(index.get() + 1);
        deleteList.add(key);
      }
    });
    deleteList.forEach(list::remove);
    return new Pair<>(index.get(), add.get());
  }

  private void checkCapitalisation(final PeriodType capitalisationPeriod) {
    if (capitalisationPeriod.getCountOfMonths() != 0
        && countCap == capitalisationPeriod.getCountOfMonths()) {
      intermediateSum += tempPercent;
      countCap = 0;
      tempPercent = 0;
    }
  }

  private void checkPeriodPay(final PeriodType periodPay) {
    if (periodPay.getCountOfMonths() != 0 && countPay == periodPay.getCountOfMonths()) {
      intermediateSum = sum;
      countPay = 0;
    }
  }
}
