package com.example.demo.Service;

import com.example.demo.Entity.Post;
import com.example.demo.Entity.Post_image;
import com.example.demo.Repository.PostImageRepo;
import com.example.demo.Repository.PostRepo;
import com.example.demo.dtos.PostImagesDTO;
import com.example.demo.dtos.PostsDTO;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepo postRepo;
    // You'll need to create PostRepository for database operations
    @Autowired
    private PostImageRepo postImageRepo;

    @Autowired
    private ModelMapper modelMapper;

    public void addPost(Post post) {
        // You can add any additional logic/validation here
        postRepo.save(post);
    }
    @Transactional
    public Post createPost(PostsDTO postsDTO) {
        Post post = new Post();
        post.setContent(postsDTO.getContent());
        post.setAuthor_id(postsDTO.getAuthor_id());
        post.setTimestamp(new Date());
        post = postRepo.save(post);
        return post;
    }


    public PostsDTO getPostById(Long id) {
        Post post = postRepo.findById(id).orElse(null);
        if (post == null) {
            // Handle user not found
            return null;
        }

        List<Post_image> images = postImageRepo.findByPostId(id);
        List<PostImagesDTO> imageDTOs = images.stream()
                .map(image -> modelMapper.map(image, PostImagesDTO.class))
                .collect(Collectors.toList());

        PostsDTO postDTO = modelMapper.map(post, PostsDTO.class);
        postDTO.setImages(imageDTOs);

        return postDTO;
    }
    @Transactional
    public void addImage(Long postId, PostImagesDTO postImagesDTO) {
        Post_image image = new Post_image();
        image.setName(postImagesDTO.getName());

        image.setId(postId);

        postImageRepo.save(image);
    }
    @Transactional
    public void savePostWithImage(Post post, MultipartFile imageFile, String uploadDirectory) {
        // Save the post
        Post savedPost = postRepo.save(post);

        // Save the image to the filesystem
        String imageName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
        Path imagePath = Paths.get(uploadDirectory, imageName);
        try {
            Files.copy(imageFile.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image to filesystem", e);
        }

        // Save image details to the database
        Post_image image = new Post_image();
        image.setName(imageName);
        image.setPost(savedPost);
        image.setImage_path(imagePath.toString()); // Set the image path
        postImageRepo.save(image);
    }

    @Transactional
    public List<PostsDTO> getAllPosts() {
        List<Post> posts = postRepo.findAll();
        return posts.stream()
                .map(post -> {
                    List<Post_image> images = postImageRepo.findByPostId(post.getId());
                    List<PostImagesDTO> imageDTOs = images.stream()
                            .map(image -> modelMapper.map(image, PostImagesDTO.class))
                            .collect(Collectors.toList());

                    PostsDTO postDTO = modelMapper.map(post, PostsDTO.class);
                    postDTO.setImages(imageDTOs);

                    return postDTO;
                })
                .collect(Collectors.toList());
    }


}
