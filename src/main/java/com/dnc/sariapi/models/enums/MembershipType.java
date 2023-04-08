package com.dnc.sariapi.models.enums;

public enum MembershipType {
    FREE("FREE"),
    BASIC("BASIC"),
    SILVER("SILVER"),
    PLATINUM("PLATINUM");

    private String membershipType;

    MembershipType(final String membershipType) {
        this.membershipType = membershipType;
    }

    public String getMembershipType() {
        return this.membershipType;
    }
}
