package com.foodies.foodiesapi.mapper;

import com.foodies.foodiesapi.entite.Food;
import com.foodies.foodiesapi.io.FoodRequest;
import com.foodies.foodiesapi.io.FoodResponse;

public class FoodMapper {
    public static Food toEntity(FoodRequest foodRequest) {
        Food food = new Food();
        food.setName(foodRequest.getName());
        food.setPrice(foodRequest.getPrice());
        food.setCategory(foodRequest.getCategory());
        food.setDescription(foodRequest.getDescription());
        return food;
    }

    public static FoodResponse toResponse(Food food) {
        FoodResponse foodResponse = new FoodResponse();
        foodResponse.setId(food.getId());
        foodResponse.setName(food.getName());
        foodResponse.setPrice(food.getPrice());
        foodResponse.setCategory(food.getCategory());
        foodResponse.setDescription(food.getDescription());
        foodResponse.setImageUrl(food.getImageUrl());
        return foodResponse;
    }
}
