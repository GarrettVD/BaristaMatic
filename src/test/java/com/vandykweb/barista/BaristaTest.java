package com.vandykweb.barista;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class BaristaTest {
    private ByteArrayOutputStream testOutput;

    @BeforeEach
    void setUp() {
        testOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOutput));
    }

    @AfterEach
    void tearDown() {
        System.setIn(System.in);
        System.setOut(System.out);
    }

    @Test
    void dispensingCaffeLatteThenExit() {
        final String expectedOutput = "Inventory:\n"
                + "Cocoa,10\n"
                + "Coffee,10\n"
                + "Cream,10\n"
                + "Decaf Coffee,10\n"
                + "Espresso,10\n"
                + "Foamed Milk,10\n"
                + "Steamed Milk,10\n"
                + "Sugar,10\n"
                + "Whipped Cream,10\n"
                + "Menu:\n"
                + "1,Caffe Americano,$3.30,true\n"
                + "2,Caffe Latte,$2.55,true\n"
                + "3,Caffe Mocha,$3.35,true\n"
                + "4,Cappucino,$2.90,true\n"
                + "5,Coffee,$2.75,true\n"
                + "6,Decaf Coffee,$2.75,true\n"
                + "Dispensing: Caffe Latte\n"
                + "Inventory:\n"
                + "Cocoa,10\n"
                + "Coffee,10\n"
                + "Cream,10\n"
                + "Decaf Coffee,10\n"
                + "Espresso,8\n"
                + "Foamed Milk,10\n"
                + "Steamed Milk,9\n"
                + "Sugar,10\n"
                + "Whipped Cream,10\n"
                + "Menu:\n"
                + "1,Caffe Americano,$3.30,true\n"
                + "2,Caffe Latte,$2.55,true\n"
                + "3,Caffe Mocha,$3.35,true\n"
                + "4,Cappucino,$2.90,true\n"
                + "5,Coffee,$2.75,true\n"
                + "6,Decaf Coffee,$2.75,true\n";

        assertExpectedOutput("2\nq\n", expectedOutput);
    }

    @Test
    void anInvalidSelectionThenExit() {
        final String expectedOutput = "Inventory:\n"
                + "Cocoa,10\n"
                + "Coffee,10\n"
                + "Cream,10\n"
                + "Decaf Coffee,10\n"
                + "Espresso,10\n"
                + "Foamed Milk,10\n"
                + "Steamed Milk,10\n"
                + "Sugar,10\n"
                + "Whipped Cream,10\n"
                + "Menu:\n"
                + "1,Caffe Americano,$3.30,true\n"
                + "2,Caffe Latte,$2.55,true\n"
                + "3,Caffe Mocha,$3.35,true\n"
                + "4,Cappucino,$2.90,true\n"
                + "5,Coffee,$2.75,true\n"
                + "6,Decaf Coffee,$2.75,true\n"
                + "Invalid selection: x\n"
                + "Inventory:\n"
                + "Cocoa,10\n"
                + "Coffee,10\n"
                + "Cream,10\n"
                + "Decaf Coffee,10\n"
                + "Espresso,10\n"
                + "Foamed Milk,10\n"
                + "Steamed Milk,10\n"
                + "Sugar,10\n"
                + "Whipped Cream,10\n"
                + "Menu:\n"
                + "1,Caffe Americano,$3.30,true\n"
                + "2,Caffe Latte,$2.55,true\n"
                + "3,Caffe Mocha,$3.35,true\n"
                + "4,Cappucino,$2.90,true\n"
                + "5,Coffee,$2.75,true\n"
                + "6,Decaf Coffee,$2.75,true\n";
        assertExpectedOutput("x\nq\n", expectedOutput);
    }

    @Test
    void coffeeIsOutOfStockAfterFourOrders() {
        final String expectedOutput = "Inventory:\n"
                + "Cocoa,10\n"
                + "Coffee,10\n"
                + "Cream,10\n"
                + "Decaf Coffee,10\n"
                + "Espresso,10\n"
                + "Foamed Milk,10\n"
                + "Steamed Milk,10\n"
                + "Sugar,10\n"
                + "Whipped Cream,10\n"
                + "Menu:\n"
                + "1,Caffe Americano,$3.30,true\n"
                + "2,Caffe Latte,$2.55,true\n"
                + "3,Caffe Mocha,$3.35,true\n"
                + "4,Cappucino,$2.90,true\n"
                + "5,Coffee,$2.75,true\n"
                + "6,Decaf Coffee,$2.75,true\n"
                + "Dispensing: Coffee\n"
                + "Inventory:\n"
                + "Cocoa,10\n"
                + "Coffee,7\n"
                + "Cream,9\n"
                + "Decaf Coffee,10\n"
                + "Espresso,10\n"
                + "Foamed Milk,10\n"
                + "Steamed Milk,10\n"
                + "Sugar,9\n"
                + "Whipped Cream,10\n"
                + "Menu:\n"
                + "1,Caffe Americano,$3.30,true\n"
                + "2,Caffe Latte,$2.55,true\n"
                + "3,Caffe Mocha,$3.35,true\n"
                + "4,Cappucino,$2.90,true\n"
                + "5,Coffee,$2.75,true\n"
                + "6,Decaf Coffee,$2.75,true\n"
                + "Dispensing: Coffee\n"
                + "Inventory:\n"
                + "Cocoa,10\n"
                + "Coffee,4\n"
                + "Cream,8\n"
                + "Decaf Coffee,10\n"
                + "Espresso,10\n"
                + "Foamed Milk,10\n"
                + "Steamed Milk,10\n"
                + "Sugar,8\n"
                + "Whipped Cream,10\n"
                + "Menu:\n"
                + "1,Caffe Americano,$3.30,true\n"
                + "2,Caffe Latte,$2.55,true\n"
                + "3,Caffe Mocha,$3.35,true\n"
                + "4,Cappucino,$2.90,true\n"
                + "5,Coffee,$2.75,true\n"
                + "6,Decaf Coffee,$2.75,true\n"
                + "Dispensing: Coffee\n"
                + "Inventory:\n"
                + "Cocoa,10\n"
                + "Coffee,1\n"
                + "Cream,7\n"
                + "Decaf Coffee,10\n"
                + "Espresso,10\n"
                + "Foamed Milk,10\n"
                + "Steamed Milk,10\n"
                + "Sugar,7\n"
                + "Whipped Cream,10\n"
                + "Menu:\n"
                + "1,Caffe Americano,$3.30,true\n"
                + "2,Caffe Latte,$2.55,true\n"
                + "3,Caffe Mocha,$3.35,true\n"
                + "4,Cappucino,$2.90,true\n"
                + "5,Coffee,$2.75,false\n"
                + "6,Decaf Coffee,$2.75,true\n"
                + "Out of stock: Coffee\n"
                + "Inventory:\n"
                + "Cocoa,10\n"
                + "Coffee,1\n"
                + "Cream,7\n"
                + "Decaf Coffee,10\n"
                + "Espresso,10\n"
                + "Foamed Milk,10\n"
                + "Steamed Milk,10\n"
                + "Sugar,7\n"
                + "Whipped Cream,10\n"
                + "Menu:\n"
                + "1,Caffe Americano,$3.30,true\n"
                + "2,Caffe Latte,$2.55,true\n"
                + "3,Caffe Mocha,$3.35,true\n"
                + "4,Cappucino,$2.90,true\n"
                + "5,Coffee,$2.75,false\n"
                + "6,Decaf Coffee,$2.75,true\n";

        assertExpectedOutput("5\n5\n5\n5\n", expectedOutput);
    }

    @Test
    void nullInputStreamThrowsNullPointerException() {
        System.setIn(null);
        assertThrows(NullPointerException.class,
                () -> assertExpectedOutput(null, null), "Expected NullPointerException to throw");
    }

    @Test
    void invalidNumericSelectionResultsInErrorMessage() {
        String expectedOutput = "Inventory:\n"
                + "Cocoa,10\n"
                + "Coffee,10\n"
                + "Cream,10\n"
                + "Decaf Coffee,10\n"
                + "Espresso,10\n"
                + "Foamed Milk,10\n"
                + "Steamed Milk,10\n"
                + "Sugar,10\n"
                + "Whipped Cream,10\n"
                + "Menu:\n"
                + "1,Caffe Americano,$3.30,true\n"
                + "2,Caffe Latte,$2.55,true\n"
                + "3,Caffe Mocha,$3.35,true\n"
                + "4,Cappucino,$2.90,true\n"
                + "5,Coffee,$2.75,true\n"
                + "6,Decaf Coffee,$2.75,true\n"
                + "Invalid selection: 99\n"
                + "Inventory:\n"
                + "Cocoa,10\n"
                + "Coffee,10\n"
                + "Cream,10\n"
                + "Decaf Coffee,10\n"
                + "Espresso,10\n"
                + "Foamed Milk,10\n"
                + "Steamed Milk,10\n"
                + "Sugar,10\n"
                + "Whipped Cream,10\n"
                + "Menu:\n"
                + "1,Caffe Americano,$3.30,true\n"
                + "2,Caffe Latte,$2.55,true\n"
                + "3,Caffe Mocha,$3.35,true\n"
                + "4,Cappucino,$2.90,true\n"
                + "5,Coffee,$2.75,true\n"
                + "6,Decaf Coffee,$2.75,true\n";
        assertExpectedOutput("99\n", expectedOutput);
    }

    private void assertExpectedOutput(String input, String expectedOutput) {
        ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
        System.setIn(testInput);

        Barista.main(new String[0]);

        if (expectedOutput != null) {
            assertEquals(expectedOutput, testOutput.toString());
        }
    }
}
