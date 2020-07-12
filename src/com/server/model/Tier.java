package com.server.model;

public enum Tier {
    TIER_A("A"), TIER_B("B"), TIER_C("C");

    private final String value;

    Tier(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
