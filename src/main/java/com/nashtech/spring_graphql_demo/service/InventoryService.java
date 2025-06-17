package com.nashtech.spring_graphql_demo.service;

import com.nashtech.spring_graphql_demo.entities.InventoryItem;
import com.nashtech.spring_graphql_demo.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * InventoryService provides methods to manage inventory items.
 * It interacts with the InventoryRepository to perform CRUD operations.
 */
@Service
public class InventoryService {

    @Autowired
    private InventoryRepository repo;

    public List<InventoryItem> getAllItems() {
        return repo.findAll();
    }

    public InventoryItem getItemById(String id) {
        return repo.findById(id).orElse(null);
    }

    public InventoryItem saveItem(InventoryItem item) {
        return repo.save(item);
    }
}
