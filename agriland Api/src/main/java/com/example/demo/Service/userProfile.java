package com.example.demo.Service;
import com.example.demo.dtos.UserProfileDTO;


import org.modelmapper.ModelMapper;
import com.example.demo.Entity.User;
import com.example.demo.Entity.NewImage;
import com.example.demo.Repository.NewimageRepo;
import com.example.demo.Repository.UserRepository;
import com.example.demo.dtos.NewImageDTO;
import com.example.demo.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java .util.stream.Collectors;

@Service
public class userProfile {
    @Autowired
    private NewimageRepo newimageRepo;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    NewimageService newimageService;

    @Autowired
    private ModelMapper modelMapper;


    public List<UserProfileDTO> getAllUserProfiles() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToUserProfileDTO)
                .collect(Collectors.toList());
    }

    private UserProfileDTO mapToUserProfileDTO(User user) {
        UserProfileDTO userProfileDTO = modelMapper.map(user, UserProfileDTO.class);

        // Get images for the user
        List<NewImageDTO> images = newimageService.getImagesByUserId(user.getId());
        List<NewImageDTO> imageDTOs = images.stream()
                .map(image -> modelMapper.map(image, NewImageDTO.class))
                .collect(Collectors.toList());

        userProfileDTO.setImages(imageDTOs);
        return userProfileDTO;
    }


    public UserDTO getUserDetails(Long userId) {
        User user = userRepository.findById(Math.toIntExact(userId)).orElse(null);
        if (user == null) {
            // Handle user not found
            return null;
        }

        List<NewImage> images = newimageRepo.findByUserId(userId);
        List<NewImageDTO> imageDTOs = images.stream()
                .map(image -> modelMapper.map(image, NewImageDTO.class))
                .collect(Collectors.toList());

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setImages(imageDTOs);

        return userDTO;
    }
    public void createUserProfile(String name, String email, byte[] imageBytes) {
        // Create a new user
        User user = new User();
        user.setName(name);
        user.setEmail(email);

        // Save the user to the database
        userRepository.save(user);

        // Create a new image
        NewImage newImage = new NewImage();
        newImage.setUser(user);
        newImage.setUploadDate(new Date());

        // Save the image to the database
        newimageService.saveImage(newImage, imageBytes);
    }






}
