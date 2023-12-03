package com.example.demo.Controller;

import com.example.demo.Entity.NewImage;
import com.example.demo.Service.NewimageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/images")
public class NewimageController {
    @Autowired
    private NewimageService newimageService;
    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file,
                                         @RequestParam("description") String description) {
        String originalFilename = file.getOriginalFilename();
        String imageName = originalFilename.substring(originalFilename.lastIndexOf("\\") + 1);

        NewImage uploadedImage = newimageService.uploadImage(imageName, description, file);

        if (uploadedImage != null) {
            return ResponseEntity.ok("Image uploaded successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to upload image");
        }
    }
    @GetMapping("/allImages")
    public List<Map<String, Object>> getAllImages() {
        List<NewImage> images = newimageService.getAllImages();
        List<Map<String, Object>> response = new ArrayList<>();

        for (NewImage image : images) {
            Map<String, Object> imageMap = new HashMap<>();
            imageMap.put("id", image.getId());
            imageMap.put("imageName", image.getImageName());

            imageMap.put("imagePath", "/images/" + image.getImageName());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            imageMap.put("uploadDate", dateFormat.format(image.getUploadDate()));

            response.add(imageMap);
        }

        return response;
    }
}