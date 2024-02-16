package com.example.demo.Repository;

import com.example.demo.Entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {

        User user = User.builder()
                .name("Nick")
                .email("nick@gmail.com")
                .password("nick11")
                .build();

        userRepository.save(user);

    }
    @Test
    public void testFindByEmail() {

        Optional<User> foundUser = userRepository.findByEmail("nick@gmail.com");
        assertTrue(foundUser.isPresent());

        User storedUser = foundUser.get();
        assertEquals("Nick",storedUser.getName());
        assertEquals("nick11", storedUser.getPassword());
    }
}