package edu.school21.enums;

public enum ScreenType {
    BASIC("/forms/basic.fxml", "SmartCalc3"),
    CREDIT("/forms/credit.fxml", "CreditCalc"),
    DEPOSIT("/forms/deposit.fxml", "DepositCalc"),
    SETTINGS("/forms/settings.fxml", "Settings"),
    ABOUT("/forms/about.fxml", "About");

    private final String fileName;
    private final String title;

    ScreenType(final String fileName, final String title) {
        this.fileName = fileName;
        this.title = title;
    }

    public String getFileName() {
        return fileName;
    }

    public String getTitle() {
        return title;
    }
}
