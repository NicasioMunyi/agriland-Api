package com.example.demo.Controller;

import com.example.demo.Service.ImageEntityService;
import com.example.demo.Entity.ImageEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.tika.Tika;
import  java.util.List;


import java.io.IOException;

import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageEntityController {

    private final ImageEntityService imageEntityService;

    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        String uploadImage = imageEntityService.uploadImage(file);
        return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName) {
        byte[] imageData = imageEntityService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(IMAGE_PNG_VALUE))
                .body(imageData);
    }

// ...0712270266

    public String determineImageContentType(byte[] imageData) {
        Tika tika = new Tika();
        return tika.detect(imageData);
    }
    @GetMapping("/getAll")

    public ResponseEntity<List<ImageEntity>> getAllImages() {

        List<ImageEntity> images = imageEntityService.getAllImages();
        return ResponseEntity.status(HttpStatus.OK).body(images);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<byte[]> downloadImageById(@PathVariable Long id) {
        byte[] imageData = imageEntityService.downloadImageById(id);

        if (imageData != null) {
            String contentType = determineImageContentType(imageData);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf(contentType));

            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } else {
            // Handle the case where the image with the given ID was not found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


}