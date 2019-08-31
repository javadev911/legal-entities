package com.trade.legalentities.enums;

public enum Country {
    US, UK, CH;

    public static boolean isAllowed(String value) {
        for (Country country : Country.values()) {
            if (country.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}