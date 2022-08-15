package com.tastygreens.inventoryservice.service;

import com.tastygreens.inventoryservice.model.InventoryServiceResponse;
import com.tastygreens.inventoryservice.model.Item;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InventoryService {

    public List<Item> getAllItems();

    public Item getItemById(Long itemId);

    public InventoryServiceResponse createOrUpdateItem(Item item);

    public InventoryServiceResponse deleteItem(Long itemId);

}
