package com.example.demo.Service;

import com.example.demo.Entity.NewImage;
import com.example.demo.Entity.User;
import com.example.demo.Repository.NewimageRepo;
import com.example.demo.Repository.UserRepository;
import com.example.demo.dtos.NewImageDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewimageService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private NewimageRepo newimageRepo;

    @Autowired
    private UserRepository userRepository;

    @Value("${upload.directory}")
    private String uploadDirectory;

    public NewImage uploadImage(String imageName, String description, MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            File imageFile = new File(uploadDirectory, fileName);
            file.transferTo(imageFile);

            NewImage image = new NewImage();
            image.setImageName(imageName);
            image.setUploadDate(new Date());
            newimageRepo.save(image);

            return image;
        } catch (IOException e) {
            // Handle exceptions (e.g., file upload failure) as needed
            e.printStackTrace();
            return null;
        }
    }

    public NewImage saveImage(NewImage image, byte[] imageBytes) {
        try {
            // Generate a unique file name or use the existing one, depending on your requirements
            String fileName = image.getImageName() + "_" + new Date().getTime() + ".jpg";
            File imageFile = new File(uploadDirectory, fileName);

            // Save the image bytes to the file
            Files.write(imageFile.toPath(), imageBytes);

            // Set the file details in the NewImage entity
            image.setImageName(fileName);
            image.setUploadDate(new Date());

            // Save the NewImage entity to the database
            return newimageRepo.save(image);
        } catch (IOException e) {
            // Handle exceptions (e.g., file write failure) as needed
            e.printStackTrace();
            return null;
        }
    }

    public List<NewImage> getAllImages() {
        return newimageRepo.findAll();
    }

    public List<NewImageDTO> getImagesByUserId(Long userId) {
        User user = userRepository.findById(Math.toIntExact(userId)).orElse(null);
        if (user == null) {
            // Handle the case where the user with the given ID is not found
            return Collections.emptyList();
        }

        List<NewImage> images = newimageRepo.findByUser(user);

        // Convert the List<NewImage> to List<NewImageDTO> using modelMapper
        return images.stream()
                .map(image -> modelMapper.map(image, NewImageDTO.class))
                .collect(Collectors.toList());
    }

    // Additional methods for image operations can be added here, like updating, retrieving, or deleting images.
}
