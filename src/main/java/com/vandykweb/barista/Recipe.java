package com.vandykweb.barista;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public enum Recipe implements ItemInterface<Map<Ingredient, Integer>> {
    COFFEE(Map.of(Ingredient.COFFEE, 3, Ingredient.SUGAR, 1, Ingredient.CREAM, 1)),
    DECAF_COFFEE(Map.of(Ingredient.DECAF_COFFEE, 3, Ingredient.SUGAR, 1, Ingredient.CREAM, 1)),
    CAFFE_LATTE(Map.of(Ingredient.ESPRESSO, 2, Ingredient.STEAMED_MILK, 1)),
    CAFFE_AMERICANO(Map.of(Ingredient.ESPRESSO, 3)),
    CAFFE_MOCHA(Map.of(Ingredient.ESPRESSO, 1, Ingredient.COCOA, 1, Ingredient.STEAMED_MILK, 1, Ingredient.WHIPPED_CREAM, 1)),
    CAPPUCINO(Map.of(Ingredient.ESPRESSO, 2, Ingredient.STEAMED_MILK, 1, Ingredient.FOAMED_MILK, 1));

    private final String name;
    private final Map<Ingredient, Integer> value;

    Recipe(Map<Ingredient, Integer> value) {
        this.name = Recipe.capitalize(this.name().replace("_", " "));
        this.value = value;
    }

    public double getCost() {
        double total = 0.0f;
        for (Map.Entry<Ingredient, Integer> entry : value.entrySet()) {
            Ingredient ingredient = entry.getKey();
            Integer quantity = entry.getValue();
            total += ingredient.getCost() * quantity;
        }
        return total;
    }

    public Map<Ingredient, Integer> getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    public static TreeMap<Recipe, Map<Ingredient, Integer>> toTreeMap() {
        TreeMap<Recipe, Map<Ingredient, Integer>> map = new TreeMap<>(Comparator.comparing(ItemInterface::getName));
        for (Recipe recipe : Recipe.values()) {
            map.put(recipe, recipe.getValue());
        }
        return map;
    }

    public static String capitalize(String string) {
        return Arrays.stream(string.toLowerCase().split("\\s+"))
                .map(t -> t.substring(0, 1).toUpperCase() + t.substring(1))
                .collect(Collectors.joining(" "));
    }
}
