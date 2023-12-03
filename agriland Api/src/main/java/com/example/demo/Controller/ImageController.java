package com.example.demo.Controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final String uploadDirectory = "C:/Users/HP/Desktop/pimages/images/";

    @GetMapping("/{imageName:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) throws IOException {
        Path imagePath = Paths.get(uploadDirectory).resolve(imageName);
        Resource resource = new UrlResource(imagePath.toUri());

        // Check if the resource exists
        if (resource.exists() && resource.isReadable()) {
            // Determine the content type based on the file extension
            String contentType = determineContentType(imageName);

            // Build the response headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));

            // Return the response with headers and resource
            return ResponseEntity.ok().headers(headers).body(resource);
        } else {
            // Return a 404 response if the resource is not found
            return ResponseEntity.notFound().build();
        }
    }

    // Helper method to determine content type based on file extension
    private String determineContentType(String fileName) {
        String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1);
        switch (fileExtension.toLowerCase()) {
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            // Add more cases for other image types if needed
            default:
                return "application/octet-stream"; // Default to binary content type
        }
    }
}

