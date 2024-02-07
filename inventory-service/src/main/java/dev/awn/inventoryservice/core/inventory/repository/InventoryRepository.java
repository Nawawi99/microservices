package dev.awn.inventoryservice.core.inventory.repository;

import dev.awn.inventoryservice.core.inventory.model.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends MongoRepository<Inventory, String> {
    Optional<Inventory> findByCode(String code);

    List<Inventory> findByCodeIn(List<String> codes);
}
