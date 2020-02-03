package com.vandykweb.barista;

import java.util.Map;

public class Recipe extends Item {
    private final Map<Ingredient, Integer> ingredientQuantities;

    public Recipe(String name, Map<Ingredient, Integer> ingredientQuantities) {
        this.name = name;
        this.ingredientQuantities = ingredientQuantities;
        this.cost = ingredientQuantities.entrySet()
                .stream()
                .mapToDouble(entry -> entry.getKey().getCost() * entry.getValue()).sum();
    }

    public Map<Ingredient, Integer> getIngredientQuantities() {
        return ingredientQuantities;
    }

    @Override
    public String toString() {
        return String.format("%s,$%.2f", name, cost);
    }
}
