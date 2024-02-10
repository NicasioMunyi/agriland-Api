package com.example.demo.Service;

import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Map;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;
    public User saveDetails(User user){
        return userRepository.save(user);
    }

    public  User getDetailsById (int id){
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserByEmail(String email){
        return  userRepository.findByEmail(email);
    }

    public User updateUserImage(Long userId, String newImageName) {
        Optional<User> userOptional = userRepository.findById(Math.toIntExact(userId));

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setImage(newImageName);
            return userRepository.save(user);
        }

        return null; // Handle the case where the user with the given ID is not found
    }
    @Value("${upload.directory}")
    private String uploadDirectory;

    public User uploadImage(Long userId, String imageName, MultipartFile file) {
        try {
            User user = userRepository.findById(Math.toIntExact(userId)).orElse(null);

            if (user == null) {
                // Handle the case where the user with the given ID is not found
                return null;
            }

            // Generate a unique file name or use the existing one, depending on your requirements
            String fileName = imageName + "_" + new Date().getTime() + ".jpg";
            File imageFile = new File(uploadDirectory,fileName);


            // Save the image bytes to the file
            Files.write(imageFile.toPath(), file.getBytes());

            // Update the user's image information
            user.setImage(fileName);

            // Save the updated User entity to the database
            return userRepository.save(user);
        } catch (IOException e) {
            // Handle exceptions (e.g., file write failure) as needed
            e.printStackTrace();
            return null;
        }
    }
    public User updateUserProfile(Long userId, Map<String, String> updateDetails) {
        User user = userRepository.findById(Math.toIntExact(userId)).orElse(null);

        if (user == null) {
            // Handle the case where the user with the given ID is not found
            return null;
        }

        // Update user details based on the provided map
        if (updateDetails.containsKey("name")) {
            user.setName(updateDetails.get("name"));
        }

        if (updateDetails.containsKey("security")) {
            user.setSecurity(updateDetails.get("security"));
        }

        if (updateDetails.containsKey("answer")) {
            user.setAnswer(updateDetails.get("answer"));
        }

        // Save the updated User entity to the database
        return userRepository.save(user);
    }

}

