package com.example.demo.Controller;

import com.example.demo.Entity.User;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")

public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public User createStudent(@RequestBody User user){
        return userService.saveDetails(user);
    }

    @GetMapping("/getUserById/{id}")
    public User getUserById(@PathVariable int id){

        return userService.getDetailsById(id);
    }
    @GetMapping("/getUserByEmail/{email}")
    public Optional<User> getUserByEmail(@PathVariable String email){
        return  userService.findUserByEmail(email);
    }
    @GetMapping("/getAllUsers")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    @PostMapping("/login")
    public ResponseEntity <String> login(@RequestBody Map<String , String> credentials){
        String email =credentials.get("email");
        String password =credentials.get("password");

        Optional <User> userOptional =userService.findUserByEmail(email);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            if (password.equals(user.getPassword())){
                return ResponseEntity.ok("Login Successful");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
    }
    @PutMapping("/updateImage/{userId}")
    public ResponseEntity<String> updateImage(@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
        String uniqueImageName = file.getOriginalFilename();
        User user =userService.uploadImage(userId,uniqueImageName, file);

        if (user != null) {
            return ResponseEntity.ok("Image updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }
    @PutMapping("/updateProfile/{id}")
    public ResponseEntity<User> updateProfile(@PathVariable Long id, @RequestBody Map<String, String> updateDetails) {
        try {
            User user = userService.updateUserProfile(id, updateDetails);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}



