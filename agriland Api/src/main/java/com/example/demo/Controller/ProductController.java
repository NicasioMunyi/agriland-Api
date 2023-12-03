package com.example.demo.Controller;

import com.example.demo.Entity.*;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.ProductService;
import com.example.demo.Service.ShowInterestService;
import com.example.demo.Service.UserService;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShowInterestService  showInterestService;
    @Autowired
    private UserService userService;

    @PostMapping("/createPost")
    public ResponseEntity<Product> createPost(@RequestBody Product product) {
        Product savedProduct = productService.createPost(product);
        return ResponseEntity.ok(savedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        productService.deletePost(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{id}/comment")
    public ResponseEntity<Comment> addComment(@PathVariable Long id, @RequestBody CommentRequest commentRequest) {
        // Retrieve the current user (you need to implement a way to get the current user based on your authentication mechanism)
        Optional<User> currentUser = userService.findUserByEmail(commentRequest.getUserEmail());

        if (currentUser.isPresent()) {
            // Create a new comment
            Comment comment = new Comment();
            comment.setContent(commentRequest.getContent());
            comment.setUser(currentUser.get());


            // Find the product by ID
            Optional<Product> product = productService.findProductById(id);

            comment.setProduct(product.get());

            // Add the comment to the product
            if (product.isPresent()) {
                product.get().getComments().add(comment);

                // Save the updated product
                productRepository.save(product.get());

                // Return the saved comment
                return ResponseEntity.ok(comment);
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllPosts() {
        List<Product> products = productService.getAllPosts();
        return ResponseEntity.ok(products);
    }
    @Value("${upload.directory}")
    private String uploadDirectory;
    @PostMapping("/createNewPost")
    public ResponseEntity<Product> createPost(
            @RequestPart("description") String description,
            @RequestPart("imageFile") MultipartFile imageFile,
            @RequestParam("userId") Long userId) {

        // Save the product details
        Product product = new Product();
        product.setDescription(description);

        // You need to set the user using the userId
        User user = new User();
        user.setId(userId);
        product.setUser(user);

        Product savedProduct = productService.createPost(product);

        // Save the image to the filesystem
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
            Path imagePath = Paths.get(uploadDirectory, imageName);

            try {
                Files.copy(imageFile.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException("Failed to save image to filesystem", e);
            }

            // Save image details to the product entity
            savedProduct.setImageName(imageName);
            savedProduct.setImagePath(imagePath.toString());
            productRepository.save(savedProduct);
        }

        return ResponseEntity.ok(savedProduct);
    }
    @PostMapping("/{id}/showInterest")
    public ResponseEntity<String> showInterest(@PathVariable Long id, @RequestBody Map<String, String> request) throws ChangeSetPersister.NotFoundException {
        String userEmail = request.get("userEmail");

        // Retrieve the user by email
        Optional<User> userOptional = userService.findUserByEmail(userEmail);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Call your service method to show interest
            productService.showInterest(id, user);

            // You can perform any additional logic here

            return ResponseEntity.ok("Interest shown successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @GetMapping("/{id}/interests")
    public ResponseEntity<List<User>> getInterestsForProduct(@PathVariable Long id) {
        Optional<Product> product = productService.findProductById(id);

        if (product.isPresent()) {
            List<User> interestedUsers = product.get().getInterests().stream()
                    .map(ShowInterest::getUser)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(interestedUsers);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
