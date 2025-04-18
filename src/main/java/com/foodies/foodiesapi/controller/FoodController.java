package com.foodies.foodiesapi.controller;

import com.foodies.foodiesapi.io.FoodRequest;
import com.foodies.foodiesapi.io.FoodResponse;
import com.foodies.foodiesapi.service.FoodService;
import com.foodies.foodiesapi.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/foods")
public class FoodController {
    private final FoodService foodService;
    private final ImageService imageService;

    public FoodController(FoodService foodService, ImageService imageService) {
        this.foodService = foodService;
        this.imageService = imageService;
    }

    @PostMapping
    public FoodResponse addFood(@RequestBody FoodRequest request) {
        return foodService.addFood(request);
    }

    @GetMapping
    public List<FoodResponse> getAllFoods() {
        return foodService.getAllFoods();
    }

    @GetMapping("/{id}")
    public FoodResponse getFood(@PathVariable String id) {
        return foodService.getFood(id);
    }

    @PutMapping("/{id}")
    public FoodResponse updateFood(@PathVariable String id, @RequestBody FoodRequest request) {
        return foodService.updateFood(id, request);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteFood(@PathVariable String id) {
        foodService.deleteFood(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{id}/upload-image")
    public String uploadFile(@PathVariable String id, @RequestParam("file") MultipartFile file) throws IOException {
        return imageService.uploadFoodImage(id,file);
    }

    @GetMapping("/{id}/image-url")
    public String getFoodImageUrl(@PathVariable String id) throws FileNotFoundException {
        return imageService.getFileUrl(id);
    }

    @DeleteMapping("/{id}/delete-image")
    public String deleteFoodImage(@PathVariable String id) throws IOException {
        return imageService.deletFoodImage(id);
    }
}
