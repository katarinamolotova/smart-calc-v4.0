package edu.school21.models.helpers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class Validator {

  public static void validateData(final String rawData) {
    lengthSizeCheck(rawData);
    bracketCountCheck(rawData);
    emptyPlaceInBracketsCheck(rawData);
  }

  private static void lengthSizeCheck(final String rawData) {
    if (rawData.length() >= 255) {
      throw new IllegalArgumentException("Error: Wrong answer size");
    }
  }

  private static void bracketCountCheck(final String rawData) {
    if (rawData.chars().asDoubleStream().filter(s -> s == '(').count()
        != rawData.chars().asDoubleStream().filter(s -> s == ')').count()) {
      throw new IllegalArgumentException("Error: Wrong amount of branches");
    }
  }

  private static void emptyPlaceInBracketsCheck(final String rawData) {
    try {
      for (int i = 0; i < rawData.length(); i++) {
        if (rawData.charAt(i) == '(' && rawData.charAt(i + 1) == ')') {
          throw new IllegalArgumentException("Error: Empty branch");
        }
      }
    } catch (final IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Error: Wrong place of branches");
    }
  }
}
