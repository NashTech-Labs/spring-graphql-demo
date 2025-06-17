package com.nashtech.spring_graphql_demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * InventoryItem represents an item in the inventory with its details.
 * It includes fields for id, name, category, supplier, cost price, selling price, and quantity.
 * The class overrides equals and hashCode methods for proper comparison and hashing.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InventoryItem {
    @Id
    private String id;
    private String name;
    private String category;
    private String supplier;
    private double costPrice;
    private double sellingPrice;
    private int quantity;


    // Compares this InventoryItem with another object for equality.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryItem that = (InventoryItem) o;
        return Double.compare(that.costPrice, costPrice) == 0 &&
                Double.compare(that.sellingPrice, sellingPrice) == 0 &&
                quantity == that.quantity &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(category, that.category) &&
                Objects.equals(supplier, that.supplier);
    }

    // Generates a hash code for the InventoryItem based on its fields.
    @Override
    public int hashCode() {
        return Objects.hash(id, name, category, supplier, costPrice, sellingPrice, quantity);
    }
}

