package com.example.demo.Controller;

import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.dtos.UserProfileDTO;
import org.springframework.http.HttpStatus;
import com.example.demo.Service.userProfile;
import com.example.demo.dtos.UserDTO;
import com.example.demo.Entity.NewImage;
import com.example.demo.Service.NewimageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/userProfile")
public class UserProfileController {

    @Autowired
    private userProfile profileService;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserDetails(@PathVariable Long userId) {
        UserDTO userDetailsDTO = profileService.getUserDetails(userId);

        if (userDetailsDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userDetailsDTO, HttpStatus.OK);
    }

    @Autowired
    private NewimageService newimageService;


    @GetMapping("/allusers")
    public ResponseEntity<List<UserProfileDTO>> getAllUserProfiles() {
        List<UserProfileDTO> userProfiles = profileService.getAllUserProfiles();

        if (userProfiles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userProfiles, HttpStatus.OK);
    }
}