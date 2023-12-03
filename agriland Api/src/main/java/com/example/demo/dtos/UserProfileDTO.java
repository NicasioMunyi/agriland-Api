package com.example.demo.dtos;// UserProfileDTO.java
import com.example.demo.dtos.NewImageDTO;
import lombok.Data;

import java.util.List;

@Data
public class UserProfileDTO {
    private Long id;
    private String name;
    private String email;
    private List<NewImageDTO> images;
}
