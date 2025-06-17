package com.nashtech.spring_graphql_demo.service;

import com.nashtech.spring_graphql_demo.entities.InventoryItem;
import com.nashtech.spring_graphql_demo.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * InventoryServiceTest is a test class for InventoryService.
 * It uses Mockito to mock dependencies and verify the behavior of the service methods.
 */
class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllItems() {
        List<InventoryItem> mockItems = Arrays.asList(
                new InventoryItem("1", "Item1", "Category1", "Supplier1", 10.0, 100.0, 50),
                new InventoryItem("2", "Item2", "Category2", "Supplier2", 20.0, 200.0, 30)
        );
        when(inventoryRepository.findAll()).thenReturn(mockItems);

        List<InventoryItem> result = inventoryService.getAllItems();

        assertEquals(mockItems, result);
        verify(inventoryRepository, times(1)).findAll();
    }

    @Test
    void testGetItemById() {
        InventoryItem mockItem = new InventoryItem("1", "Item1", "Category1", "Supplier1", 10.0, 100.0, 50);
        when(inventoryRepository.findById("1")).thenReturn(Optional.of(mockItem));

        InventoryItem result = inventoryService.getItemById("1");

        assertEquals(mockItem, result);
        verify(inventoryRepository, times(1)).findById("1");
    }

    @Test
    void testSaveItem() {
        InventoryItem mockItem = new InventoryItem("1", "Item1", "Category1", "Supplier1", 10.0, 100.0, 50);
        when(inventoryRepository.save(mockItem)).thenReturn(mockItem);

        InventoryItem result = inventoryService.saveItem(mockItem);

        assertEquals(mockItem, result);
        verify(inventoryRepository, times(1)).save(mockItem);
    }
}