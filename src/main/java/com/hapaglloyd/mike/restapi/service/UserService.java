package com.hapaglloyd.mike.restapi.service;

import com.hapaglloyd.mike.restapi.repository.UserRepository;
import com.hapaglloyd.mike.restapi.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<User> save(User user) {
        if (existsByUsername(user)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        user.setTimestamp(LocalDateTime.now());
        return ResponseEntity.ok(userRepository.save(user));
    }

    public ResponseEntity<User> get(Integer id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<Iterable<User>> get() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    public ResponseEntity<User> update(User user) {
        if (userRepository.existsById(user.getId())) {
            if (existsByUsername(user, user.getId())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }
            user.setTimestamp(LocalDateTime.now());
            return ResponseEntity.ok(userRepository.save(user));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    private boolean existsByUsername(User user) {
        for (User u : userRepository.findAll()) {
            if (u.getUsername().equals(user.getUsername())) {
                return true;
            }
        }
        return false;
    }

    private boolean existsByUsername(User user, Integer id) {
        for (User u : userRepository.findAll()) {
            if (u.getUsername().equals(user.getUsername())) {
                if (u.getId() != id) {
                    return true;
                }
            }
        }
        return false;
    }
}
