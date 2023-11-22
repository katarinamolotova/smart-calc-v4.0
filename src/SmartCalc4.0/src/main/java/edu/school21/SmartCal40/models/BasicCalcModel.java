package edu.school21.SmartCal40.models;

import edu.school21.SmartCal40.enums.ErrorMessage;
import edu.school21.SmartCal40.models.helpers.Calculator;
import edu.school21.SmartCal40.models.helpers.DataCooker;
import edu.school21.SmartCal40.models.helpers.Parser;
import edu.school21.SmartCal40.models.helpers.Validator;
import javafx.util.Pair;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Queue;

@Component
@AllArgsConstructor
public class BasicCalcModel {

  static final int AROUND_VAR = 7;
  static final int MAXIMUM_NUMBER_OF_POINTS  = 10;
  private final Parser parser;

  @Getter
  private ArrayList<Double> xCoordinates;
  @Getter
  private ArrayList<Double> yCoordinates;

  public String calculateForDraw(
          final String inputString,
          final String minXAsString,
          final String maxXAsString
  ) {
    final double minX;
    final double maxX;
    try {
      minX = parseDouble(minXAsString);
      maxX = parseDouble(maxXAsString);
    } catch (final NumberFormatException e) {
      return ErrorMessage.ERROR_ARGUMENTS.getName();
    }

    String response = ErrorMessage.SUCCESS.getName();
    final double calculateStep = (Math.abs(maxX) + Math.abs(minX)) / MAXIMUM_NUMBER_OF_POINTS;
    for (double i = minX; i < maxX; i += calculateStep) {
      xCoordinates.add(i);
      response = getResult(inputString, String.valueOf(i));
      if(response.equals(ErrorMessage.ERROR_ARGUMENTS.getName())) {
        break;
      }
      yCoordinates.add(Double.parseDouble(response));
    }
    return response;
  }

  public String getResult(final String inputString, final String valueRaw) {
    try {
      final double value = parseDouble(valueRaw);
      Validator.validateData(inputString);
      final String result = DataCooker.DataCook(inputString, value);
      final Queue<Pair<String, Double>> pairs = parser.doParsing(result);
      return String.valueOf(round(Calculator.calculate(pairs)));
    } catch (final NumberFormatException e) {
      return ErrorMessage.ERROR_ARGUMENTS.getName();
    }
  }

  private double parseDouble(final String number) {
    return Double.parseDouble(!number.isEmpty() ? number : "0");
  }

  private static double round(final double value) {
      if (AROUND_VAR < 0) {
          throw new IllegalArgumentException();
      }
    try {
      BigDecimal bd = new BigDecimal(Double.toString(value));
      bd = bd.setScale(AROUND_VAR, RoundingMode.HALF_UP);
      return bd.doubleValue();
    } catch (final Exception e) {
      throw new IllegalArgumentException(ErrorMessage.ERROR_SOMETHING_WRONG.getName());
    }
  }
}
