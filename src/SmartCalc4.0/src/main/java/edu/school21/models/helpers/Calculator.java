package edu.school21.models.helpers;

import java.util.Optional;
import javafx.util.Pair;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Queue;
import java.util.Stack;

import static edu.school21.models.helpers.OperationsHelper.isBinaryOperation;
import static java.lang.Math.*;

@Component
@AllArgsConstructor
public class Calculator {

  public static double calculate(Queue<Pair<String, Double>> polishNotation) {
    Stack<Double> intermediateResult = new Stack<>();
    while (!polishNotation.isEmpty()) {
      String operation = polishNotation.peek().getKey();
      Pair<String, Double> result = Optional.ofNullable(polishNotation.peek())
          .orElseThrow(() -> new IllegalArgumentException("Error: Not enough arguments"));
      final double value = result.getValue();
      if (Objects.equals(operation, "num")) {
        intermediateResult.push(value);
      } else if (isBinaryOperation(operation)) {
        executionBinaryOperation(intermediateResult, operation);
      } else {
        executionUnaryOperation(intermediateResult, operation);
      }
      polishNotation.poll();
    }
    return intermediateResult.pop();
  }

  private static void executionBinaryOperation(Stack<Double> intermediateResult,
      final String operation) {
    double result = 0;
    final double value1 = getValueFromStack(intermediateResult);
    final double value2 = getValueFromStack(intermediateResult);
    if (Objects.equals(operation, "*")) {
      result = value2 * value1;
    } else if (Objects.equals(operation, "+")) {
      result = value2 + value1;
    } else if (Objects.equals(operation, "-")) {
      result = value2 - value1;
    } else if (Objects.equals(operation, "/")) {
      checkDivision(value1);
      result = value2 / value1;
    } else if (Objects.equals(operation, "mod")) {
      checkDivision(value1);
      result = value2 % value1;
    } else if (Objects.equals(operation, "^")) {
      checkZeroPow(value1, value2);
      result = Math.pow(value2, value1);
    }
    intermediateResult.push(result);
  }

  private static void executionUnaryOperation(Stack<Double> intermediateResult,
      final String operation) {
    double result = 0;
    double value = getValueFromStack(intermediateResult);
    if (Objects.equals(operation, "~")) {
      result = -1 * value;
    } else if (Objects.equals(operation, "plus")) {
      result = value;
    } else if (Objects.equals(operation, "sin")) {
      result = sin(value);
    } else if (Objects.equals(operation, "cos")) {
      result = cos(value);
    } else if (Objects.equals(operation, "tan")) {
      result = tan(value);
    } else if (Objects.equals(operation, "sqrt")) {
      checkSqrt(value);
      result = sqrt(value);
    } else if (Objects.equals(operation, "ln")) {
      result = log(value);
    } else if (Objects.equals(operation, "log")) {
      result = log10(value);
    } else if (Objects.equals(operation, "atan")) {
      result = atan(value);
    } else if (Objects.equals(operation, "acos")) {
      result = acos(value);
    } else if (Objects.equals(operation, "asin")) {
      result = asin(value);
    }
    intermediateResult.push(result);
  }

  private static void checkZeroPow(final double degree, final double value) {
    if (degree < 0 && value == 0) {
      throw new IllegalArgumentException("Error: Undefined result");
    }
  }

  private static double getValueFromStack(final Stack<Double> intermediateResult) {
    if (intermediateResult.isEmpty()) {
      throw new IllegalArgumentException("Error: Not enough arguments");
    }
    return intermediateResult.pop();
  }

  private static void checkDivision(final double value) {
    if (value == 0) {
      throw new IllegalArgumentException("Error: Division by zero");
    }
  }

  private static void checkSqrt(final double value) {
    if (value < 0) {
      throw new IllegalArgumentException("Error: Sqrt of a negative number");
    }
  }
}
