package edu.school21.models.helpers;

import javafx.util.Pair;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

import static edu.school21.models.helpers.OperationsHelper.isBinaryOperation;
import static edu.school21.models.helpers.OperationsHelper.isUnaryOperation;

@Component
@AllArgsConstructor
public class Parser {

  private final Queue<Pair<String, Double>> polishNotation = new LinkedList<>();
  private boolean isUnary;

  public Queue<Pair<String, Double>> doParsing(String str) {
    Stack<String> operations = new Stack<>();
    for (int i = 0; i < str.length(); i++) {
      String strByPos = str.substring(i);
      if (Character.isDigit(str.charAt(i))) {
        i = numbersProcessing(i, strByPos);
      } else if (str.charAt(i) == '-' && isUnary) {
        operations.add("~");
      } else if (str.charAt(i) == '+' && isUnary) {
        operations.add("plus");
      } else {
        i = operationsProcessing(operations, i, strByPos);
      }
    }
    while (!operations.empty()) {
      this.polishNotation.add(new Pair<>(operations.pop(), 0d));
    }
    return polishNotation;
  }

  int numbersProcessing(int index, final String str) {
    try {
      Number number = NumberFormat.getNumberInstance().parse(str);
      this.polishNotation.add(new Pair<>("num", number.doubleValue()));
      isUnary = false;
    } catch (ParseException e) {
      throw new RuntimeException("Error: Something wrong");
    }
    return index + (shiftNumber(str) - 1);
  }


  int operationsProcessing(Stack<String> operations, int index, String str) {
    String op = getOperations(str);
    if (Objects.equals(op, " ")) {
      char temp = str.charAt(0);
      op = Character.toString(temp);
    }
    if (Objects.equals(op, ")")) {
      getValueInBrackets(operations);
      isUnary = false;
    } else if (isBinaryOperation(op) || isUnaryOperation(op) || Objects.equals(op, "(")) {
      boolean flagPrevPow = true;

      if (Objects.equals(op, "^") && !operations.isEmpty()) {
        flagPrevPow = !(Objects.equals(operations.peek(), "^"));
      }

      if (!Objects.equals(op, "(") && flagPrevPow) {
        prioritization(operations, op);
      }

      operations.push(op);
      if (isUnaryOperation(op)) {
        operations.add("(");
      }
      isUnary = true;
    } else if (!Objects.equals(op, " ")) {
      throw new IllegalArgumentException("Error: Wrong argument");
    }
    return index + shiftOperation(op);
  }

  int shiftOperation(final String op) {
    int result = 0;
    if (Objects.equals(op, "ln") || Objects.equals(op, "mod")) {
      result = 2;
    } else if (Objects.equals(op, "sin") || Objects.equals(op, "cos") || Objects.equals(op, "log")
        || Objects.equals(op, "tan")
    ) {
      result = 3;
    } else if (Objects.equals(op, "sqrt") || Objects.equals(op, "atan") || Objects.equals(op,
        "acos") || Objects.equals(op, "asin")) {
      result = 4;
    }
    return result;
  }

  int shiftNumber(String value) {
    boolean isDot = false;
    int result = 0;
    while (Character.isDigit(value.charAt(result)) || value.charAt(result) == '.' && !isDot) {
      if (value.charAt(result) == '.') {
        isDot = true;
      }
      result++;
      if (result == value.length()) {
        break;
      }
    }
    return result;
  }

  void prioritization(Stack<String> operations, final String op) {
    while (!operations.empty() && priority(operations.peek()) >= priority(op)) {
      this.polishNotation.add(new Pair<>(operations.pop(), 0d));
    }
  }

  int priority(final String op) {
    int result = -1;
    if (Objects.equals(op, "+") ||
        Objects.equals(op, "-") ||
        Objects.equals(op, "~") ||
        Objects.equals(op, "plus")) {
      result = 1;
    } else if (Objects.equals(op, "*") ||
        Objects.equals(op, "/") ||
        Objects.equals(op, "mod")) {
      result = 2;
    } else if (Objects.equals(op, "^")) {
      result = 3;
    } else if (Objects.equals(op, "sin") ||
        Objects.equals(op, "cos") ||
        Objects.equals(op, "tan") ||
        Objects.equals(op, "asin") ||
        Objects.equals(op, "acos") ||
        Objects.equals(op, "atan") ||
        Objects.equals(op, "log") ||
        Objects.equals(op, "ln") ||
        Objects.equals(op, "sqrt")) {
      result = 4;
    }
    return result;
  }

  String getOperations(final String str) {
    String op = " ";
    if (str.indexOf("sin(") == 0) {
      op = "sin";
    } else if (str.indexOf("cos(") == 0) {
      op = "cos";
    } else if (str.indexOf("tan(") == 0) {
      op = "tan";
    } else if (str.indexOf("acos(") == 0) {
      op = "acos";
    } else if (str.indexOf("asin(") == 0) {
      op = "asin";
    } else if (str.indexOf("atan(") == 0) {
      op = "atan";
    } else if (str.indexOf("sqrt(") == 0) {
      op = "sqrt";
    } else if (str.indexOf("ln(") == 0) {
      op = "ln";
    } else if (str.indexOf("log(") == 0) {
      op = "log";
    } else if (str.indexOf("mod") == 0) {
      op = "mod";
    }
    return op;
  }

  void getValueInBrackets(Stack<String> operations) {
    while (!operations.empty() && !Objects.equals(operations.peek(), "(")) {
      this.polishNotation.add(new Pair<>(operations.pop(), 0d));
    }
    operations.pop();
  }

}