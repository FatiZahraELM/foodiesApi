package com.foodies.foodiesapi.entite;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "foods")
@AllArgsConstructor
@NoArgsConstructor
public class Food {
    @Id
    private String id;
    private String name;
    private String description;
    private String category;
    private double price;
    private String imageUrl;

}
