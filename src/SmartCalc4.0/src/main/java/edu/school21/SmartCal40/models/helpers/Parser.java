package edu.school21.SmartCal40.models.helpers;

import edu.school21.SmartCal40.enums.BinaryOperationType;
import edu.school21.SmartCal40.enums.Status;
import edu.school21.SmartCal40.enums.UnaryOperationType;
import javafx.util.Pair;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Stack;

@Component
@AllArgsConstructor
public class Parser {

  private final Queue<Pair<String, Double>> polishNotation = new LinkedList<>();
  private boolean isUnary;

  public Parser() {
    this.isUnary = true;
  }

  public Queue<Pair<String, Double>> doParsing(String str) {
    polishNotation.clear();
    this.isUnary = true;
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
      throw new RuntimeException(Status.ERROR_SOMETHING_WRONG.getName());
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
    } else if (BinaryOperationType.isBinaryOperation(op)
               || UnaryOperationType.isUnaryOperation(op)
               || Objects.equals(op, "(")) {

      boolean flagPrevPow = true;

      if (Objects.equals(op, "^") && !operations.isEmpty()) {
        flagPrevPow = !(Objects.equals(operations.peek(), "^"));
      }

      if (!Objects.equals(op, "(") && flagPrevPow) {
        prioritization(operations, op);
      }

      operations.push(op);
      if (UnaryOperationType.isUnaryOperation(op)) {
        operations.add("(");
      }
      isUnary = true;
    } else if (!Objects.equals(op, " ")) {
      throw new IllegalArgumentException(Status.ERROR_WRONG_ARGUMENT.getName());
    }
    return index + shiftOperation(op);
  }

  int shiftOperation(final String op) {
    int result = 0;
    if (Objects.equals(op, UnaryOperationType.LN.getOperation()) ||
        Objects.equals(op, BinaryOperationType.REMAINDER_OF_DIVISION.getOperation())) {
      result = 2;
    } else if (Objects.equals(op, UnaryOperationType.SIN.getOperation()) ||
               Objects.equals(op, UnaryOperationType.COS.getOperation()) ||
               Objects.equals(op, UnaryOperationType.LOG.getOperation()) ||
               Objects.equals(op, UnaryOperationType.TAN.getOperation())
    ) {
      result = 3;
    } else if (Objects.equals(op, UnaryOperationType.SQRT.getOperation()) ||
               Objects.equals(op, UnaryOperationType.ATAN.getOperation()) ||
               Objects.equals(op, UnaryOperationType.ACOS.getOperation()) ||
               Objects.equals(op, UnaryOperationType.ASIN.getOperation())
    ) {
      result = 4;
    }
    return result;
  }

  int shiftNumber(String value) {
    boolean isDot = false;
    int result = 0;
    while (Character.isDigit(value.charAt(result)) ||
           value.charAt(result) == '.' && !isDot) {
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
    if (Objects.equals(BinaryOperationType.fromString(op), BinaryOperationType.ADDITION) ||
        Objects.equals(BinaryOperationType.fromString(op), BinaryOperationType.SUBTRACTION) ||
        Objects.equals(UnaryOperationType.fromString(op), UnaryOperationType.TILDE) ||
        Objects.equals(UnaryOperationType.fromString(op), UnaryOperationType.PLUS)
    ) {
      result = 1;
    } else if (Objects.equals(BinaryOperationType.fromString(op), BinaryOperationType.MULTIPLICATION) ||
               Objects.equals(BinaryOperationType.fromString(op), BinaryOperationType.DIVISION) ||
               Objects.equals(UnaryOperationType.fromString(op), BinaryOperationType.REMAINDER_OF_DIVISION)
    ) {
      result = 2;
    } else if (Objects.equals(BinaryOperationType.fromString(op), BinaryOperationType.DEGREE)) {
      result = 3;
    } else if (Objects.equals(UnaryOperationType.fromString(op), UnaryOperationType.SIN) ||
        Objects.equals(UnaryOperationType.fromString(op), UnaryOperationType.COS) ||
        Objects.equals(UnaryOperationType.fromString(op), UnaryOperationType.TAN) ||
        Objects.equals(UnaryOperationType.fromString(op), UnaryOperationType.ATAN) ||
        Objects.equals(UnaryOperationType.fromString(op), UnaryOperationType.ASIN) ||
        Objects.equals(UnaryOperationType.fromString(op), UnaryOperationType.ACOS) ||
        Objects.equals(UnaryOperationType.fromString(op), UnaryOperationType.LOG) ||
        Objects.equals(UnaryOperationType.fromString(op), UnaryOperationType.LN) ||
        Objects.equals(UnaryOperationType.fromString(op), UnaryOperationType.SQRT)
    ) {
      result = 4;
    }
    return result;
  }

  String getOperations(final String str) {
    String op = " ";
    if (str.indexOf("sin(") == 0) {
      op = UnaryOperationType.SIN.getOperation();
    } else if (str.indexOf("cos(") == 0) {
      op = UnaryOperationType.COS.getOperation();
    } else if (str.indexOf("tan(") == 0) {
      op = UnaryOperationType.TAN.getOperation();
    } else if (str.indexOf("acos(") == 0) {
      op = UnaryOperationType.ACOS.getOperation();
    } else if (str.indexOf("asin(") == 0) {
      op = UnaryOperationType.ASIN.getOperation();
    } else if (str.indexOf("atan(") == 0) {
      op = UnaryOperationType.ATAN.getOperation();
    } else if (str.indexOf("sqrt(") == 0) {
      op = UnaryOperationType.SQRT.getOperation();
    } else if (str.indexOf("ln(") == 0) {
      op = UnaryOperationType.LN.getOperation();
    } else if (str.indexOf("log(") == 0) {
      op = UnaryOperationType.LOG.getOperation();
    } else if (str.indexOf("mod") == 0) {
      op = BinaryOperationType.REMAINDER_OF_DIVISION.getOperation();
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