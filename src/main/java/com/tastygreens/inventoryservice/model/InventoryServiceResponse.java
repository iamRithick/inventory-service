package com.tastygreens.inventoryservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InventoryServiceResponse {

    private Long itemId;

    private String title;

    private Status status;

    private List<String> errors;
}
