package com.example.demo.Service;

import com.example.demo.Entity.Product;
import com.example.demo.Entity.ShowInterest;
import com.example.demo.Entity.User;
import com.example.demo.Repository.ShowInterestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ShowInterestService {
    @Autowired
    private ShowInterestRepository interestRepository;

    public ShowInterest showInterest(User user, Product product) {
        ShowInterest interest = new ShowInterest();
        interest.setUser(user);
        interest.setProduct(product);
        return interestRepository.save(interest);
    }

    // Other methods as needed...
}
