package com.nashtech.spring_graphql_demo.util;

import com.nashtech.spring_graphql_demo.entities.InventoryItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

/**
 * InventoryPublisherTest is a test class for InventoryPublisher.
 * It uses JUnit 5 and Reactor's StepVerifier to test the functionality of InventoryPublisher.
 */
class InventoryPublisherTest {

    private InventoryPublisher inventoryPublisher;

    @BeforeEach
    void setUp() {
        inventoryPublisher = new InventoryPublisher();
    }

    @Test
    void testAddInventory() {
        InventoryItem item = new InventoryItem("1", "Item1", "Category1", "Supplier1", 10.0, 100.0, 50);

        inventoryPublisher.addInventory(item);

        StepVerifier.create(inventoryPublisher.getInventoryItems())
                .expectNext(item)
                .thenCancel() // Cancel the subscription to stop the test so that it doesn't block indefinitely
                .verify();
    }

    @Test
    void testMultipleInventoryItems() {
        InventoryItem item1 = new InventoryItem("1", "Item1", "Category1", "Supplier1", 10.0, 100.0, 50);
        InventoryItem item2 = new InventoryItem("2", "Item2", "Category2", "Supplier2", 20.0, 200.0, 30);

        inventoryPublisher.addInventory(item1);
        inventoryPublisher.addInventory(item2);

        StepVerifier.create(inventoryPublisher.getInventoryItems())
                .expectNext(item1, item2)
                .thenCancel()
                .verify();
    }
}