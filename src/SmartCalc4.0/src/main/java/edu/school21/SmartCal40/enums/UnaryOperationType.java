package edu.school21.SmartCal40.enums;

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
        for (UnaryOperationType t : UnaryOperationType.values()) {
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
