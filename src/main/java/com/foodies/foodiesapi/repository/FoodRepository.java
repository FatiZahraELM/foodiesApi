package com.foodies.foodiesapi.repository;

import com.foodies.foodiesapi.entite.Food;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends MongoRepository<Food, String> {
}
