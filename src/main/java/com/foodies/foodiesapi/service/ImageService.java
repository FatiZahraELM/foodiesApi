package com.foodies.foodiesapi.service;

import com.foodies.foodiesapi.entite.Food;
import com.foodies.foodiesapi.repository.FoodRepository;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class ImageService {

    private final FoodRepository foodRepository;
    @Setter
    @Value("${max-file-size}")
    private Long maxFileSize;

    @Setter
    @Value("${file.storage.path}")
    private String fileDirectory;


    @Value("${ip-address}")
    private String ipAddress;

    public ImageService( FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public String uploadFoodImage(String id, MultipartFile file) throws IOException {
        if (file.getSize() > maxFileSize) {
            throw new IllegalArgumentException("File size exceeds the maximum allowed size");
        }

        Food food = foodRepository.findById(id).orElseThrow(()-> new RuntimeException("no food found"));
        String originalfile_name = file.getOriginalFilename();
        assert originalfile_name != null;
        String extension = originalfile_name.substring(originalfile_name.lastIndexOf('.') + 1);

        String oldFile = food.getImageUrl();
        if (oldFile != null) {
            deletFoodImage(id);
        }

        String newfile_name = UUID.randomUUID() + "." + extension;
        String filePath = fileDirectory  + newfile_name;

        food.setImageUrl(newfile_name);
        foodRepository.save(food);

        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Failed to save LOGO", e);
        }

        return "Logo uploaded successfully";
    }

    public String deletFoodImage(String id) throws IOException {
        Food food = foodRepository.findById(id).orElseThrow(()-> new RuntimeException("no food found"));
        String file_name = food.getImageUrl();

        if (file_name == null) {
            throw new FileNotFoundException("No file found for Food: " + id);
        }

        String filePath = getFilePath(file_name);
        deleteFile(filePath);

        food.setImageUrl(null);
        foodRepository.save(food);

        return "Food file deleted successfully";
    }

    private String getFilePath(String file_name) {
        return fileDirectory + file_name;
    }

    private void deleteFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        Files.deleteIfExists(path);
    }

    public String getFileUrl(String id) throws FileNotFoundException {
        Food food = foodRepository.findById(id).orElseThrow(()-> new RuntimeException("no food found"));
        String file_name = food.getImageUrl();
        if (file_name == null) {
            throw new FileNotFoundException("Fichier introuvable for Food: " + id);
        }
        return ipAddress + file_name ;
    }
}