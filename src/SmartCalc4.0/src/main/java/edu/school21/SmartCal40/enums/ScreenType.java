package edu.school21.SmartCal40.enums;

public enum ScreenType {
    BASIC("Главная", "SmartCalc3"),
    CREDIT("Кредитный калькулятор", "CreditCalc"),
    DEPOSIT("Депозитный калькулятор", "DepositCalc"),
    ABOUT("Справка", "About");

    private final String name;
    private final String title;

    ScreenType(final String name, final String title) {
        this.name = name;
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }
}
