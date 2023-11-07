package edu.school21.enums;

public enum TermType {
    MONTH("Месяцев"),
    YEAR("Лет");

    private final String name;

    TermType(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static TermType getTermType(final String name) {
        return name.equals(TermType.MONTH.getName()) ?
                TermType.MONTH :
                TermType.YEAR;
    }
}
