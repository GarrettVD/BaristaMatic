package com.vandykweb.barista;

public abstract class Item {
    String name;
    double cost = 0.0;

    double getCost() {
        return this.cost;
    }

    String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
