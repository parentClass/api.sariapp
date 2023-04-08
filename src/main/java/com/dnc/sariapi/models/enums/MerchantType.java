package com.dnc.sariapi.models.enums;

public enum MerchantType {
    WHOLESALER("WHOLESALER"),
    SARIOWNER("SARIOWNER");

    private String merchantType;

    MerchantType(final String merchantType) {
        this.merchantType = merchantType;
    }

    public String getMerchantType() {
        return this.merchantType;
    }
}
