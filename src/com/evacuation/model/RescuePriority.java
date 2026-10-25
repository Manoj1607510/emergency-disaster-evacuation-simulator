package com.evacuation.model;

public enum RescuePriority {

    CRITICAL(4),
    HIGH(3),
    MEDIUM(2),
    LOW(1);

    private final int level;

    RescuePriority(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}