package edu.school21.SmartCal40.enums;

public enum PeriodType {
    NONE("Нет", 0),
    ONCE("Единовременно", 0),
    MONTHLY("Ежемесячно", 1),
    QUARTERLY("Ежеквартально", 3),
    ANNUAL("Ежегодно", 12);

    private final String name;
    private final Integer countOfMonths;

    PeriodType(final String name, final Integer countOfMonths) {
        this.name = name;
        this.countOfMonths = countOfMonths;
    }

    public String getName() {
        return name;
    }

    public Integer getCountOfMonths() {
        return countOfMonths;
    }

    public static PeriodType getPeriodType(final String name) {
        if (name.equals(PeriodType.NONE.name)) {
            return PeriodType.NONE;
        } else if (name.equals(PeriodType.ONCE.name)) {
            return PeriodType.ONCE;
        } else if (name.equals(PeriodType.MONTHLY.name)) {
            return PeriodType.MONTHLY;
        } else if (name.equals(PeriodType.QUARTERLY.name)) {
            return PeriodType.QUARTERLY;
        } else if (name.equals(PeriodType.ANNUAL.name)) {
            return PeriodType.ANNUAL;
        } else {
            return PeriodType.NONE;
        }
    }

}
