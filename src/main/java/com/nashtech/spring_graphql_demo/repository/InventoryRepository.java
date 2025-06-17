package com.nashtech.spring_graphql_demo.repository;

import com.nashtech.spring_graphql_demo.entities.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * InventoryRepository provides CRUD operations for InventoryItem entities.
 * It extends JpaRepository to leverage Spring Data JPA functionalities.
 */
public interface InventoryRepository extends JpaRepository<InventoryItem, String> {
}
