package com.bkenterprises.inventory.controller;

import com.bkenterprises.inventory.dao.InventoryRepository;
import com.bkenterprises.inventory.model.Inventory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class InventoryController {

    private final InventoryRepository inventoryRepository;

    public InventoryController(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @GetMapping("/inventory")
    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll().stream()
                .filter(i -> i.getQuantity() > 0)
                .collect(Collectors.toList());
    }

}
