package com.example.demo.Service;

import com.example.demo.Entity.ShowInterest;
import com.example.demo.Entity.User;
import com.example.demo.Repository.CommentRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Entity.Product;
import com.example.demo.Entity.Comment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ModelMapper modelMapper;
    public Product createPost(Product product) {
        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

    public void deletePost(Long id) {
        productRepository.deleteById(id);
    }

    public Comment addComment(Long productId, Comment comment) {
        comment.setProduct(productRepository.findById(productId).get());
        Comment savedComment = commentRepository.save(comment);
        return savedComment;
    }

    public List<Product> getAllPosts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> {
                    List<Comment> comments = commentRepository.findByProductId(product.getId());
                    product.setComments(comments);
                    return product;
                })
                .collect(Collectors.toList());
    }


    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    public void saveProduct(Product product) {
        productRepository.save(product);

    }
    public List<User> getUsersInterestedInProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        return product.map(value -> value.getInterests().stream()
                        .map(ShowInterest::getUser)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    public ShowInterest showInterest(Long productId, User user) throws ChangeSetPersister.NotFoundException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        ShowInterest interest = new ShowInterest();
        interest.setProduct(product);
        interest.setUser(user);

        product.getInterests().add(interest);
        productRepository.save(product);

        return interest;
    }

}
