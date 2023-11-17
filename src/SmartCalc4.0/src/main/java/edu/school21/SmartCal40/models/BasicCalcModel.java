package edu.school21.SmartCal40.models;

import edu.school21.SmartCal40.enums.ErrorMessage;
import edu.school21.SmartCal40.models.helpers.Calculator;
import edu.school21.SmartCal40.models.helpers.DataCooker;
import edu.school21.SmartCal40.models.helpers.Parser;
import edu.school21.SmartCal40.models.helpers.Validator;
import javafx.util.Pair;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Queue;

@Component
@AllArgsConstructor
public class BasicCalcModel {

  static final int AROUND_VAR = 7;
  final Parser parser;

  public String getResult(final String inputString, final String valueRaw) {
    try {
      double value = Double.parseDouble(!valueRaw.isEmpty() ? valueRaw : "0");
      Validator.validateData(inputString);
      String result = DataCooker.DataCook(inputString, value);
      Queue<Pair<String, Double>> pairs = parser.doParsing(result);
      return String.valueOf(round(Calculator.calculate(pairs)));
    } catch (NumberFormatException e) {
      return ErrorMessage.ERROR_ARGUMENTS.getName();
    }
  }

  private static double round(double value) {
      if (AROUND_VAR < 0) {
          throw new IllegalArgumentException();
      }
    try {
      BigDecimal bd = new BigDecimal(Double.toString(value));
      bd = bd.setScale(AROUND_VAR, RoundingMode.HALF_UP);
      return bd.doubleValue();
    } catch (final Exception e) {
      throw new IllegalArgumentException("Something wrong");
    }
  }
}
