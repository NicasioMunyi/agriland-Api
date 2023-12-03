package com.example.demo.Repository;

import com.example.demo.Entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageEntityRepo extends JpaRepository<ImageEntity, Long> {

    Optional<ImageEntity> findByName(String name);

}
