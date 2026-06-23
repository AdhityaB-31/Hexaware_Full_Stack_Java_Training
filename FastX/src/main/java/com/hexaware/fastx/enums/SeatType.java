package com.hexaware.fastx.enums;


public enum SeatType {

    // Sleeper bus seat types
    LOWER_BERTH("LB"),
    UPPER_BERTH("UB"),
    SIDE_LOWER("SL"),
    SIDE_UPPER("SU"),

    // Seater / Semi-sleeper / Volvo seat types
    WINDOW_SEAT("WS"),
    AISLE_SEAT("AS"),
    MIDDLE_SEAT("MS");

    private final String code;

    SeatType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
