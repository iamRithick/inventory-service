package com.tastygreens.inventoryservice.service.impl;

import com.tastygreens.inventoryservice.model.InventoryServiceResponse;
import com.tastygreens.inventoryservice.model.Item;
import com.tastygreens.inventoryservice.model.Status;
import com.tastygreens.inventoryservice.repo.InventoryRepo;
import com.tastygreens.inventoryservice.service.InventoryService;
import com.tastygreens.inventoryservice.service.MongoDBSequenceGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    private static final Logger LOGGER = LogManager.getLogger(InventoryServiceImpl.class);

    @Autowired
    private InventoryRepo inventoryRepo;

    @Autowired
    private MongoDBSequenceGenerator sequenceGenerator;

    @Override
    public List<Item> getAllItems() {
        return inventoryRepo.findAll();
    }

    @Override
    public Item getItemById(Long itemId) {
        return inventoryRepo.findById(itemId).orElse(null);
    }

    @Override
    @Transactional
    public InventoryServiceResponse createOrUpdateItem(Item item) {
        InventoryServiceResponse response = new InventoryServiceResponse();
        response.setTitle(item.getTitle());
        try {
            response.setStatus(Status.UPDATED);
            if (item.getItemId() == 0) {
                item.setItemId(sequenceGenerator.generateSequenceNumber(Item.SEQUENCE_NAME));
                response.setStatus(Status.CREATED);
            }
            item.setQuantityDate(new Date());
            Item insertedItem = inventoryRepo.save(item);
            response.setItemId(insertedItem.getItemId());
        } catch (Exception ex){
            LOGGER.error("Exception while adding item to db: ", ex);
            response.setStatus(Status.ERROR);
            response.setErrors(new ArrayList<>());
            response.getErrors().add("Unable to create/update item: " + ex.getMessage());
        }
        return response;
    }

    @Override
    @Transactional
    public InventoryServiceResponse deleteItem(Long itemId) {
        InventoryServiceResponse response = new InventoryServiceResponse();
        response.setItemId(itemId);
        try {
            Item item = inventoryRepo.findById(itemId).orElse(null);
            if (item != null){
                inventoryRepo.delete(item);
                response.setTitle(item.getTitle());
                response.setStatus(Status.DELETED);
            } else {
                response.setStatus(Status.ERROR);
                response.setErrors(new ArrayList<>());
                response.getErrors().add("No item found with itemId: " + itemId);
            }
        } catch (Exception ex){
            LOGGER.error("Exception while adding item to db: ", ex);
            response.setStatus(Status.ERROR);
            response.setErrors(new ArrayList<>());
            response.getErrors().add("Unable to delete the item: " + ex.getMessage());
        }
        return response;
    }
}
