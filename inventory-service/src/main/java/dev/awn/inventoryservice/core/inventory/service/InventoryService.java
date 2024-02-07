package dev.awn.inventoryservice.core.inventory.service;

import dev.awn.inventoryservice.core.inventory.dto.InventoryResponse;

import java.util.List;

public interface InventoryService {
    List<InventoryResponse> isInStock(List<String> codes);
}
