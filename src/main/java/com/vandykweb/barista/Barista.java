package com.vandykweb.barista;

import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Barista {
    private final Map<String, Ingredient> ingredients;
    private final TreeSet<Recipe> recipes;
    private final TreeMap<Ingredient, Integer> ingredientInventory;

    public Barista() {
        this.ingredients = List.of(
                new Ingredient("Coffee", 0.75),
                new Ingredient("Decaf Coffee", 0.75),
                new Ingredient("Sugar", 0.25),
                new Ingredient("Cream", 0.25),
                new Ingredient("Steamed Milk", 0.35),
                new Ingredient("Foamed Milk", 0.35),
                new Ingredient("Espresso", 1.1),
                new Ingredient("Cocoa", 0.9),
                new Ingredient("Whipped Cream", 1.0))
                .stream()
                .collect(Collectors.toMap(Item::getName, Function.identity()));

        this.ingredientInventory = new TreeMap<>(Comparator.comparing((Ingredient::getName)));
        for (Ingredient v : this.ingredients.values()) {
            this.ingredientInventory.put(v, 10);
        }

        this.recipes = new TreeSet<>(Comparator.comparing((Recipe::getName))) {{
            add(new Recipe("Coffee",
                    Map.of(ingredients.get("Coffee"), 3, ingredients.get("Sugar"), 1, ingredients.get("Cream"), 1)));
            add(new Recipe("Decaf Coffee",
                    Map.of(ingredients.get("Decaf Coffee"), 3, ingredients.get("Sugar"), 1, ingredients.get("Cream"),
                            1)));
            add(new Recipe("Caffe Latte", Map.of(ingredients.get("Espresso"), 2, ingredients.get("Steamed Milk"), 1)));
            add(new Recipe("Caffe Americano", Map.of(ingredients.get("Espresso"), 3)));
            add(new Recipe("Caffe Mocha",
                    Map.of(ingredients.get("Espresso"), 1, ingredients.get("Cocoa"), 1, ingredients.get("Steamed Milk"),
                            1, ingredients.get("Whipped Cream"), 1)));
            add(new Recipe("Cappucino", Map.of(ingredients.get("Espresso"), 2, ingredients.get("Steamed Milk"), 1,
                    ingredients.get("Foamed Milk"), 1)));
        }};
    }

    public void dispense(Recipe recipe) {
        if (!hasEnoughIngredientsForRecipe(recipe)) {
            System.out.println("Out of stock: " + recipe.getName());
            return;
        }
        for (Map.Entry<Ingredient, Integer> entry : recipe.getIngredientQuantities().entrySet()) {
            Ingredient ingredient = entry.getKey();
            Integer quantityRequired = entry.getValue();
            Integer quantityRemaining = ingredientInventory.get(ingredient);
            ingredientInventory.put(ingredient, quantityRemaining - quantityRequired);
        }
        System.out.println("Dispensing: " + recipe.getName());
    }

    public void printMenu() {
        System.out.println("Menu:");
        int menuNumber = 1;
        for (Recipe recipe : recipes) {
            System.out.printf("%d,%s,%b\n", menuNumber, recipe.toString(), hasEnoughIngredientsForRecipe(recipe));
            menuNumber++;
        }
    }

    public void printInventory() {
        System.out.println("Inventory:");
        for (Map.Entry<Ingredient, Integer> entry : ingredientInventory.entrySet()) {
            Ingredient ingredient = entry.getKey();
            Integer quantity = entry.getValue();
            System.out.printf("%s,%d\n", ingredient.getName(), quantity);
        }
    }

    public void restockIngredients() {
        for (Map.Entry<Ingredient, Integer> entry : ingredientInventory.entrySet()) {
            entry.setValue(10);
        }
    }

    private boolean hasEnoughIngredientsForRecipe(Recipe recipe) {
        for (Map.Entry<Ingredient, Integer> entry : recipe.getIngredientQuantities().entrySet()) {
            Ingredient ingredient = entry.getKey();
            Integer quantityRequired = entry.getValue();
            Integer quantityRemaining = ingredientInventory.get(ingredient);
            if (quantityRemaining - quantityRequired < 0) {
                return false;
            }
        }
        return true;
    }

    public void run(InputStream inputStream) {

        printInventory();
        printMenu();

        boolean skip = false;
        try (Scanner inputScanner = new Scanner(inputStream)) {
            while (inputScanner.hasNext()) {
                String command = inputScanner.nextLine().toLowerCase();
                if (command.matches("^\\s*$")) {
                    continue;
                }
                switch (command) {
                    case "r":
                        restockIngredients();
                        break;

                    case "q":
                        inputScanner.close();
                        skip = true;
                        break;

                    default:
                        if (command.matches("^\\s$")) {
                            break;
                        }
                        else if (command.matches("^\\d+$")) {
                            try {
                                Recipe recipe = (Recipe) this.recipes.toArray()[Integer.parseInt(command) - 1];
                                dispense(recipe);
                                break;
                            }
                            catch (IndexOutOfBoundsException error) {
                                // NOP - If the index is OOB, then it's an invalid selection
                            }
                        }
                        System.out.println("Invalid selection: " + command);
                        break;
                }
                if (!skip) {
                    printInventory();
                    printMenu();
                }
            }
        }
        catch (IllegalStateException e) {
            // NOP - since this is thrown by Scanner#hasNextLine() is called following a Scanner#close()
        }
    }

    public static void main(String[] args) {
        final Barista barista = new Barista();
        barista.run(System.in);
    }
}
