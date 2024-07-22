package com.hapaglloyd.mike.restapi.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hapaglloyd.mike.restapi.model.User;
import com.hapaglloyd.mike.restapi.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void testCreateUser() throws Exception {
        User user = new User(0, "testuser", "male", 25);
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.age").value(25))
                .andExpect(jsonPath("$.gender").value("male"));
    }

    @Test
    void testGetUser() throws Exception {
        User user = new User(0, "testuser", "male", 25); // Set id to 0
        user = userRepository.save(user); // Save the user and get the generated id

        mockMvc.perform(get("/users/{id}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.age").value(25));
    }

    @Test
    void testGetUsers() throws Exception {
        List<User> users = Arrays.asList(
                new User(0, "John", "male", 25),
                new User(0, "Bruce", "prefer-not-to-say", 28)
        );
        userRepository.saveAll(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[0].id").value(users.getFirst().getId()))
                .andExpect(jsonPath("$[0].username").value("John"))
                .andExpect(jsonPath("$[0].gender").value("male"))
                .andExpect(jsonPath("$[0].age").value(25))
                .andExpect(jsonPath("$[1].id").value(users.get(1).getId()))
                .andExpect(jsonPath("$[1].username").value("Bruce"))
                .andExpect(jsonPath("$[1].gender").value("prefer-not-to-say"))
                .andExpect(jsonPath("$[1].age").value(28));
    }

    @Test
    void testUpdateUser() throws Exception {
        User user = new User(0, "John", "male", 25);
        user = userRepository.save(user);

        User updatedUser = new User(user.getId(), "John", "female", 26);
        String updatedJson = objectMapper.writeValueAsString(updatedUser);

        mockMvc.perform(put("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.username").value("John"))
                .andExpect(jsonPath("$.gender").value("female"))
                .andExpect(jsonPath("$.age").value(26));
    }
}
