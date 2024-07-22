package com.hapaglloyd.mike.restapi.service;


import com.hapaglloyd.mike.restapi.repository.UserRepository;
import com.hapaglloyd.mike.restapi.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUser() {
        User user = new User(1, "testuser", "male", 25);
        when(userRepository.save(any(User.class))).thenReturn(user);

        ResponseEntity<User> response = userService.save(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());

    }

    @Test
    void testGetUser() {
        User user = new User(1, "testuser", "male", 25);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userService.get(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testGetUserNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<User> response = userService.get(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testUpdateUser() {
        int userId = 1;
        User user = new User(userId, "testuser", "male", 25);
        User updatedUser = new User(userId, "testuser", "female", 26);

        when(userRepository.existsById(userId)).thenReturn(true);

        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setTimestamp(LocalDateTime.now());
            return savedUser;
        });

        ResponseEntity<User> response = userService.update(updatedUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedUser.getId(), response.getBody().getId());
        assertEquals(updatedUser.getUsername(), response.getBody().getUsername());
        assertEquals(updatedUser.getAge(), response.getBody().getAge());
        assertEquals(updatedUser.getGender(), response.getBody().getGender());
        assertNotNull(response.getBody().getTimestamp());

        verify(userRepository).save(any(User.class));
    }

    @Test
    void testDeleteUser() {
        int userId = 1;
        User user = new User(userId, "testuser", "male", 25);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.delete(userId);

        verify(userRepository).deleteById(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        ResponseEntity<User> response = userService.get(userId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
