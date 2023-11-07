package edu.school21.enums;

public enum CreditType {
    ANNUITY("Аннуитетный"),
    DIFFERENTIATED("Дифференцированный");

    private final String name;

    CreditType(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static CreditType getCreditType(final String name) {
        return name.equals(CreditType.ANNUITY.getName()) ?
                CreditType.ANNUITY :
                CreditType.DIFFERENTIATED;
    }
}
