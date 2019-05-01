package com.strikalov.weatherapp.model.entities;

/**
 * Константы для обозначения направления ветра с описанием
 */
public enum WindDirection {

    NORTH("NORTH"),
    NORTH_WEST("NORTH_WEST"),
    NORTH_EAST("NORTH_EAST"),
    WEST("WEST"),
    EAST("EAST"),
    SOUTH("SOUTH"),
    SOUTH_WEST("SOUTH_WEST"),
    SOUTH_EAST("SOUTH_EAST"),
    NO_DIRECTION("NO_DIRECTION");

    private String description;

    WindDirection(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "WindDirection{" +
                "description='" + description + '\'' +
                "}\n";
    }
}
