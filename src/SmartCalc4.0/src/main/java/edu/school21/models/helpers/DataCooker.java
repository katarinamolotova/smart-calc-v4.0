package edu.school21.models.helpers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@AllArgsConstructor
public class DataCooker {

  private static final String REGEX = "[-+]?(?:\\d+(?:\\.\\d*)?|\\.\\d+)(?:[eE][-+]?\\d+)";

  public static String DataCook(final String rawData, final double xValue) {
    String temp = replaceValueX(rawData, xValue);
    temp = exponentialEntryReplacement(temp);
    temp = insertMultiple(temp);
    return temp;
  }

  private static String insertMultiple(final String inputString) {
    String result = inputString;
    for (int i = 1; i < result.length(); i++) {
      if (result.charAt(i) == '(' && (Character.isDigit(result.charAt(i - 1))
          || result.charAt(i - 1) == ')')) {
        String subBegin = result.substring(0, i);
        String subEnd = result.substring(i);
        result = subBegin + '*' + subEnd;
      }
    }
    return result;
  }

  private static String exponentialEntryReplacement(final String inputString) {
    String result = inputString;
    Pattern pattern = Pattern.compile(REGEX);
    Matcher matcher = pattern.matcher(inputString);

    while (matcher.find()) {
      String expValue = checkPlusAtBeginString(matcher.group(0));
      String value = BigDecimal.valueOf(Double.parseDouble(expValue)).toPlainString();
      result = result.replace(expValue, value);
    }
    return result;
  }

  private static String checkPlusAtBeginString(final String str) {
    return str.charAt(0) != '+' ? str : str.substring(1);
  }

  private static String replaceValueX(final String inputString, final double value) {
    return inputString.replace("x", "(" + value + ")");
  }

}
