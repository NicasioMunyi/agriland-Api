package com.example.demo.Controller;

import com.example.demo.Service.userProfile;
import com.example.demo.dtos.UserDTO;
import com.example.demo.dtos.UserProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("agirland")
class UserProfileController{
    @Autowired
    private userProfile userProfileService;

    @GetMapping("all-users")
    public ResponseEntity<List<UserProfileDTO>> getAllUsersProfiles(){
        List<UserProfileDTO> usersProfile = userProfileService.getAllUserProfiles();

        return  new ResponseEntity<>(usersProfile , HttpStatus.OK);

    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId){
        UserDTO user = userProfileService.getUserDetails(userId);
        if(user == null){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<>(user, HttpStatus.OK);

    }


}