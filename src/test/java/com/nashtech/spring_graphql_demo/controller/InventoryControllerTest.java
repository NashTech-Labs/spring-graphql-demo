package com.nashtech.spring_graphql_demo.controller;

import com.nashtech.spring_graphql_demo.entities.InventoryItem;
import com.nashtech.spring_graphql_demo.service.InventoryService;
import com.nashtech.spring_graphql_demo.util.InventoryPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * InventoryControllerTest is a test class for InventoryController.
 * It uses Mockito to mock dependencies and verify the behavior of the controller methods.
 */
class InventoryControllerTest {

    @Mock
    private InventoryService inventoryService;

    @Mock
    private InventoryPublisher inventoryPublisher;

    @InjectMocks
    private InventoryController inventoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetInventoryItems() {
        List<InventoryItem> mockItems = Arrays.asList(
                new InventoryItem("1", "Item1", "Category1", "Supplier1", 10.0, 100.0, 50),
                new InventoryItem("2", "Item2", "Category2", "Supplier2", 20.0, 200.0, 30)
        );
        when(inventoryService.getAllItems()).thenReturn(mockItems);

        List<InventoryItem> result = inventoryController.getInventoryItems();

        assertEquals(mockItems, result);
        verify(inventoryService, times(1)).getAllItems();
    }

    @Test
    void testGetInventoryItemById() {
        InventoryItem mockItem = new InventoryItem("1", "Item1", "Category1", "Supplier1", 10.0, 100.0, 50);
        when(inventoryService.getItemById("1")).thenReturn(mockItem);

        InventoryItem result = inventoryController.getInventoryItemById("1");

        assertEquals(mockItem, result);
        verify(inventoryService, times(1)).getItemById("1");
    }

    @Test
    void testAddInventoryItem() {
        InventoryItem mockItem = new InventoryItem("1", "Item1", "Category1", "Supplier1", 10.0, 100.0, 50);
        when(inventoryService.saveItem(mockItem)).thenReturn(mockItem);

        InventoryItem result = inventoryController.addInventoryItem(
                "1", "Item1", "Category1", "Supplier1", 10.0, 100.0, 50);

        assertEquals(mockItem, result);
        verify(inventoryService, times(1)).saveItem(mockItem);
        verify(inventoryPublisher, times(1)).addInventory(mockItem);
    }

    @Test
    void testInventoryItemSubscription() {
        Flux<InventoryItem> mockFlux = Flux.just(
                new InventoryItem("1", "Item1", "Category1", "Supplier1", 10.0, 100.0, 50),
                new InventoryItem("2", "Item2", "Category2", "Supplier2", 20.0, 200.0, 30)
        );
        when(inventoryPublisher.getInventoryItems()).thenReturn(mockFlux);

        Flux<InventoryItem> result = inventoryController.inventoryItem();

        assertEquals(mockFlux, result);
        verify(inventoryPublisher, times(1)).getInventoryItems();
    }
}