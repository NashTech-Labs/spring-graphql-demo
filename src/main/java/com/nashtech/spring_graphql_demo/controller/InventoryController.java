package com.nashtech.spring_graphql_demo.controller;

import com.nashtech.spring_graphql_demo.entities.InventoryItem;
import com.nashtech.spring_graphql_demo.service.InventoryService;
import com.nashtech.spring_graphql_demo.util.InventoryPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * InventoryController handles GraphQL queries, mutations, and subscriptions related to inventory items.
 * It provides methods to retrieve, add, and subscribe to inventory items.
 */
@Controller
public class InventoryController {

    @Autowired
    private InventoryPublisher inventoryPublisher;

    @Autowired
    private InventoryService service;

    // Query to get all inventory items
    @QueryMapping
    public List<InventoryItem> getInventoryItems() {
        return service.getAllItems();
    }

    // Query to get a specific inventory item by ID
    @QueryMapping
    public InventoryItem getInventoryItemById(@Argument String id) {
        return service.getItemById(id);
    }

    // Mutation to add a new inventory item and publish it to subscribers, so that they can receive updates
    @MutationMapping
    public InventoryItem addInventoryItem(@Argument String id,
                                          @Argument String name,
                                          @Argument String category,
                                          @Argument String supplier,
                                          @Argument double costPrice,
                                          @Argument double sellingPrice,
                                          @Argument int quantity) {
        InventoryItem item = new InventoryItem(id, name, category, supplier, costPrice, sellingPrice, quantity);
        service.saveItem(item);
        inventoryPublisher.addInventory(item);
        return item;
    }

    // Subscription to receive real-time updates of inventory items
    @SubscriptionMapping
    public Flux<InventoryItem> inventoryItem(){
        return inventoryPublisher.getInventoryItems();
    }

}
