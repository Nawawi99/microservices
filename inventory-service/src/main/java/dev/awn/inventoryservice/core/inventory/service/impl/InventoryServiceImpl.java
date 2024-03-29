package dev.awn.inventoryservice.core.inventory.service.impl;

import dev.awn.inventoryservice.common.exception.BadRequestException;
import dev.awn.inventoryservice.core.inventory.dto.InventoryResponse;
import dev.awn.inventoryservice.core.inventory.repository.InventoryRepository;
import dev.awn.inventoryservice.core.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;

    @Override
    public List<InventoryResponse> isInStock(List<String> codes) {
        // Simulating a slow service response
//        try {
//            log.info("sleeping");
//            Thread.sleep(10000);
//            Thread.currentThread().interrupt();
//        } catch (InterruptedException ignored) {
//            throw new RuntimeException();
//        } finally {
//            log.info("in finally");
//        }

        if (codes == null || codes.isEmpty()) {
            throw new BadRequestException("Code/s cannot be empty");
        }

        return inventoryRepository.findByCodeIn(codes)
                                  .stream()
                                  .map(inventory -> InventoryResponse.builder()
                                                                     .code(inventory.getCode())
                                                                     .inStock(inventory.getQuantity() > 0)
                                                                     .build())
                                  .toList();
    }
}
