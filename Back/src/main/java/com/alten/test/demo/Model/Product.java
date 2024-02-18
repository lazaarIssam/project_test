package com.alten.test.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @MongoId(FieldType.INT64)
    private ObjectId id;
    private String code;
    private String name;
    private String description;
    private Integer price;
    private Integer quantity;
    private String inventoryStatus;
    private String category;
    private String image;
    private Integer rating;
}
