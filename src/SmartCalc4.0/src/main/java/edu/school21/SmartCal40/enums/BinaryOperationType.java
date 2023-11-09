package edu.school21.SmartCal40.enums;

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
        for (BinaryOperationType t : BinaryOperationType.values()) {
            if (t.operation.equalsIgnoreCase(operation)) {
                return true;
            }
        }
        return false;
    }

    public String getOperation() {
        return operation;
    }

}
