package com.tastygreens.inventoryservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;

@Data
@Document(collection = "Inventory")
@JsonPropertyOrder({"itemId", "title", "type", "description", "price", "quantity", "quantityDate"})
public class Item {

    @Transient
    public static final String SEQUENCE_NAME = "INVENTORY_SEQ";

    @Id
    private long itemId;

    @NotBlank
    private String title;

    @NotBlank
    private String type;

    @NotBlank
    private String description;

    @Positive
    private float price;

    @PositiveOrZero
    private int quantity;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "ddMMMyyyy")
    private Date quantityDate;

}
