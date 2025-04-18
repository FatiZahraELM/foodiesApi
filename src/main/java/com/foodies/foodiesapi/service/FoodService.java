package com.foodies.foodiesapi.service;

import com.foodies.foodiesapi.io.FoodRequest;
import com.foodies.foodiesapi.io.FoodResponse;
import jakarta.validation.constraints.AssertFalse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FoodService {
    FoodResponse addFood(FoodRequest food);

    FoodResponse getFood(String foodId);

    FoodResponse updateFood(String foodId, FoodRequest food);

    void deleteFood(String foodId);

    List<FoodResponse> getAllFoods();
}
