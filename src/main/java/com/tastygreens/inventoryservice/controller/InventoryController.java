package com.tastygreens.inventoryservice.controller;

import com.tastygreens.inventoryservice.model.InventoryServiceResponse;
import com.tastygreens.inventoryservice.model.Item;
import com.tastygreens.inventoryservice.model.Status;
import com.tastygreens.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/")
    public String hello(){
        return "Hello from Inventory Service!";
    }

    @GetMapping(value = "/items", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Item>> getAllItems(){
        return ResponseEntity.ok(inventoryService.getAllItems());
    }

    @GetMapping(value = "/item", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> getItemById(@RequestParam Long itemId){
        return ResponseEntity.ok(inventoryService.getItemById(itemId));
    }

    @PostMapping(value = "/item", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InventoryServiceResponse> createItem(@Validated @RequestBody Item item){
        return ResponseEntity.ok(inventoryService.createOrUpdateItem(item));
    }

    @PutMapping(value = "/item", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InventoryServiceResponse> updateItem(@Validated @RequestBody Item item){
        return ResponseEntity.ok(inventoryService.createOrUpdateItem(item));
    }

    @DeleteMapping(value = "/item/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InventoryServiceResponse> deleteItem(@PathVariable Long itemId){
        return ResponseEntity.ok(inventoryService.deleteItem(itemId));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<InventoryServiceResponse> handleValidationException(MethodArgumentNotValidException ex){
        InventoryServiceResponse invResponse = new InventoryServiceResponse();
        invResponse.setStatus(Status.ERROR);
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(
                error -> errors.add((String.format("%s %s",((FieldError) error).getField(), error.getDefaultMessage()))));
        invResponse.setErrors(errors);
        return new ResponseEntity<>(invResponse, HttpStatus.BAD_REQUEST);
    }
}
