package com.example.demo.Controller;

import com.example.demo.Entity.Post;
import com.example.demo.Service.PostService;
import com.example.demo.dtos.PostImagesDTO;
import com.example.demo.dtos.PostsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/add")
    public ResponseEntity<String> addPost(@RequestBody Post post) {
        postService.addPost(post);
        return new ResponseEntity<>("Post added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/{post_id}")
    public ResponseEntity<PostsDTO> getPostDetils(@PathVariable Long post_id) {
        PostsDTO postsDTO = postService.getPostById(post_id);

        if (postsDTO == null) {
            // You can customize the response or throw an exception based on your requirements
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(postsDTO, HttpStatus.OK);
    }

    @PostMapping("/addNew")
    public ResponseEntity<Post> createPost(@RequestBody PostsDTO postsDTO) {
        PostImagesDTO imageDTO = (PostImagesDTO) postsDTO.getImages();
        Post post = postService.createPost(postsDTO);

        if (imageDTO != null) {
            postService.addImage(post.getId(), imageDTO);
        }

        return ResponseEntity.ok(post);
    }

    @Value("${upload.directory}")
    private String uploadDirectory; // Set the upload directory in application.properties

    @PostMapping("/addNewPost")
    public ResponseEntity<String> addPostWithImage(
            @RequestParam("content") String content,
            @RequestParam("author_Id") Long authorId,
            @RequestParam("imageFile") MultipartFile imageFile
    ) {
        Post post = new Post();
        post.setContent(content);
        postService.savePostWithImage(post, imageFile, uploadDirectory);

        return ResponseEntity.ok("Post and image added successfully");
    }

    @GetMapping("/getAllPosts")
    public ResponseEntity<List<PostsDTO>> getAllPosts() {
        List<PostsDTO> postsDTOs = postService.getAllPosts();
        return ResponseEntity.ok(postsDTOs);
    }
}
