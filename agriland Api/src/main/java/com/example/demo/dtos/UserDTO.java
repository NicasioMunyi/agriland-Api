package com.example.demo.dtos;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private String name;
    private String email;

    private List<NewImageDTO> images;

}
