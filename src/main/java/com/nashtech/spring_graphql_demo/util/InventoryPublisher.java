package com.nashtech.spring_graphql_demo.util;

import com.nashtech.spring_graphql_demo.entities.InventoryItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

/**
 * InventoryPublisher is a utility class that manages the publishing of inventory items.
 * It uses Reactor's Sinks to emit inventory items to multiple subscribers.
 */
@Component
public class InventoryPublisher {
    private static final Logger logger = LoggerFactory.getLogger(InventoryPublisher.class);
    private final Sinks.Many<InventoryItem> sink;

    // Constructor initializes the Sinks.Many processor for multicast emission
    public InventoryPublisher() {
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
        logger.info("InventoryPublisher initialized with a multicast sink.");
    }

    // Adds an inventory item to the sink.
    public void addInventory(InventoryItem item) {
        logger.info("Adding inventory item: {}", item);
        Sinks.EmitResult result = sink.tryEmitNext(item);
        if (result.isSuccess()) {
            logger.info("Successfully emitted inventory item: {}", item);
        } else {
            logger.error("Failed to emit inventory item: {}. EmitResult: {}", item, result);
        }
    }

    // Returns a Flux that allows subscribers to receive inventory items.
    public Flux<InventoryItem> getInventoryItems() {
        logger.info("Returning Flux for inventory items.");
        return sink.asFlux();
    }
}