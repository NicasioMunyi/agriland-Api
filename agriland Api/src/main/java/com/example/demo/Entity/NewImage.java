package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "new_images")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "IMAGENAME")
    private String imageName;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "UPLOADTIME")
    private Date uploadDate;

}
