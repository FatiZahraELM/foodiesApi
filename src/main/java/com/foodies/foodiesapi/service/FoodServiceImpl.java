package com.foodies.foodiesapi.service;

import com.foodies.foodiesapi.entite.Food;
import com.foodies.foodiesapi.io.FoodRequest;
import com.foodies.foodiesapi.io.FoodResponse;
import com.foodies.foodiesapi.mapper.FoodMapper;
import com.foodies.foodiesapi.repository.FoodRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;
    private final ImageService imageService;

    public FoodServiceImpl(FoodRepository foodRepository, ImageService imageService) {
        this.foodRepository = foodRepository;
        this.imageService = imageService;
    }

    @Override
    public FoodResponse addFood(FoodRequest foodRequest) {
        Food newFood= FoodMapper.toEntity(foodRequest);
        Food foodSaved=foodRepository.save(newFood);
        return FoodMapper.toResponse(foodSaved);
    }

    @Override
    public FoodResponse getFood(String foodId) {
        Food food=foodRepository.findById(foodId).get();
        return FoodMapper.toResponse(food);
    }

    @Override
    public FoodResponse updateFood(String foodId, FoodRequest foodRequest) {
        Food food=foodRepository.findById(foodId).get();
        food.setName(foodRequest.getName());
        food.setPrice(foodRequest.getPrice());
        food.setCategory(foodRequest.getCategory());
        food.setDescription(foodRequest.getDescription());

        Food savedFood=foodRepository.save(food);
        return FoodMapper.toResponse(savedFood);
    }

    @Override
    public void deleteFood(String foodId) {
        Food food=foodRepository.findById(foodId).get();
        if(food.getImageUrl()!=null){
           try {
               imageService.deletFoodImage(foodId);
           }catch (Exception e){
               e.printStackTrace();
           }
        }
        foodRepository.deleteById(foodId);
    }

    @Override
    public List<FoodResponse> getAllFoods() {
        return foodRepository.findAll().stream().map(FoodMapper::toResponse).collect(Collectors.toList());
    }

}
