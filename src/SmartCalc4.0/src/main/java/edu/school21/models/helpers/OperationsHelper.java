package edu.school21.models.helpers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
public class OperationsHelper {

  public static boolean isBinaryOperation(final String operation) {
    return (Objects.equals(operation, "*") ||
        Objects.equals(operation, "-") ||
        Objects.equals(operation, "+") ||
        Objects.equals(operation, "mod") ||
        Objects.equals(operation, "^") ||
        Objects.equals(operation, "/"));
  }

  public static boolean isUnaryOperation(final String operation) {
    return (Objects.equals(operation, "sin") ||
        Objects.equals(operation, "cos") ||
        Objects.equals(operation, "tan") ||
        Objects.equals(operation, "sqrt") ||
        Objects.equals(operation, "ln") ||
        Objects.equals(operation, "log") ||
        Objects.equals(operation, "atan") ||
        Objects.equals(operation, "acos") ||
        Objects.equals(operation, "asin") ||
        Objects.equals(operation, "~") ||
        Objects.equals(operation, "plus"));
  }

}
