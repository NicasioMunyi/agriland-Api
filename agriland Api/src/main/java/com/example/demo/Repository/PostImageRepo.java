package com.example.demo.Repository;

import com.example.demo.Entity.Post_image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostImageRepo extends JpaRepository<Post_image, Long> {
    List<Post_image> findByPostId(Long post_id);

}
