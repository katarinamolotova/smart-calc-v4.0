package edu.school21.SmartCal40.models.helpers;

import java.util.Optional;

import edu.school21.SmartCal40.enums.BinaryOperationType;
import edu.school21.SmartCal40.enums.Status;
import edu.school21.SmartCal40.enums.UnaryOperationType;
import javafx.util.Pair;
import java.util.Objects;
import java.util.Queue;
import java.util.Stack;

import static java.lang.Math.*;

public class Calculator {
  public static double calculate(Queue<Pair<String, Double>> polishNotation) {
    Stack<Double> intermediateResult = new Stack<>();
    while (!polishNotation.isEmpty()) {
      String operation = polishNotation.peek().getKey();
      Pair<String, Double> result = Optional.ofNullable(polishNotation.peek())
          .orElseThrow(() -> new IllegalArgumentException(Status.ERROR_ARGUMENTS.getName()));
      final double value = result.getValue();
      if (Objects.equals(operation, "num")) {
        intermediateResult.push(value);
      } else if (BinaryOperationType.isBinaryOperation(operation)) {
        executionBinaryOperation(intermediateResult, operation);
      } else {
        executionUnaryOperation(intermediateResult, operation);
      }
      polishNotation.poll();
    }
    return intermediateResult.pop();
  }

  private static void executionBinaryOperation(
          Stack<Double> intermediateResult,
          final String operation
  ) {
    double result = 0;
    final double value1 = getValueFromStack(intermediateResult);
    final double value2 = getValueFromStack(intermediateResult);
    if (Objects.equals(operation, BinaryOperationType.MULTIPLICATION.getOperation())) {
      result = value2 * value1;
    } else if (Objects.equals(operation, BinaryOperationType.ADDITION.getOperation())) {
      result = value2 + value1;
    } else if (Objects.equals(operation, BinaryOperationType.SUBTRACTION.getOperation())) {
      result = value2 - value1;
    } else if (Objects.equals(operation, BinaryOperationType.DIVISION.getOperation())) {
      checkDivision(value1);
      result = value2 / value1;
    } else if (Objects.equals(operation, BinaryOperationType.REMAINDER_OF_DIVISION.getOperation())) {
      checkDivision(value1);
      result = value2 % value1;
    } else if (Objects.equals(operation, BinaryOperationType.DEGREE.getOperation())) {
      checkZeroPow(value1, value2);
      result = Math.pow(value2, value1);
    }
    intermediateResult.push(result);
  }

  private static void executionUnaryOperation(Stack<Double> intermediateResult,
      final String operation) {
    double result = 0;
    double value = getValueFromStack(intermediateResult);
    if (Objects.equals(operation, UnaryOperationType.TILDE.getOperation())) {
      result = -1 * value;
    } else if (Objects.equals(operation, UnaryOperationType.PLUS.getOperation())) {
      result = value;
    } else if (Objects.equals(operation, UnaryOperationType.SIN.getOperation())) {
      result = sin(value);
    } else if (Objects.equals(operation, UnaryOperationType.COS.getOperation())) {
      result = cos(value);
    } else if (Objects.equals(operation, UnaryOperationType.TAN.getOperation())) {
      result = tan(value);
    } else if (Objects.equals(operation, UnaryOperationType.SQRT.getOperation())) {
      checkSqrt(value);
      result = sqrt(value);
    } else if (Objects.equals(operation, UnaryOperationType.LN.getOperation())) {
      result = log(value);
    } else if (Objects.equals(operation, UnaryOperationType.LOG.getOperation())) {
      result = log10(value);
    } else if (Objects.equals(operation, UnaryOperationType.ATAN.getOperation())) {
      result = atan(value);
    } else if (Objects.equals(operation, UnaryOperationType.ACOS.getOperation())) {
      result = acos(value);
    } else if (Objects.equals(operation, UnaryOperationType.ASIN.getOperation())) {
      result = asin(value);
    }
    intermediateResult.push(result);
  }

  private static void checkZeroPow(final double degree, final double value) {
    if (degree < 0 && value == 0) {
      throw new IllegalArgumentException(Status.ERROR_UNDEFINE_RESULT.getName());
    }
  }

  private static double getValueFromStack(final Stack<Double> intermediateResult) {
    if (intermediateResult.isEmpty()) {
      throw new IllegalArgumentException(Status.ERROR_ARGUMENTS.getName());
    }
    return intermediateResult.pop();
  }

  private static void checkDivision(final double value) {
    if (value == 0) {
      throw new IllegalArgumentException(Status.ERROR_DIVISION_ZERO.getName());
    }
  }

  private static void checkSqrt(final double value) {
    if (value < 0) {
      throw new IllegalArgumentException(Status.ERROR_SQRT_WRONG_NUMBER.getName());
    }
  }
}
