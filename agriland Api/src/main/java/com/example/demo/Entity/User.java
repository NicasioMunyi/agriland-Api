package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users_db")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private  Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name="EMAIL")
    private String email;

    @Column(name="PASSWORD")
    private String password;
    @Column(name="SECURITY")
    private String security;

    @Column(name="ANSWER")
    private String answer;

    @Column(name = "IMAGE")
    private String image;

}
