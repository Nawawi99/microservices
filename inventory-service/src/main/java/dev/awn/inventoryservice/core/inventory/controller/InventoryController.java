package dev.awn.inventoryservice.core.inventory.controller;

import dev.awn.inventoryservice.core.inventory.dto.InventoryResponse;
import dev.awn.inventoryservice.core.inventory.model.Inventory;
import dev.awn.inventoryservice.core.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<InventoryResponse>> isInStock(@RequestParam List<String> codes) {
        List<InventoryResponse> inventoryResponses = inventoryService.isInStock(codes);

        if (inventoryResponses == null || inventoryResponses.isEmpty()) {
            return null;
        }

        return new ResponseEntity<>(inventoryService.isInStock(codes),
                HttpStatus.OK);
    }

}
