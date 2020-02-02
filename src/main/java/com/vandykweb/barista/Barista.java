package com.vandykweb.barista;

import java.io.InputStream;
import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;


public class Barista {
    private final TreeMap<Ingredient, Integer> ingredientInventory;
    private final TreeMap<Recipe, Map<Ingredient, Integer>> recipes;

    public Barista() {
        this.ingredientInventory = new TreeMap<>(Comparator.comparing(ItemInterface::getName));
        this.recipes = Recipe.toTreeMap();
        restockIngredients();
    }

    public void dispense(Recipe recipe) {
        if (!hasEnoughIngredientsForRecipe(recipe)) {
            System.out.println("Out of stock: " + recipe.getName());
            return;
        }
        for (Map.Entry<Ingredient, Integer> entry : recipe.getValue().entrySet()) {
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
        for (Map.Entry<Recipe, Map<Ingredient, Integer>> entry : recipes.entrySet()) {
            Recipe recipe = entry.getKey();
            System.out.printf("%d,%s,$%.2f,%b\n", menuNumber, recipe.getName(), recipe.getCost(),
                    hasEnoughIngredientsForRecipe(recipe));
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
        for (Ingredient ingredient : Ingredient.values()) {
            ingredientInventory.put(ingredient, 10);
        }
    }

    public void run(InputStream inputStream) {
        printInventory();
        printMenu();

        try (Scanner inputScanner = new Scanner(inputStream)) {
            while (inputScanner.hasNextLine()) {
                String command = inputScanner.nextLine().toLowerCase();
                switch (command) {
                    case "r":
                        restockIngredients();
                        break;

                    case "q":
                        // Could also just call System.exit(0), but I've opted to terminate by letting the main()
                        // method complete it's execution gracefully
                        inputScanner.close();
                        break;

                    default:
                        if (command.matches("^\\d+$")) {
                            int selection = Integer.parseInt(command) - 1;
                            try {
                                Recipe recipe = (Recipe) Recipe.toTreeMap().keySet().toArray()[selection];
                                dispense(recipe);
                                break;
                            }
                            catch (IndexOutOfBoundsException error) {
                                // NOP - If the index is OOB, then it's an invalid selection
                                System.out.println("Invalid selection: " + command);
                            }
                            break;
                        }
                        System.out.println("Invalid selection: " + command);
                        break;
                }
                printInventory();
                printMenu();
            }
        }
        catch (IllegalStateException e) {
            // NOP - since this is thrown by Scanner#hasNextLine() is called following a Scanner#close()
        }
    }

    private boolean hasEnoughIngredientsForRecipe(Recipe recipe) {
        for (Map.Entry<Ingredient, Integer> entry : recipe.getValue().entrySet()) {
            Ingredient ingredient = entry.getKey();
            Integer quantityRequired = entry.getValue();
            Integer quantityRemaining = ingredientInventory.get(ingredient);
            if (quantityRemaining - quantityRequired < 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        final Barista barista = new Barista();
        barista.run(System.in);
    }
}
