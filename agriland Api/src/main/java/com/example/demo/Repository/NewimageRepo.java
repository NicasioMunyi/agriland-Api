package com.example.demo.Repository;
import com.example.demo.Entity.NewImage;
import com.example.demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository

public interface NewimageRepo extends JpaRepository<NewImage, Long> {
    List<NewImage> findByUserId(Long userId);
    List<NewImage> findByUser(User user);

}



