package com.dnc.sariapi.models.enums;

public enum AccountType {
    CUSTOMER("CUSTOMER"),
    MERCHANT("MERCHANT");

    private String accountType;

    AccountType(final String accountType) {
        this.accountType = accountType;
    }

    public String getAccountType() {
        return this.accountType;
    }
}
