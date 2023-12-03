package com.example.demo.Entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "post_images")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post_image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "IMAGE_NAME")
    private String name;

    @Column(name = "IMAGE_PATH")
    private String image_path;



}
