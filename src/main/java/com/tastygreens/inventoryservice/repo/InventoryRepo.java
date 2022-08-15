package com.tastygreens.inventoryservice.repo;

import com.tastygreens.inventoryservice.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepo extends MongoRepository<Item, Long> {

}
