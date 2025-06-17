package com.nashtech.spring_graphql_demo.util;

import com.nashtech.spring_graphql_demo.entities.InventoryItem;
import org.springframework.stereotype.Component;
import reactor.core.publisher.*;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * InventoryPublisher is a utility class that manages the publishing of inventory items.
 * It uses Reactor's Sinks to emit inventory items and notify subscribers.
 */
@Component
public class InventoryPublisher {
    private final Sinks.Many<InventoryItem> processor;
    private final CopyOnWriteArrayList<Sinks.Many<InventoryItem>> subscribers = new CopyOnWriteArrayList<>();

    // Constructor initializes the Sinks.Many processor for unicast emission
    public InventoryPublisher() {
        this.processor = Sinks.many().unicast().onBackpressureBuffer();
    }

    // Adds an inventory item to the processor and notifies all subscribers.
    public void addInventory(InventoryItem item) {
        processor.tryEmitNext(item);
        notifySubscriber(item);
    }

    // Notifies all subscribers about the new inventory item.
    public void notifySubscriber(InventoryItem item) {
        subscribers.forEach(subscriber->subscriber.tryEmitNext(item));
    }

    // Returns a Flux that allows subscribers to receive inventory items.
    public Flux<InventoryItem> getInventoryItems() {
        return processor.asFlux();
    }

    // Adds a new subscriber to the list of subscribers.
    public void addSubscriber(Sinks.Many<InventoryItem> subscriber) {
        subscribers.add(subscriber);
    }

    // Removes a subscriber from the list of subscribers.
    public void removeSubscriber(Sinks.Many<InventoryItem> subscriber) {
        subscribers.remove(subscriber);
    }

    public CopyOnWriteArrayList<Sinks.Many<InventoryItem>> getSubscribers() {
        return subscribers;
    }

}
