package com.nashtech.spring_graphql_demo.util;

import com.nashtech.spring_graphql_demo.entities.InventoryItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.test.StepVerifier;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        Flux<InventoryItem> inventoryFlux = inventoryPublisher.getInventoryItems();

        StepVerifier.create(inventoryFlux)
                .expectNext(item)
                .thenCancel() // Cancel the subscription to stop the test so that it doesn't block indefinitely
                .verify();
    }

    @Test
    void testNotifySubscriber() {
        InventoryItem item = new InventoryItem("1", "Item1", "Category1", "Supplier1", 10.0, 100.0, 50);

        Sinks.Many<InventoryItem> sink = Sinks.many().unicast().onBackpressureBuffer();
        inventoryPublisher.addSubscriber(sink);
        inventoryPublisher.addInventory(item);

        StepVerifier.create(sink.asFlux())
                .expectNext(item)
                .thenCancel() // Cancel the subscription to stop the test so that it doesn't block indefinitely
                .verify();
    }

    @Test
    void testAddAndRemoveSubscriber() {
        InventoryItem item = new InventoryItem("1", "Item1", "Category1", "Supplier1", 10.0, 100.0, 50);

        Sinks.Many<InventoryItem> subscriber = Sinks.many().unicast().onBackpressureBuffer();
        inventoryPublisher.addSubscriber(subscriber);

        inventoryPublisher.addInventory(item);
        inventoryPublisher.removeSubscriber(subscriber);

        assertTrue(inventoryPublisher.getSubscribers().isEmpty());
    }
}