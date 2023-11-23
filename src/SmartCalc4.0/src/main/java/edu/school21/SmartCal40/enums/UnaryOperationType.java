package edu.school21.SmartCal40.enums;

import java.util.Arrays;

public enum UnaryOperationType {
    SIN("sin"),
    COS("cos"),
    TAN("tan"),
    SQRT("sqrt"),
    LN("ln"),
    LOG("log"),
    ATAN("atan"),
    ACOS("acos"),
    ASIN("asin"),
    TILDE("~"),
    PLUS("plus");

    private final String operation;

    UnaryOperationType(final String operation) {
        this.operation = operation;
    }

    public static boolean isUnaryOperation(final String operation) {
        return Arrays.stream(UnaryOperationType.values())
                     .anyMatch(t -> t.operation.equalsIgnoreCase(operation));
    }

    public static UnaryOperationType fromString(final String text) {
        return Arrays
                .stream(UnaryOperationType.values())
                .filter(b -> b.operation.equalsIgnoreCase(text))
                .findFirst()
                .orElse(null);
    }

    public String getOperation() {
        return operation;
    }

}
