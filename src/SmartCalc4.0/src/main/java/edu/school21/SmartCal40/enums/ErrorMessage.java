package edu.school21.SmartCal40.enums;

public enum ErrorMessage {
    ERROR_ARGUMENTS("Error: Not enough arguments"),
    ERROR_DIVISION_ZERO("Error: Division by zero"),
    ERROR_UNDEFINE_RESULT("Error: Undefined result"),
    ERROR_SOMETHING_WRONG("Error: Something wrong"),
    ERROR_WRONG_ARGUMENT("Error: Wrong argument"),
    ERROR_SQRT_WRONG_NUMBER("Error: Sqrt of a negative number");

    private final String name;

    ErrorMessage(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
