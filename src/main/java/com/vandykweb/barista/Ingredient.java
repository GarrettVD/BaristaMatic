package com.vandykweb.barista;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.stream.Collectors;

public enum Ingredient implements ItemInterface<Double> {
    COFFEE(0.75),
    DECAF_COFFEE(0.75),
    SUGAR(0.25),
    CREAM(0.25),
    STEAMED_MILK(0.35),
    FOAMED_MILK(0.35),
    ESPRESSO(1.1),
    COCOA(0.9),
    WHIPPED_CREAM(1);

    private final String name;
    private final double cost;

    Ingredient(double cost) {
        this.name = Ingredient.capitalize(this.name().replace("_", " "));
        this.cost = cost;
    }

    public double getCost() {
        return this.cost;
    }
    public String getName() {
        return this.name;
    }
    public Double getValue() {
        return this.getValue();
    }
    public static String capitalize(String string) {
        return Arrays.stream(string.toLowerCase().split("\\s+"))
                .map(t -> t.substring(0, 1).toUpperCase() + t.substring(1))
                .collect(Collectors.joining(" "));
    }
}
