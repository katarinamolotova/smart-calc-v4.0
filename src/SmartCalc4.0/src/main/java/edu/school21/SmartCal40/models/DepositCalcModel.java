package edu.school21.SmartCal40.models;

import edu.school21.SmartCal40.dto.DepositParametersDTO;
import edu.school21.SmartCal40.dto.DepositResultDTO;
import edu.school21.SmartCal40.enums.ErrorMessage;
import edu.school21.SmartCal40.enums.PeriodType;
import edu.school21.SmartCal40.enums.TermType;
import javafx.util.Pair;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class DepositCalcModel {

  private static final Integer MONTHS_OF_YEAR = 12;
  private static final Integer DAYS_OF_YEAR = 365;
  private static final Double MAX_PERCENT = 100.;
  private static final Double SCALE = 100.;


  private DepositParametersDTO startParameters = null;
  private double result = 0;
  private double intermediateSum = 0;
  private double tempPercent = 0;
  private double add = 0;
  private double sum = 0;
  private double sub = 0;
  private int countPay = 0;
  private int countCap = 0;


  public void validateInputParameters(
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
              sumBegin,
              resultPercent,
              taxPercent);
  }

  public DepositResultDTO getResult() {
    if(Objects.isNull(startParameters)) {
      return new DepositResultDTO(
              true,
              ErrorMessage.ERROR_SOMETHING_WRONG.getName()
      );

    } else if(!startParameters.isBroken()) {
      return new DepositResultDTO(
              sumAtTheEnd(startParameters),
              resultPercent(startParameters),
              sumTax(startParameters)
      );
    }
    return new DepositResultDTO(
            true,
            startParameters.getErrorMessage()
                           .getName());
  }

  private double resultPercent(final DepositParametersDTO dto) {
    sum = dto.getSumma();
    intermediateSum = dto.getSumma();
    final int period =
        (dto.getTermType() == TermType.MONTH) ? dto.getAmountOfMonth() : dto.getAmountOfMonth() * MONTHS_OF_YEAR;

    for (int i = dto.getMonthStart(); i < dto.getMonthStart() + period; i++, countCap++, countPay++) {
      final int numberOfMonth = (i + MONTHS_OF_YEAR - 1) % MONTHS_OF_YEAR;
      final int daysInMonth = (numberOfMonth == 1) ? 28 : (31 - numberOfMonth % 7 % 2);

      checkAddList(numberOfMonth, dto.getAdditions());
      checkSubList(numberOfMonth, dto.getWithdrawal());
      checkCapitalisation(dto.getCapitalizationPeriod());
      checkPeriodPay(dto.getPeriodPay());

      final double expression =
          (intermediateSum + add - sub) / MAX_PERCENT * dto.getPercent() * daysInMonth / DAYS_OF_YEAR;
      result += expression;
      tempPercent += expression;
    }
    return Math.ceil(result * SCALE) / SCALE;
  }

  private double sumAtTheEnd(final DepositParametersDTO dto) {
    double result = dto.getSumBegin() + dto.getResultPercent();
    result += dto.getAdditions().values().stream().mapToDouble(i -> i).sum();
    result += dto.getWithdrawal().values().stream().mapToDouble(i -> -1 * i).sum();
    return Math.ceil(result * SCALE) / SCALE;
  }

  private double sumTax(final DepositParametersDTO dto) {
    final double result = dto.getResultPercent() * ( dto.getTaxPercent() / MAX_PERCENT);
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
