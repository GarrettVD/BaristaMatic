package com.vandykweb.barista;


interface ItemInterface<T> {
    double getCost();
    String getName();
    T getValue();
}
