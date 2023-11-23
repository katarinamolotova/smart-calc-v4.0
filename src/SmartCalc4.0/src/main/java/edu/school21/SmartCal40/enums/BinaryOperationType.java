package edu.school21.SmartCal40.enums;

import java.util.Arrays;

public enum BinaryOperationType {
    MULTIPLICATION("*"),
    ADDITION("+"),
    DIVISION("/"),
    SUBTRACTION("-"),
    DEGREE("^"),
    REMAINDER_OF_DIVISION("mod");

    private final String operation;

    BinaryOperationType(final String operation) {
        this.operation = operation;
    }

    public static boolean isBinaryOperation(final String operation) {
        return Arrays.stream(BinaryOperationType.values())
                     .anyMatch(t -> t.operation.equalsIgnoreCase(operation));
    }

    public static BinaryOperationType fromString(final String text) {
        return Arrays
                .stream(BinaryOperationType.values())
                .filter(b -> b.operation.equalsIgnoreCase(text))
                .findFirst()
                .orElse(null);
    }

    public String getOperation() {
        return operation;
    }

}
