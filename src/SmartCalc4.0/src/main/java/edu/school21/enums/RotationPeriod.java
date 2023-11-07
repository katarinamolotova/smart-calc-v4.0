package edu.school21.enums;

public enum RotationPeriod {
    MINUTELY("Minutely"),
    HOURLY("Hourly"),
    DAILY("Daily"),
    MONTHLY("Monthly");

    private final String name;

    RotationPeriod(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
